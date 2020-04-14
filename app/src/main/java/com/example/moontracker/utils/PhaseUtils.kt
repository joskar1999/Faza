package com.example.moontracker.utils

import com.example.moontracker.constants.Constants.PHASE_IMAGES
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList

class PhaseUtils {

    fun getPreviousNewMoon(date: LocalDate, calc: PhaseCalc): String {
        var d = date
        var days: Long = 1
        while (calc.calculatePhase(d.year, d.monthValue, d.dayOfMonth) != 1) {
            d = date.minusDays(days++)
        }
        return "${d.dayOfMonth}.${d.monthValue}.${d.year}"
    }

    fun getNextFullMoon(date: LocalDate, calc: PhaseCalc): String {
        var d = date
        var days: Long = 1
        while (calc.calculatePhase(d.year, d.monthValue, d.dayOfMonth) != 15) {
            d = date.plusDays(days++)
        }
        return "${d.dayOfMonth}.${d.monthValue}.${d.year}"
    }

    fun getAllFullMoonsYearly(date: LocalDate, calc: PhaseCalc): ArrayList<String> {
        var d = date.minusDays(date.dayOfYear.toLong() - 1)
        val daysAmount = ChronoUnit.DAYS.between(d, d.plusYears(1))
        val fullMoons = ArrayList<String>()
        for (i in 0 until daysAmount) {
            if (calc.calculatePhase(d.year, d.monthValue, d.dayOfMonth) == 15) {
                fullMoons.add("${d.dayOfMonth}.${d.monthValue}.${d.year}")
            }
            d = d.plusDays(1)
        }
        return fullMoons
    }

    fun mapPhaseToDrawable(phase: Int, hemisphere: String): String {
        val suffix = if (phase <= 15) "p" else "pm"
        val p = if (phase == 0) 1 else if (phase in 1..15) phase else 30 - phase
        val h = hemisphere.toLowerCase(Locale.getDefault())
        return PHASE_IMAGES.filter { it.matches("^$h[0-9]{2,3}$suffix$".toRegex()) }
            .sortedByDescending { it.substring(1, it.indexOf(suffix)).toInt() }
            .first {
                it.substring(1, it.indexOf(suffix)).toDouble().div(1000).compareTo(p.toDouble().div(15)) < 0
            }
    }
}