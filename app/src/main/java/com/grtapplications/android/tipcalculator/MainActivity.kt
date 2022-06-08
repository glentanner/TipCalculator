package com.grtapplications.android.tipcalculator

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.grtapplications.android.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var splitOption: Int = 1
    private val minCost: Double = 2.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up a click listener
        binding.calculateButton.setOnClickListener { calculateTip() }
        // Hide keyboard on 'enter'
        binding.costOfService.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }
        // Listen for Split Slider value
        val splitSlider: Slider = binding.splitSlider
        splitSlider.addOnChangeListener { _, value, _ ->
            splitOption = value.roundToInt()
            if (binding.totalResult.text.isNotEmpty()) {
                calculateTip()
            }
        }
        // Listen for Switch
        val roundupSwitch: SwitchMaterial = binding.roundUpSwitch
        roundupSwitch.setOnCheckedChangeListener { _, _ ->
            if (binding.totalResult.text.isNotEmpty()) {
                calculateTip()
            }
        }
        // On startup, focus on Cost TextField
        binding.costOfService.requestFocus()
    }

    private fun calculateTip() {
        // Get the cost of service as text, convert the string to a double (decimal)
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        // If there is no amount entered, remind the user to enter an amount
        if (cost == null) {
            val snack = Snackbar.make(
                binding.root,
                "Enter an amount to calculate tip",
                Snackbar.LENGTH_LONG
            )
            snack.setAction("DISMISS", View.OnClickListener {
            })
            snack.show()
            binding.tipResult.text = ""
            binding.totalResult.text = ""
            binding.numChecks.text = ""
            return
        }
        // Get tip percentage from the user
        val tipPercentage =
            when (binding.tipOptions.checkedRadioButtonId) {
                R.id.option_twenty_five_percent -> 0.25
                R.id.option_twenty_percent -> 0.20
                R.id.option_eighteen_percent -> 0.18
                R.id.option_fifteen_percent -> 0.15
                else -> 0.15
            }
        //
        if (cost * tipPercentage < minCost) {
            val snack =
                Snackbar.make(binding.root, "Be nice. Minimal tip is $2", Snackbar.LENGTH_LONG)
            snack.setAction("DISMISS", View.OnClickListener {
                binding.costOfService.text!!.clear()
            })
            snack.show()
            binding.tipResult.text = ""
            binding.totalResult.text = ""
            binding.numChecks.text = ""
            return
        }
        // Calculate tip
        var tip = (tipPercentage * cost)
        // Round up if desired
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        // Calculate total for each check
        val total = (cost + tip) / splitOption
        // Format currency according to user settings
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        val formattedTotal = NumberFormat.getCurrencyInstance().format(total)
        // Set text fields
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
        binding.totalResult.text = getString(R.string.total_amount, formattedTotal)
        binding.numChecks.text = getString(R.string.num_checks, splitOption.toString())
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}