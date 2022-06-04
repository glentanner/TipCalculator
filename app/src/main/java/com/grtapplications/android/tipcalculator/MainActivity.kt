package com.grtapplications.android.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grtapplications.android.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    /*
    To add binding:
        1. Add block to Module gradle.build file:
               buildFeatures { viewBinding = true }
        2. Add variable
               lateinit var binding: ActivityMainBinding
        3. Add lines to onCreate()
               binding = ActivityMainBinding.inflate(layoutInflater)
               setContentView(binding.root)
        // Old way with findViewById()
        val myButton: Button = findViewById(R.id.my_button)
        myButton.text = "A button"

        // Better way with view binding
        val myButton: Button = binding.myButton
        myButton.text = "A button"

        // Best way with view binding and no extra variable
        binding.myButton.text = "A button"
     */
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up a click listener
        binding.calculateButton.setOnClickListener{ calculateTip() }
    }

    private fun calculateTip() {
        // Get the cost of service as text, convert the string to a double (decimal)
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tipResult.text = ""
            binding.totalResult.text = ""
            return
        }

        // Get tip percentage from the selected radio button
        val tipPercentage =
            when(binding.tipOptions.checkedRadioButtonId) {
                R.id.option_twenty_percent -> 0.20
                R.id.option_eighteen_percent -> 0.18
                R.id.option_fifteen_percent -> 0.15
                else -> 0.15
            }
        // Calculate tip
        var tip = tipPercentage * cost
        // Round up if desired
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        // Format currency according to user settings
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        val formattedTotal = NumberFormat.getCurrencyInstance().format(cost+tip)

        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
        binding.totalResult.text = getString(R.string.total_amount, formattedTotal)
    }
}