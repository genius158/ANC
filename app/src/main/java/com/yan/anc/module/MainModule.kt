package com.yan.anc.module

import com.yan.anc.di.ActivityScope
import com.yan.anc.utils.RetrofitHelper
import dagger.Module
import dagger.Provides

/**
 * Created by yan on 2017/11/5.
 */
@Module
class MainModule(private val mainView: MainMVPContact.MainBaseView) {
    @Provides
    @ActivityScope
    fun provideMainPresenter(retrofitHelper: RetrofitHelper): MainMVPContact.MainPresenter {
        return MainMVPContact.MainPresenter(mainView,retrofitHelper)
    }
}