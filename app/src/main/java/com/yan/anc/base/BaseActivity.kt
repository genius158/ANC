package com.yan.anc.base

import android.app.Activity
import android.os.Bundle
import com.yan.anc.App
import com.yan.anc.DaggerAppComponent
import com.yan.anc.utils.ToastHelper
import javax.inject.Inject

/**
 * Created by Administrator on 2017/11/5.
 */

abstract class BaseActivity : Activity() {

    @Inject
    lateinit var toastHelper: ToastHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent(App.appComponent)
    }

    protected abstract fun appComponent(appComponent: DaggerAppComponent)

}