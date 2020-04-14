package com.example.moontracker.utils

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.sin

class SecondaryTrigPhaseCalc : TrigPhaseCalc() {

    override fun calculatePhase(year: Int, month: Int, day: Int): Int {
        val n = floor(12.37 * (year - 1900 + (1.0 * month - 0.5) / 12.0))
        val rad = 3.14159265 / 180.0
        val t = n / 1236.85
        val t2 = t * t
        val ass = 359.2242 + 29.105356 * n
        val am = 306.0253 + 385.816918 * n + 0.010730 * t2
        var extra = 0.75933 + 1.53058868 * n + (1.178e-4 - 1.55e-7 * t) * t2
        extra += (0.1734 - 3.93e-4 * t) * sin(rad * ass) - 0.4068 * sin(rad * am)
        val i = if (extra > 0.0) floor(extra) else ceil(extra - 1.0)
        val j1 = julDay(year, month, day)
        val jd = 2415020 + 28 * n + i
        return ((j1 - jd + 30) % 30).roundToInt()
    }
}