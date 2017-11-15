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
    private val result = MediatorLiveData<Resource<ResultType>>()

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

    @SuppressLint("StaticFieldLeak")
    @MainThread
    private fun saveResultAndReInit(response: ApiResponse<RequestType>) {
        object : AsyncTask<Any, Any, Any>() {
            override fun doInBackground(vararg any: Any): Any {
                saveCallResult(response.results)
                return Any()
            }

            @Suppress("UNCHECKED_CAST")
            override fun onPostExecute(any: Any) {
                // we specially request a new live data,
                // otherwise we will get immediately last cached value,
                // which may not be updated with latest results received from network.

                val dbSource: LiveData<ResultType>? = loadFromDb()
                if (dbSource == null) {
                    try {
                        val data: Resource<ResultType> = Resource.success(response.results as ResultType, isRefresh())!!
                        result.value = data
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                } else {
                    result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData, isRefresh())) }
                }
            }
        }.execute()
    }

    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType?)

    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected abstract fun shouldFetch(@Nullable data: ResultType?): Boolean

    // get data status
    @MainThread
    protected open fun isRefresh(): Boolean {
        return true
    }

    // Called to get the cached data from the database
    @MainThread
    protected open fun loadFromDb(): LiveData<ResultType>? {
        return null
    }

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
    fun getAsLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

}