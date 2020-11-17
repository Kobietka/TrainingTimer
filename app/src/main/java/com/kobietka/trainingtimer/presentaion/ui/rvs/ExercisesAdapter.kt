package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.ExerciseViewModel
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Provider


class ExercisesAdapter
     @Inject constructor(private val repository: ExerciseRepository,
                         private val modelProvider: Provider<ExerciseViewModel>) : RecyclerView.Adapter<ExerciseViewHolder>() {

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

    fun onDeleteClicks(function: (position: Int) -> Unit){
        onDeleteClick = function
    }

    fun onEditClicks(function: (position: Int) -> Unit){
        onEditClick = function
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyclew_view_exercise_entry, parent, false)

        val viewModel = modelProvider.get()

        return ExerciseViewHolder(view, viewModel , lifecycleOwner)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.viewModel.switchId(ids[position])
        holder.itemView.findViewById<LinearLayout>(R.id.fragment_exercises_entry_edit_icon).setOnClickListener {
            onEditClick(ids[position])
        }
        holder.itemView.findViewById<LinearLayout>(R.id.fragment_exercises_entry_delete_icon).setOnClickListener {
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

    override fun onViewAttachedToWindow(holder: ExerciseViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: ExerciseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }


}