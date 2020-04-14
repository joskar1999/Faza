package com.example.moontracker.utils

import java.util.*
import kotlin.math.floor


class BasicPhaseCalc : PhaseCalc {

    override fun calculatePhase(year: Int, month: Int, day: Int): Int {
        val lp = 2551443
        val now = Date(year, month - 1, day, 20, 35, 0)
        val newMoon = Date(1970, 0, 7, 20, 35, 0)
        val phase = (now.time - newMoon.time) / 1000 % lp
        return (floor(phase / (24 * 3600).toDouble())).toInt()
    }
}