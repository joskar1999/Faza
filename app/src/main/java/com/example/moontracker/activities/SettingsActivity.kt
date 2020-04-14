package com.example.moontracker.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.moontracker.R
import com.example.moontracker.constants.Constants.ALGORITHM
import com.example.moontracker.constants.Constants.ALGORITHM_CONWAY
import com.example.moontracker.constants.Constants.ALGORITHM_SIMPLE
import com.example.moontracker.constants.Constants.ALGORITHM_TRIG_PRIMARY
import com.example.moontracker.constants.Constants.ALGORITHM_TRIG_SECONDARY
import com.example.moontracker.constants.Constants.APPLICATION_SETTINGS
import com.example.moontracker.constants.Constants.DEFAULT_ALGORITHM
import com.example.moontracker.constants.Constants.DEFAULT_HEMISPHERE
import com.example.moontracker.constants.Constants.HEMISPHERE
import com.example.moontracker.constants.Constants.HEMISPHERE_NORTH
import com.example.moontracker.constants.Constants.HEMISPHERE_SOUTH
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPreferences = this.getSharedPreferences(APPLICATION_SETTINGS, Context.MODE_PRIVATE)
        setDefaultRadioButtons()
    }

    fun onAlgorithmSelectClicked(view: View) {
        if (view is RadioButton) {
            when (view.id) {
                R.id.alg_simple -> saveAlgorithmSelection(ALGORITHM_SIMPLE)
                R.id.alg_conway -> saveAlgorithmSelection(ALGORITHM_CONWAY)
                R.id.alg_trig_primary -> saveAlgorithmSelection(ALGORITHM_TRIG_PRIMARY)
                R.id.alg_trig_secondary -> saveAlgorithmSelection(ALGORITHM_TRIG_SECONDARY)
            }
        }
    }

    fun onSelectHemisphereClicked(view: View) {
        if (view is RadioButton) {
            when (view.id) {
                R.id.hemisphere_north -> saveHemisphereSelection(HEMISPHERE_NORTH)
                R.id.hemisphere_south -> saveHemisphereSelection(HEMISPHERE_SOUTH)
            }
        }
    }

    private fun saveAlgorithmSelection(algorithm: String) {
        with(sharedPreferences!!.edit()) {
            putString(ALGORITHM, algorithm)
            commit()
        }
    }

    private fun saveHemisphereSelection(hemisphere: String) {
        with(sharedPreferences!!.edit()) {
            putString(HEMISPHERE, hemisphere)
            commit()
        }
    }

    private fun setDefaultRadioButtons() {
        when (sharedPreferences!!.getString(ALGORITHM, DEFAULT_ALGORITHM)) {
            ALGORITHM_SIMPLE -> alg_simple.isChecked = true
            ALGORITHM_CONWAY -> alg_conway.isChecked = true
            ALGORITHM_TRIG_PRIMARY -> alg_trig_primary.isChecked = true
            ALGORITHM_TRIG_SECONDARY -> alg_trig_secondary.isChecked = true
        }
        when (sharedPreferences!!.getString(HEMISPHERE, DEFAULT_HEMISPHERE)) {
            HEMISPHERE_NORTH -> hemisphere_north.isChecked = true
            HEMISPHERE_SOUTH -> hemisphere_south.isChecked = true
        }
    }
}
