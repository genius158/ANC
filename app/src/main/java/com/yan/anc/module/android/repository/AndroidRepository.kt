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
internal class AndroidRepository(val api: ANCApi) {

//    var userDao: UserDao? = null

    private val pageSize = 10

    fun getDatas(pageNo: Int): LiveData<Resource<ApiResponse<List<AndroidData>>>> {
        return object : NetworkBoundResource<ApiResponse<List<AndroidData>>, ApiResponse<List<AndroidData>>>() {
            override fun saveCallResult(item: ApiResponse<List<AndroidData>>, inActive: BooleanArray) {
                Thread.sleep(1000)
                Log.e("asyncTask", inActive[0].toString())
            }

            override fun shouldFetch(@Nullable data: ApiResponse<List<AndroidData>>?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<ApiResponse<List<AndroidData>>>? {
                return null
            }

            override fun isRefresh(): Boolean {
                return pageNo == 1
            }

            override fun createCall(): LiveData<ApiResponse<ApiResponse<List<AndroidData>>>> {
                return api.getAndroidDatas(pageSize.toString(), pageNo.toString())
            }
        }.getAsLiveData()
    }

}