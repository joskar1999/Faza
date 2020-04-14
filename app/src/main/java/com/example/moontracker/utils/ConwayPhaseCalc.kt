package com.example.moontracker.utils

import kotlin.math.floor

class ConwayPhaseCalc : PhaseCalc {

    override fun calculatePhase(year: Int, month: Int, day: Int): Int {
        var r: Double = (year % 100).toDouble()
        r %= 19
        r -= if (r > 9.0) 19.0 else 0.0
        r = r * 11 % 30 + month + day
        r += if (month < 3) 2.0 else 0.0
        r -= if (year < 2000) 4.0 else 8.3
        r = floor(r + 0.5) % 30
        return (if (r < 0.0) r + 30.0 else r).toInt()
    }
}