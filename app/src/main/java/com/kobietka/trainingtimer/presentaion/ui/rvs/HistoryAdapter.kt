package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.HistoryViewModel
import com.kobietka.trainingtimer.repositories.HistoryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Provider


class HistoryAdapter
@Inject constructor(private val historyRepository: HistoryRepository,
                    private val viewModelProvider: Provider<HistoryViewModel>) : RecyclerView.Adapter<HistoryViewHolder>() {

    val compositeDisposable = CompositeDisposable()
    var ids = listOf<Int>()
    lateinit var lifecycleOwner: LifecycleOwner

    fun updateList(list: List<Int>){
        ids = list
        notifyDataSetChanged()
    }

    fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner){
        this.lifecycleOwner = lifecycleOwner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_history_entry, parent, false)

        return HistoryViewHolder(view, viewModelProvider.get(), lifecycleOwner)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.viewModel.switchId(ids[position])
    }

    override fun getItemCount(): Int {
        return ids.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        compositeDisposable.add(
            historyRepository.getAllIds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateList)
        )
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        compositeDisposable.clear()
    }

    override fun onViewAttachedToWindow(holder: HistoryViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: HistoryViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }


}