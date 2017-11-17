/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yan.anc.repository

import android.annotation.SuppressLint
import android.arch.lifecycle.*
import android.os.AsyncTask
import android.support.annotation.MainThread
import org.jetbrains.annotations.Nullable
import android.support.annotation.WorkerThread

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread constructor() {
    private val result: MediatorLiveData<Resource<ResultType>> = object : MediatorLiveData<Resource<ResultType>>() {
        override fun onInactive() {
            super.onInactive()
            saveAsyncTask?.cancel(true)
        }
    }

    private var saveAsyncTask: NotifyAsyncTask? = null

    init {
        result.setValue(Resource.loading(null, isRefresh()))
        val dbSource: LiveData<ResultType>? = loadFromDb()
        if (dbSource == null) {
            fetchFromNetwork(null)
        } else {
            result.addSource<ResultType>(dbSource) { data ->
                result.removeSource<ResultType>(dbSource)
                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource)
                } else {
                    result.addSource<ResultType>(dbSource
                    ) { newData -> result.setValue(Resource.success(newData, isRefresh())) }
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>?) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source,
        // it will dispatch its latest value quickly
        dbSource?.let {
            result.addSource(it) { newData -> result.setValue(Resource.loading(newData, isRefresh())) }
        }
        apiResponse?.let {
            result.addSource<ApiResponse<RequestType>>(it) { response ->
                result.removeSource<ApiResponse<RequestType>>(apiResponse)
                dbSource?.let { result.removeSource(it) }

                if (response!!.isSuccessful()) {
                    saveResultAndReInit(response)
                } else {
                    onFetchFailed()
                    dbSource?.let {
                        result.addSource(it) { newData ->
                            result.setValue(Resource.error(response.error, newData, isRefresh()))
                        }
                    }
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    @SuppressLint("StaticFieldLeak")
    inner class NotifyAsyncTask : AsyncTask<Any, Any, Any>() {
        override fun doInBackground(vararg response: Any): Any {
            saveCallResult(isCancelled, (response[0] as ApiResponse<RequestType>).results!!)
            return response[0]
        }

        override fun onPostExecute(response: Any) {
            if (isCancelled) return

            val dbSource: LiveData<ResultType>? = loadFromDb()
            if (dbSource == null) {
                try {
                    val data: Resource<ResultType> = Resource.success((response as ApiResponse<*>).results as ResultType, isRefresh())!!
                    this@NetworkBoundResource.result.value = data
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                this@NetworkBoundResource.result.addSource(dbSource) { newData ->
                    result.setValue(Resource.success(newData, isRefresh()))
                }
            }
        }
    }

    @MainThread
    private fun saveResultAndReInit(response: ApiResponse<RequestType>) {
        if (saveAsyncTask == null || saveAsyncTask!!.isCancelled) {
            saveAsyncTask = NotifyAsyncTask().apply { execute(response) }
        }
    }

    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract fun saveCallResult(isCancelled: Boolean, item: RequestType)

    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected abstract fun shouldFetch(@Nullable data: ResultType?): Boolean

    // get data status
    @MainThread
    protected open fun isRefresh(): Boolean = true

    // Called to get the cached data from the database
    @MainThread
    protected open fun loadFromDb(): LiveData<ResultType>? = null

    // Called to create the API call.
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>?

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected open fun onFetchFailed() {
    }

    // returns a LiveData that represents the resource, implemented
    // in the base class.
    fun getAsLiveData(): LiveData<Resource<ResultType>> = result

}