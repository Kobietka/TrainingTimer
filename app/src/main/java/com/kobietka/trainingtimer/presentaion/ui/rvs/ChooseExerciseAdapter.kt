package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.DragEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.WorkoutAddExerciseEvent
import com.kobietka.trainingtimer.presentaion.viewmodels.ChooseExerciseViewModel
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Provider


class ChooseExerciseAdapter
    @Inject constructor(private val repository: ExerciseRepository,
                        private val modelProvider: Provider<ChooseExerciseViewModel>) : RecyclerView.Adapter<ChooseExerciseViewHolder>() {


    var ids = listOf<Int>()
    private val compositeDisposable = CompositeDisposable()
    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var onAddClick: (position: Int) -> Unit

    private fun updateList(idsList: List<Int>){
        ids = idsList
        this.notifyDataSetChanged()
    }

    fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner){
        this.lifecycleOwner = lifecycleOwner
    }

    fun setOnAddClickFunction(function: (position: Int) -> Unit){
        onAddClick = function
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_choose_exercise_entry, parent, false)

        val viewModel = modelProvider.get()

        return ChooseExerciseViewHolder(view, viewModel, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: ChooseExerciseViewHolder, position: Int) {
        holder.viewModel.switchId(ids[position])
        holder.itemView.findViewById<ImageView>(R.id.fragment_choose_exercise_entry_add_icon).setOnClickListener {
            onAddClick(ids[position])
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

    override fun onViewAttachedToWindow(holder: ChooseExerciseViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: ChooseExerciseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }

}