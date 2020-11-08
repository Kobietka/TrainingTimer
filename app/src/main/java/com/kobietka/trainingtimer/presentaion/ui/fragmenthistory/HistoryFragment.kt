package com.kobietka.trainingtimer.presentaion.ui.fragmenthistory

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.HistoryAdapter
import kotlinx.android.synthetic.main.fragment_history.*
import javax.inject.Inject


class HistoryFragment : BaseFragment() {

    @Inject lateinit var adapter: HistoryAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        recyclerView = view.findViewById(R.id.fragment_history_rv)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        adapter.setLifeCycleOwner(viewLifecycleOwner)
        recyclerView.adapter = adapter

        fragment_history_back_arrow.setOnClickListener {
            navController.navigate(R.id.action_historyFragment_to_mainFragment)
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_history
    }

}