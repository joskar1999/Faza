package com.example.moontracker.utils

import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.sin

class PrimaryTrigPhaseCalc : TrigPhaseCalc() {

    override fun calculatePhase(year: Int, month: Int, day: Int): Int {
        val thisJD = julDay(year, month, day)
        val degToRad = 3.14159265 / 180
        val t2: Double
        val t3: Double
        val j0: Double
        val f0: Double
        val m0: Double
        val m1: Double
        val b1: Double
        var oldJ = 0.0
        val k0: Double = floor((year - 1900) * 12.3685)
        val t: Double = (year - 1899.5) / 100
        t2 = t * t
        t3 = t * t * t
        j0 = 2415020 + 29 * k0
        f0 = 0.0001178 * t2 - 0.000000155 * t3 + (0.75933 + 0.53058868 * k0) - (0.000837 * t + 0.000335 * t2)
        m0 = 360 * getFrac(k0 * 0.08084821133) + 359.2242 - 0.0000333 * t2 - 0.00000347 * t3
        m1 = 360 * getFrac(k0 * 0.07171366128) + 306.0253 + 0.0107306 * t2 + 0.00001236 * t3
        b1 = 360 * getFrac(k0 * 0.08519585128) + 21.2964 - 0.0016528 * t2 - 0.00000239 * t3
        var phase = 0
        var jDay = 0.0
        while (jDay < thisJD) {
            var f = f0 + 1.530588 * phase
            val m5 = (m0 + phase * 29.10535608) * degToRad
            val m6 = (m1 + phase * 385.81691806) * degToRad
            val b6 = (b1 + phase * 390.67050646) * degToRad
            f -= 0.4068 * sin(m6) + (0.1734 - 0.000393 * t) * sin(m5)
            f += 0.0161 * sin(2 * m6) + 0.0104 * sin(2.0 * b6)
            f -= 0.0074 * sin(m5 - m6) - 0.0051 * sin(m5 + m6)
            f += 0.0021 * sin(2 * m5) + 0.0010 * sin(2 * b6 - m6)
            f += 0.5 / 1440
            oldJ = jDay
            jDay = j0 + 28 * phase + floor(f)
            phase++
        }
        return ((thisJD - oldJ) % 30).roundToInt()
    }

    private fun getFrac(fr: Double): Double {
        return (fr - floor(fr))
    }
}