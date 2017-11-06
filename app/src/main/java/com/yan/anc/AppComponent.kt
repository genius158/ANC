package com.yan.anc

import com.yan.anc.module.MainComponent
import com.yan.anc.module.MainModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by yan on 2017/11/5.
 */

@Singleton
@Component(modules = arrayOf(
        AppModule::class
))
interface AppComponent {
    fun plus(module: MainModule): MainComponent
}