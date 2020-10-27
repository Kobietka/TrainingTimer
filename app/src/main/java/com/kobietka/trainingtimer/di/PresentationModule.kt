package com.kobietka.trainingtimer.di

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.kobietka.trainingtimer.presentaion.common.BaseActivity
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.MainActivity
import com.kobietka.trainingtimer.presentaion.ui.rvs.ExercisesAdapter
import com.kobietka.trainingtimer.presentaion.viewmodels.ExerciseViewModel
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.subjects.Subject
import javax.inject.Named


@Module
class PresentationModule(private val activity: BaseActivity? = null,
                         private val baseFragment: BaseFragment? = null) {

    @Provides
    @Named("ActivityContext")
    fun provideActivityContext(): Context {
        return activity!!
    }

    @Provides
    fun provideBaseFragment(): BaseFragment {
        return baseFragment!!
    }

    @Provides
    fun provideLaunchEvents(subject: Subject<Int>): Observable<Int> {
        return subject
    }


}