package com.yan.anc.module.common

import com.yan.anc.di.FragmentScope
import dagger.Module
import dagger.Subcomponent

/**
 * Created by yan on 2017/11/5.
 */
class RefreshDIContact {
    @FragmentScope
    @Subcomponent(modules = arrayOf(
            RefreshModule::class
    ))
    interface RefreshComponent {
        fun injectTo(refreshFragment: RefreshFragment)
    }

    //------------------------------- line ----------------------------------

    @Module
    class RefreshModule {

    }
}