package com.yan.anc

import android.content.Context
import com.yan.anc.module.MainDIContact
import com.yan.anc.utils.RetrofitHelper
import com.yan.anc.utils.ToastHelper
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by yan on 2017/11/5.
 */
class AppDIContact {
    @Singleton
    @Component(modules = arrayOf(
            AppModule::class
    ))
    interface AppComponent {
        fun plus(module: MainDIContact.MainModule): MainDIContact.MainComponent
    }

    //------------------------------- line ----------------------------------

    @Module
    class AppModule(private val app: App) {

        @Provides
        @Singleton
        fun provideApp(): App = app

        @Provides
        @Singleton
        fun provideAppContext(): Context = app.applicationContext

        @Provides
        @Singleton
        fun provideToastHelper(): ToastHelper = ToastHelper(app)

        @Provides
        @Singleton
        fun provideRetrofitHelper(): RetrofitHelper = RetrofitHelper(app)

    }
}