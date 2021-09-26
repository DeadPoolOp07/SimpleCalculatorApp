package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var lastNumeric = false
    private var lastDot = false


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun onDigit(view: View){

        binding.input.append((view as Button).text)
        lastNumeric = true

    }

    fun onClear(view: View){

        binding.input.text=""
        lastNumeric = false
        lastDot = false

    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            binding.input.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {

        if (lastNumeric) {

            var value = binding.input.text.toString()
            var prefix = ""
            try {

                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1)
                }

                when {
                    value.contains("/") -> {

                        val splitedValue = value.split("/")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        binding.input.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                    value.contains("x") -> {

                        val splitedValue = value.split("x")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }


                        binding.input.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                    value.contains("-") -> {


                        val splitedValue = value.split("-")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }


                        binding.input.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    value.contains("+") -> {

                        val splitedValue = value.split("+")

                        var one = splitedValue[0]
                        val two = splitedValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }


                        binding.input.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    fun onOperator(view: View){

        if(lastNumeric && !isOperatorAdded(binding.input.text.toString())) {
            binding.input.append((view as Button).text)
            lastNumeric = false
            lastDot = false

        }
    }

    private fun isOperatorAdded(value : String) : Boolean{
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("x")
                    || value.contains("+") || value.contains("-")
        }
    }

    private fun removeZeroAfterDot(result: String): String {

        var value = result

        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }

}