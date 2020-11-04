package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
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
                    private val modelProvider: Provider<EditWorkoutViewModel>,
                    private val launchEvents: Observable<EventType>) : RecyclerView.Adapter<EditWorkoutViewHolder>() {

    var ids = listOf<Int>()
    private val compositeDisposable = CompositeDisposable()
    lateinit var lifecycleOwner: LifecycleOwner
    private val workoutIds = BehaviorSubject.create<Int>().toSerialized()

    init {
        compositeDisposable.add(
            launchEvents.subscribe {
                if(it.clickId == ClickId.EditWorkout) workoutIds.onNext(it.itemId)
                if(it.clickId == ClickId.EditWorkoutFromChoosing) workoutIds.onNext(it.itemId)
            }
        )
    }

    private fun updateList(idsList: List<Int>){
        ids = idsList
        this.notifyDataSetChanged()
    }

    fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner){
        this.lifecycleOwner = lifecycleOwner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditWorkoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_edit_workout_entry, parent, false)

        val viewModel = modelProvider.get()
        view.findViewById<ImageView>(R.id.fragment_edit_workout_entry_delete_icon).setOnClickListener {
            viewModel.onDeleteClick()
        }

        return EditWorkoutViewHolder(view, viewModel, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: EditWorkoutViewHolder, position: Int) {
        holder.viewModel.switchId(ids[position])
    }

    override fun getItemCount(): Int {
        return ids.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        workoutIds.withLatestFrom(launchEvents, { workoutId, eventType ->
            workoutRelationRepository.getRelationIdsByWorkoutId(workoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    updateList(it)
                }
        }).subscribe()
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