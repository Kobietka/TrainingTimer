package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.presentaion.viewmodels.EditWorkoutViewModel
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import com.kobietka.trainingtimer.repositories.WorkoutRelationRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject
import javax.inject.Provider

class EditWorkoutAdapter
@Inject constructor(private val workoutRelationRepository: WorkoutRelationRepository,
                    private val modelProvider: Provider<EditWorkoutViewModel>) : RecyclerView.Adapter<EditWorkoutViewHolder>() {

    var ids = listOf<Int>()
    private val compositeDisposable = CompositeDisposable()
    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var onItemClick: (position: Int) -> Unit
    var workoutID = 0

    private fun updateList(idsList: List<Int>){
        ids = idsList
        this.notifyDataSetChanged()
    }

    fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner){
        this.lifecycleOwner = lifecycleOwner
    }

    fun setWorkoutId(id: Int){
        workoutID = id
    }

    fun setItemClick(function: (position: Int) -> Unit){
        onItemClick = function
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditWorkoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_edit_workout_entry, parent, false)

        val viewModel = modelProvider.get()

        return EditWorkoutViewHolder(view, viewModel, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: EditWorkoutViewHolder, position: Int) {
        holder.viewModel.switchId(ids[position])
        holder.itemView.findViewById<RelativeLayout>(R.id.fragment_edit_workout_entry_delete_icon).setOnClickListener {
            onItemClick(ids[position])
        }
    }

    override fun getItemCount(): Int {
        return ids.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        compositeDisposable.add(
            workoutRelationRepository.getRelationIdsByWorkoutId(workoutID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    updateList(it)
                }
        )
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        compositeDisposable.clear()
    }

    override fun onViewAttachedToWindow(holder: EditWorkoutViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: EditWorkoutViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }
}