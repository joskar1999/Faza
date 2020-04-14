package com.example.moontracker.utils

import kotlin.math.floor
import kotlin.math.roundToInt

abstract class TrigPhaseCalc : PhaseCalc {

    protected fun julDay(year: Int, month: Int, day: Int): Int {
        var y = year
        val ja: Double
        if (year < 0) {
            y++
        }
        var jy = y
        var jm = month + 1
        if (month <= 2) {
            jy--
            jm += 12
        }
        var jul = floor(365.25 * jy) + floor(30.6001 * jm) + day + 1720995
        if (day + 31 * (month + 12 * year) >= (15 + 31 * (10 + 12 * 1582))) {
            ja = floor(0.01 * jy)
            jul = jul + 2 - ja + floor(0.25 * ja)
        }
        return jul.roundToInt()
    }
}