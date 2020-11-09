package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.WorkoutsViewModel
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Provider


class WorkoutsAdapter
@Inject constructor(private val repository: WorkoutRepository,
                    private val modelProvider: Provider<WorkoutsViewModel>) : RecyclerView.Adapter<WorkoutsViewHolder>(){

    var ids = listOf<Int>()
    private val compositeDisposable = CompositeDisposable()
    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var onEditClick: (position: Int) -> Unit
    lateinit var onDeleteClick: (position: Int) -> Unit

    private fun updateList(idsList: List<Int>){
        ids = idsList
        this.notifyDataSetChanged()
    }

    fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner){
        this.lifecycleOwner = lifecycleOwner
    }

    fun onEditClicks(function: (position: Int) -> Unit){
        onEditClick = function
    }

    fun onDeleteClicks(function: (position: Int) -> Unit){
        onDeleteClick = function
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_workout_entry, parent, false)

        val viewModel = modelProvider.get()

        return WorkoutsViewHolder(view, viewModel, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: WorkoutsViewHolder, position: Int) {
        holder.viewModel.switchId(ids[position])
        holder.itemView.findViewById<LinearLayout>(R.id.fragment_workout_entry_edit_icon).setOnClickListener {
            onEditClick(ids[position])
        }
        holder.itemView.findViewById<LinearLayout>(R.id.fragment_workout_entry_delete_icon).setOnClickListener {
            onDeleteClick(ids[position])
        }
    }

    override fun getItemCount(): Int {
        return ids.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        compositeDisposable.add(
            repository.getAllIds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateList)
        )
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        compositeDisposable.clear()
    }

    override fun onViewAttachedToWindow(holder: WorkoutsViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: WorkoutsViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }

}