package com.yan.anc.module.android

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.yan.anc.api.ANCApi
import com.yan.anc.module.android.repository.AndroidData
import com.yan.anc.module.android.repository.AndroidRepository
import com.yan.anc.module.common.viewmodel.ApiViewModel
import com.yan.anc.repository.ApiResponse
import com.yan.anc.repository.Resource

/**
 * Created by yan on 2017/11/8.
 */
class AndroidViewModel(api: ANCApi) : ApiViewModel(api) {
    private val androidRepository: AndroidRepository = AndroidRepository(api)

    private var refreshLiveData: MutableLiveData<Int> = MutableLiveData()

    var androidData: LiveData<Resource<ApiResponse<List<AndroidData>>>>

    init {
        androidData = Transformations.switchMap(refreshLiveData, { pageNo ->
            return@switchMap androidRepository.getDatas(pageNo)
        })
    }

    fun getData() {
        refreshLiveData.value = 1
    }

    fun getDataMore() {
        refreshLiveData.value = refreshLiveData.value?.plus(1)
    }

}