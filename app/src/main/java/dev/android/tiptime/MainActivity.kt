package dev.android.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.android.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    private fun calculateTip() {

        // get cost of service
        val stringInCostOfService = binding.costOfService.text.toString()
        val cost = stringInCostOfService.toDoubleOrNull()
        if (cost == null) {
            displayTip(0.0)
            return
        }

        // get percentage
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // calculate tip
        var tip = cost * tipPercentage

        // check if we need round up the tip
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        // show tip in view
        displayTip(tip)

    }

    private fun displayTip (tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}