package com.example.moontracker.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moontracker.R
import com.example.moontracker.constants.Constants.ALGORITHM
import com.example.moontracker.constants.Constants.ALGORITHM_CONWAY
import com.example.moontracker.constants.Constants.ALGORITHM_SIMPLE
import com.example.moontracker.constants.Constants.ALGORITHM_TRIG_PRIMARY
import com.example.moontracker.constants.Constants.ALGORITHM_TRIG_SECONDARY
import com.example.moontracker.constants.Constants.APPLICATION_SETTINGS
import com.example.moontracker.constants.Constants.DEFAULT_ALGORITHM
import com.example.moontracker.constants.Constants.HEMISPHERE
import com.example.moontracker.constants.Constants.HEMISPHERE_NORTH
import com.example.moontracker.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private var calculator: PhaseCalc? = null
    private var sharedPreferences: SharedPreferences? = null
    private val now = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences(APPLICATION_SETTINGS, Context.MODE_PRIVATE)

        full_moons_yearly.setOnClickListener {
            val intent = Intent(this, DatesActivity::class.java)
            startActivity(intent)
        }

        send_to_settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getPhaseCalculator()
        calculateLunarData()
        setMoonImage()
    }

    private fun getPhaseCalculator() {
        calculator = when (sharedPreferences!!.getString(ALGORITHM, DEFAULT_ALGORITHM)) {
            ALGORITHM_SIMPLE -> BasicPhaseCalc()
            ALGORITHM_CONWAY -> ConwayPhaseCalc()
            ALGORITHM_TRIG_PRIMARY -> PrimaryTrigPhaseCalc()
            ALGORITHM_TRIG_SECONDARY -> SecondaryTrigPhaseCalc()
            else -> BasicPhaseCalc()
        }
    }

    private fun getDailyPercentage(): String {
        val phase = calculator!!.calculatePhase(now.year, now.monthValue, now.dayOfMonth)
        val percentage = ((phase.toDouble() / 29) * 100).roundToInt()
        return "Dzisiaj: $percentage%"
    }

    private fun calculateLunarData() {
        phase_percentage.text = getDailyPercentage()
        new_moon.text = PhaseUtils().getPreviousNewMoon(now, calculator!!)
        full_moon.text = PhaseUtils().getNextFullMoon(now, calculator!!)
    }

    private fun setMoonImage() {
        val hemisphere = sharedPreferences!!.getString(HEMISPHERE, HEMISPHERE_NORTH)
        val image = PhaseUtils().mapPhaseToDrawable(
            calculator!!.calculatePhase(now.year, now.monthValue, now.dayOfMonth),
            hemisphere!!
        )
        moon_image.setImageURI(Uri.parse("android.resource://${this.packageName}/drawable/$image"))
    }
}
