package com.yan.anc.module

import com.yan.anc.di.ActivityScope
import dagger.Subcomponent


/**
 * Created by yan on 2017/11/5.
 */

@ActivityScope
@Subcomponent(modules = arrayOf(
        MainModule::class
))
interface MainComponent {
    fun injectTo(mainActivity: MainActivity)
}