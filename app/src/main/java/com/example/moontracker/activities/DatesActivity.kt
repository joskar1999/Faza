package com.example.moontracker.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moontracker.R
import com.example.moontracker.adapter.DatesListAdapter
import com.example.moontracker.constants.Constants.ALGORITHM
import com.example.moontracker.constants.Constants.ALGORITHM_CONWAY
import com.example.moontracker.constants.Constants.ALGORITHM_SIMPLE
import com.example.moontracker.constants.Constants.ALGORITHM_TRIG_PRIMARY
import com.example.moontracker.constants.Constants.ALGORITHM_TRIG_SECONDARY
import com.example.moontracker.constants.Constants.APPLICATION_SETTINGS
import com.example.moontracker.constants.Constants.DEFAULT_ALGORITHM
import com.example.moontracker.utils.*
import kotlinx.android.synthetic.main.activity_dates.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DatesActivity : AppCompatActivity() {

    private var calculator: PhaseCalc? = null
    private var sharedPreferences: SharedPreferences? = null
    private var fullMoonDates = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dates)

        sharedPreferences = this.getSharedPreferences(APPLICATION_SETTINGS, Context.MODE_PRIVATE)

        dates_list.layoutManager = LinearLayoutManager(this)
        dates_list.adapter =
            DatesListAdapter(getFullMoonsYearly(LocalDate.now().year.toString(), BasicPhaseCalc()), this)

        year_input.setText(LocalDate.now().year.toString())
        year_input.addTextChangedListener {
            dates_list.adapter = DatesListAdapter(getFullMoonsYearly(it.toString(), BasicPhaseCalc()), this)
        }
    }

    override fun onResume() {
        super.onResume()
        getPhaseCalculator()
    }

    fun onYearUpdate(view: View) {
        if (view is Button) {
            when (view.id) {
                R.id.plus_button -> updateYear(1)
                R.id.minus_button -> updateYear(-1)
            }
        }
    }

    private fun updateYear(value: Int) {
        if (year_input.text.matches("^[0-9]+$".toRegex())) {
            year_input.setText((year_input.text.toString().toInt() + value).toString())
        }
    }

    private fun getFullMoonsYearly(year: String, calc: PhaseCalc): ArrayList<String> {
        if (year.matches("[0-9]{4}".toRegex())) {
            val date = LocalDate.parse("${year}0101", DateTimeFormatter.BASIC_ISO_DATE)
            fullMoonDates = PhaseUtils().getAllFullMoonsYearly(date, calc)
        }
        return fullMoonDates
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
}
