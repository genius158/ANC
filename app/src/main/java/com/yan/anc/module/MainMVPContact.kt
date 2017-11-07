package com.yan.anc.module

import android.util.Log
import com.yan.anc.base.mvp.IBasePresenter
import com.yan.anc.base.mvp.IBaseView
import com.yan.anc.utils.RetrofitHelper

/**
 * Created by yan on 2017/11/5.
 */
class MainMVPContact {

    interface MainView : IBaseView {
    }

    class MainPresenter(private val mainView: MainView, private val retrofitHelper: RetrofitHelper) : IBasePresenter {
        fun log() = Log.e("sdfsdf", "sdfsadfsaf" + retrofitHelper)
    }

}