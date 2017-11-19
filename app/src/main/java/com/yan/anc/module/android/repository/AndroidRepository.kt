package com.yan.anc.module.android.repository

import com.yan.anc.repository.ApiResponse
import android.arch.lifecycle.LiveData
import android.util.Log
import com.yan.anc.api.ANCApi
import com.yan.anc.repository.NetworkBoundResource
import com.yan.anc.repository.Resource
import org.jetbrains.annotations.Nullable


/**
 * Created by yan on 2017/11/14.
 */
internal class AndroidRepository(val api: ANCApi, val androidDao: AndroidDataDao) {
    private var DURING = 4000
    private var lastFetchTime = 0L

    private val pageSize = 10

    fun getDatas(pageNo: Int): LiveData<Resource<ApiResponse<List<AndroidData>>>> {
        return object : NetworkBoundResource<ApiResponse<List<AndroidData>>, ApiResponse<List<AndroidData>>>() {
            override fun saveCallResult(item: ApiResponse<List<AndroidData>>, inActive: BooleanArray) {
                item.results?.let { androidDao.insert(it) }
            }

            override fun shouldFetch(@Nullable data: ApiResponse<List<AndroidData>>?): Boolean {
                if (lastFetchTime == 0L
                        || data == null
                        || data.results == null
                        || data.results!!.isEmpty()
                        || lastFetchTime + DURING < System.currentTimeMillis()) {
                    lastFetchTime = System.currentTimeMillis()
                    return true
                }
                return false
            }


            override fun loadFromDb(): LiveData<ApiResponse<List<AndroidData>>>? = androidDao.loadAndroidData()

            override fun isRefresh(): Boolean = pageNo == 1

            override fun createCall(): LiveData<ApiResponse<ApiResponse<List<AndroidData>>>> = api.getAndroidDatas(pageSize.toString(), pageNo.toString())
        }.getAsLiveData()
    }

}