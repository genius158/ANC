package com.yan.anc

import android.arch.persistence.room.Room
import android.content.Context
import com.yan.anc.api.ANCApi
import com.yan.anc.db.ANCDatabase
import com.yan.anc.module.MainActivity
import com.yan.anc.module.common.RefreshDIContact
import com.yan.anc.module.common.viewmodel.ModelFactory
import com.yan.anc.repository.LiveDataCallAdapterFactory
import com.yan.anc.utils.ToastHelper
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        fun injectTo(mainActivity: MainActivity)

        fun plus(module: RefreshDIContact.RefreshModule): RefreshDIContact.RefreshComponent

    }

    //------------------------------- line ----------------------------------

    @Module
    class AppModule(private val app: App) {
        private val ancDatabase = Room.databaseBuilder(app, ANCDatabase::class.java, "anc.db").build()

        private val api = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                        .build())
                .build()
                .create(ANCApi::class.java)

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
        fun provideApi(): ANCApi = api

        @Provides
        @Singleton
        fun provideModelFactory(): ModelFactory = ModelFactory(api, ancDatabase)

        @Singleton
        @Provides
        fun provideDb(): ANCDatabase = ancDatabase
    }
}

