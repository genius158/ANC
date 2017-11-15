package com.yan.anc.api

import android.arch.lifecycle.LiveData
import com.yan.anc.repository.ApiResponse
import com.yan.anc.module.android.repository.AndroidData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by yan on 2017/11/10.
 */

interface ANCApi {
    companion object {
        const val ANDROID_URL_BASE = "http://gank.io/api"
    }

    @GET(ANDROID_URL_BASE + "/data/Android/{pageSize}/{pageNo}")
    fun getAndroidDatas(@Path("pageSize") pageSize: String, @Path("pageNo") pageNo: String): LiveData<ApiResponse<ApiResponse<List<AndroidData>>>>

}
