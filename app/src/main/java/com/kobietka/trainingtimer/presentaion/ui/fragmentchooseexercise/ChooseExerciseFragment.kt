package com.kobietka.trainingtimer.presentaion.ui.fragmentchooseexercise

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.models.WorkoutAddExerciseEvent
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.ChooseExerciseAdapter
import com.kobietka.trainingtimer.presentaion.viewmodels.ChooseExerciseUIViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fragment_choose_exercise.*
import javax.inject.Inject



class ChooseExerciseFragment : BaseFragment() {

    val compositeDisposable = CompositeDisposable()
    @Inject lateinit var adapter: ChooseExerciseAdapter
    @Inject lateinit var launchEvents: Observable<EventType>
    @Inject lateinit var workoutIdSender: Subject<Int>
    lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    @Inject lateinit var viewModel: ChooseExerciseUIViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        compositeDisposable.add(
            launchEvents.subscribe {
                if(it.clickId == ClickId.AddExercise){
                    workoutIdSender.onNext(it.itemId)
                    viewModel.switchWorkoutId(it.itemId)
                }
            }
        )

        fragment_choose_exercise_back_arrow.setOnClickListener {
            viewModel.onBackPressed()
        }

        recyclerView = view.findViewById(R.id.fragment_choose_exercise_rv)
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )

        adapter.setLifeCycleOwner(viewLifecycleOwner)
        recyclerView.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_choose_exercise
    }

}