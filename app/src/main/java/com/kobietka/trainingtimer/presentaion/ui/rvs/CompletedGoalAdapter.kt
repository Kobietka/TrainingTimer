package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.ActiveGoalViewModel
import com.kobietka.trainingtimer.presentaion.viewmodels.CompletedGoalViewModel
import com.kobietka.trainingtimer.repositories.ActiveGoalRepository
import com.kobietka.trainingtimer.repositories.CompletedGoalRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Provider

class CompletedGoalAdapter
@Inject constructor(private val completedGoalRepository: CompletedGoalRepository,
                    private val viewModelProvider: Provider<CompletedGoalViewModel>): RecyclerView.Adapter<CompletedGoalViewHolder>() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var lifecycleOwner: LifecycleOwner
    private var ids = listOf<Int>()

    fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner){
        this.lifecycleOwner = lifecycleOwner
    }

    private fun updateList(newList: List<Int>){
        ids = newList.reversed()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedGoalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_completed_goal_entry, parent, false)

        return CompletedGoalViewHolder(view, viewModelProvider.get(), lifecycleOwner)
    }

    override fun onBindViewHolder(holder: CompletedGoalViewHolder, position: Int) {
        holder.viewModel.switchId(ids[position])
    }

    override fun getItemCount(): Int {
        return ids.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        compositeDisposable.add(
            completedGoalRepository.getAllIds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateList)
        )
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        compositeDisposable.clear()
    }

    override fun onViewAttachedToWindow(holder: CompletedGoalViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: CompletedGoalViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }


}