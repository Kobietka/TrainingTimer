package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.AttachedWorkoutViewModel
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Provider

class AttachedWorkoutAdapter
@Inject constructor(private val workoutRepository: WorkoutRepository,
                    private val viewModelProvider: Provider<AttachedWorkoutViewModel>
): RecyclerView.Adapter<AttachedWorkoutViewHolder>(){

    private val compositeDisposable = CompositeDisposable()
    private var ids = listOf<Int>()
    private lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var onItemClick: (Int) -> Unit

    private fun updateList(newList: List<Int>){
        ids = newList
        notifyDataSetChanged()
    }

    fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner){
        this.lifecycleOwner = lifecycleOwner
    }

    fun setOnClick(function: (Int) -> Unit){
        onItemClick = function
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttachedWorkoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_create_goal_entry, parent,  false)

        return AttachedWorkoutViewHolder(view, viewModelProvider.get(), lifecycleOwner)
    }

    override fun onBindViewHolder(holder: AttachedWorkoutViewHolder, position: Int) {
        holder.viewModel.switchId(ids[position])
        holder.itemView.findViewById<RelativeLayout>(R.id.fragment_create_goal_list_item).setOnClickListener {
            onItemClick.invoke(ids[position])
        }
    }

    override fun getItemCount(): Int {
        return ids.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        compositeDisposable.add(
            workoutRepository.getAllIds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateList)
        )
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        compositeDisposable.clear()
    }

    override fun onViewAttachedToWindow(holder: AttachedWorkoutViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: AttachedWorkoutViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }

}