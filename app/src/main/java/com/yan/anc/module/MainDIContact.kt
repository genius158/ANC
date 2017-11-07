package com.yan.anc.module

import com.yan.anc.di.ActivityScope
import com.yan.anc.utils.RetrofitHelper
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by yan on 2017/11/5.
 */
class MainDIContact {

    @ActivityScope
    @Subcomponent(modules = arrayOf(
            MainModule::class
    ))
    interface MainComponent {
        fun injectTo(mainActivity: MainActivity)
    }

    //-------------------------- line -----------------------------

    @Module
    class MainModule(private val mainView: MainMVPContact.MainView) {
        @Provides
        @ActivityScope
        fun provideMainPresenter(retrofitHelper: RetrofitHelper): MainMVPContact.MainPresenter {
            return MainMVPContact.MainPresenter(mainView, retrofitHelper)
        }
    }
}