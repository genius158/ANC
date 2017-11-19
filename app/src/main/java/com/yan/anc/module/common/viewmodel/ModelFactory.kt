package com.yan.anc.module.common.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.yan.anc.api.ANCApi
import com.yan.anc.db.ANCDatabase
import com.yan.anc.module.android.AndroidViewModel
import javax.inject.Inject

/**
 * Created by yan on 2017/11/11.
 */
class ModelFactory @Inject constructor(private val api: ANCApi, private val ancDatabase: ANCDatabase) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (modelClass.simpleName) {
            AndroidViewModel::class.java.simpleName -> return AndroidViewModel(api, ancDatabase) as T
        }
        return null as T
    }
}