package com.yan.anc.module.android

import android.util.Log
import com.yan.anc.api.ANCApi
import com.yan.anc.module.common.viewmodel.ApiViewModel

/**
 * Created by yan on 2017/11/8.
 */

class AndroidViewModel(api: ANCApi) : ApiViewModel(api) {
    init {
        Log.e("ViewModel", this.toString() + "   " + api)
    }
}