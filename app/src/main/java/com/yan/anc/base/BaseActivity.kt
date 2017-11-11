package com.yan.anc.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yan.anc.App
import com.yan.anc.DaggerAppDIContact_AppComponent
import com.yan.anc.utils.ToastHelper
import javax.inject.Inject

/**
 * Created by yan on 2017/11/5.
 */

open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var toastHelper: ToastHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent(App.appComponent)
    }

    protected open fun appComponent(appComponent: DaggerAppDIContact_AppComponent) {}

}