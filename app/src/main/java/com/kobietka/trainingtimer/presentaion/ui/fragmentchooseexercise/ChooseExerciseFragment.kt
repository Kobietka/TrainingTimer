package com.kobietka.trainingtimer.presentaion.ui.fragmentchooseexercise

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.ChooseExerciseAdapter
import kotlinx.android.synthetic.main.fragment_choose_exercise.*
import javax.inject.Inject


class ChooseExerciseFragment : BaseFragment() {

    @Inject lateinit var adapter: ChooseExerciseAdapter
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        recyclerView = view.findViewById(R.id.fragment_choose_exercise_rv)
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )

        adapter.setLifeCycleOwner(viewLifecycleOwner)
        recyclerView.adapter = adapter

        

    }

    override fun getLayout(): Int {
        return R.layout.fragment_choose_exercise
    }

}