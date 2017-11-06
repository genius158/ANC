package com.yan.anc

import android.app.Application

/**
 * Created by yan on 2017/11/5.
 */
class App : Application() {

    companion object {
        lateinit var appComponent: DaggerAppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        val dac = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        appComponent = dac as DaggerAppComponent
    }

}