package com.yan.anc

import android.app.Application

/**
 * Created by yan on 2017/11/5.
 */
class App : Application() {

    companion object {
        lateinit var appComponent: DaggerAppDIContact_AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        val dac = DaggerAppDIContact_AppComponent.builder()
                .appModule(AppDIContact.AppModule(this))
                .build()

        appComponent = dac as DaggerAppDIContact_AppComponent
    }

}