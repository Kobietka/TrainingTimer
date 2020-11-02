package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.AddWorkoutViewModel
import com.kobietka.trainingtimer.presentaion.viewmodels.ExerciseViewModel
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import com.kobietka.trainingtimer.repositories.WorkoutRelationRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Provider


class AddWorkoutAdapter
@Inject constructor(private val repository: WorkoutRelationRepository,
                    private val modelProvider: Provider<AddWorkoutViewModel>) : RecyclerView.Adapter<AddWorkoutViewHolder>() {

    var ids = listOf<Int>()
    private val compositeDisposable = CompositeDisposable()
    lateinit var lifecycleOwner: LifecycleOwner
    var workout = 0

    private fun updateList(idsList: List<Int>){
        ids = idsList
        this.notifyDataSetChanged()
    }

    fun setWorkoutId(id: Int){
        workout = id
    }

    fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner){
        this.lifecycleOwner = lifecycleOwner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddWorkoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_create_workout_entry, parent, false)

        val viewModel = modelProvider.get()

        /*view.findViewById<ImageView>(R.id.fragment_create_workout_entry_delete_icon).setOnClickListener {
            viewModel.onDeleteClick()
        }*/

        return AddWorkoutViewHolder(view, viewModel , lifecycleOwner)
    }

    override fun onBindViewHolder(holder: AddWorkoutViewHolder, position: Int) {
        holder.viewModel.switchId(ids[position])
    }

    override fun getItemCount(): Int {
        return ids.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        compositeDisposable.add(
            repository.getRelationIdsByWorkoutId(workout)
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

    override fun onViewAttachedToWindow(holder: AddWorkoutViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: AddWorkoutViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }

}