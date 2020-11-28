package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.StatisticsViewModel
import com.kobietka.trainingtimer.repositories.WeekRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Provider


class StatisticsAdapter
@Inject constructor(private val weekRepository: WeekRepository,
                    private val viewModelProvider: Provider<StatisticsViewModel>) : RecyclerView.Adapter<StatisticsViewHolder>() {

    private val compositeDisposable = CompositeDisposable()

    private var ids = listOf<Int>()
    private lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var onClick: (Int) -> Unit

    private fun updateIds(newIds: List<Int>){
        ids = newIds
        notifyDataSetChanged()
    }

    fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner){
        this.lifecycleOwner = lifecycleOwner
    }

    fun onItemClick(function: (Int) -> Unit){
        onClick = function
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_statistics_entry, parent, false)

        return StatisticsViewHolder(view, viewModelProvider.get(), lifecycleOwner)
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        holder.viewModel.switchId(ids[position])
        holder.itemView.setOnClickListener {
            onClick.invoke(ids[position])
        }
    }

    override fun getItemCount(): Int {
        return ids.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        compositeDisposable.add(
            weekRepository.getAllIds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateIds)
        )
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        compositeDisposable.clear()
    }

    override fun onViewAttachedToWindow(holder: StatisticsViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: StatisticsViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }

}