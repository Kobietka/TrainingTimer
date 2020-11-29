package com.kobietka.trainingtimer.presentaion.viewmodels

import android.util.Log
import com.kobietka.trainingtimer.data.WeekEntity
import com.kobietka.trainingtimer.repositories.CompletedWorkoutRepository
import com.kobietka.trainingtimer.repositories.WeekRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class MainActivityViewModel
@Inject constructor(private val weekRepository: WeekRepository,
                    private val completedWorkoutRepository: CompletedWorkoutRepository){

    val compositeDisposable = CompositeDisposable()
    lateinit var onNewWeek: (String) -> Unit

    fun setActionOnNewWeek(function: (String) -> Unit){
        onNewWeek = function
    }

    private fun ifNoWeeks(){
        val day = getDayNumber()
        val month = getMonthNumber()
        val year = getYearNumber()
        compositeDisposable.add(
            weekRepository.insert(
                WeekEntity(null,
                    true,
                    calculateDateRange(day, month, year),
                    day,
                    month,
                    year)
            ).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe()
        )
    }

    private fun getActiveWeekId(){
        compositeDisposable.add(
            weekRepository.getActiveWeekId(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    loadWeek(it)
                }
        )
    }

    private fun loadWeek(id: Int){
        compositeDisposable.add(
            weekRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    checkWeek(it)
                }
        )
    }

    private fun checkWeek(activeWeek: WeekEntity){
        val now = GregorianCalendar(getYearNumber(), getMonthNumber(), getDayNumber())
        val newWeek = calculateNewWeek(activeWeek)
        val weekCal = GregorianCalendar(newWeek.startYear, newWeek.startMonth, newWeek.startDay)

        if(!now.before(weekCal) && activeWeek.id != null){
            compositeDisposable.add(
                weekRepository.insert(newWeek)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )

            compositeDisposable.add(
                weekRepository.updateActiveStatus(false, activeWeek.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        getActiveWeekId()
                    }
            )

            compositeDisposable.add(
                completedWorkoutRepository.getAllByWeekId(activeWeek.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if(it.isNotEmpty()) onNewWeek.invoke(activeWeek.dateRange)
                    }
            )
        }
    }

    fun loadWeeks(){
        compositeDisposable.add(
            weekRepository.getAllIdsMaybe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(it.isEmpty()) ifNoWeeks()
                    else getActiveWeekId()
                }
        )
    }

    private fun calculateNewWeek(week: WeekEntity): WeekEntity {
        val cal = GregorianCalendar(week.startYear, week.startMonth, week.startDay)
        val newDay = week.startDay + 7
        val newMonth = week.startMonth + 1
        var newYear = week.startYear
        if(newDay > cal.getActualMaximum(Calendar.DAY_OF_MONTH)){
            if(week.startMonth + 1 > 11){
                newYear = week.startYear + 1
            }

            val newWeekDay = 7 - (cal.getActualMaximum(Calendar.DAY_OF_MONTH) - week.startDay + 1) + 1

            return WeekEntity(
                null,
                true,
                calculateDateRange(newWeekDay, newMonth, newYear),
                newWeekDay,
                newMonth,
                newYear
            )
        } else {
            return WeekEntity(
                null,
                true,
                calculateDateRange(newDay, week.startMonth, week.startYear),
                newDay,
                week.startMonth,
                week.startYear
            )
        }
    }

    private fun calculateDateRange(day: Int, month: Int, year: Int): String{
        var result = ""
        val cal = GregorianCalendar(year, month, day)
        lateinit var newCal: GregorianCalendar
        val endDay = day + 6;
        if(endDay > cal.getActualMaximum(Calendar.DAY_OF_MONTH)){
            val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
            val change = maxDaysInMonth - day + 1
            val dayInNewMonth =  7 - change
            newCal = if(month + 1 > 11){
                GregorianCalendar(year + 1, 0, dayInNewMonth)
            } else {
                GregorianCalendar(year, month + 1, dayInNewMonth)
            }
            result = "$day ${cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.UK).substring(0,3)} - $dayInNewMonth ${newCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.UK).substring(0,3)} "
        } else {
            val newDayInMonth = day + 6
            result = "$day ${cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.UK).substring(0,3)} - $newDayInMonth ${cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.UK).substring(0,3)} "
        }

        return result
    }

    private fun getDayNumber(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun getMonthNumber(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH)
    }

    private fun getYearNumber(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR)
    }

}