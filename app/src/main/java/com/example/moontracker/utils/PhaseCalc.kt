package com.example.moontracker.utils

@FunctionalInterface
interface PhaseCalc {

    fun calculatePhase(year: Int, month: Int, day: Int): Int
}