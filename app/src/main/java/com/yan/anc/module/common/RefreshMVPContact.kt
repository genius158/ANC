package com.yan.anc.module.common

import android.util.Log
import com.yan.anc.base.mvp.IBasePresenter
import com.yan.anc.base.mvp.IBaseView
import com.yan.anc.utils.RetrofitHelper

/**
 * Created by yan on 2017/11/5.
 */
class RefreshMVPContact() {

    interface RefreshView : IBaseView {
    }

    class MainPresenter(private val refreshView: RefreshView, private val retrofitHelper: RetrofitHelper) : IBasePresenter {
        fun log() = Log.e("sdfsdf", "sdfsadfsaf" + retrofitHelper)
    }

}