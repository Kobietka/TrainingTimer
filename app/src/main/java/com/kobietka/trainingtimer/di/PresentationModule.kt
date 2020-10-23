package com.kobietka.trainingtimer.di

import android.content.Context
import com.kobietka.trainingtimer.presentaion.common.BaseActivity
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.MainActivity
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module
class PresentationModule(private val activity: BaseActivity? = null, private val baseFragment: BaseFragment? = null) {

    @Provides
    @Named("ActivityContext")
    fun provideActivityContext(): Context {
        return activity!!
    }

    @Provides
    fun provideBaseFragment(): BaseFragment {
        return baseFragment!!
    }

}