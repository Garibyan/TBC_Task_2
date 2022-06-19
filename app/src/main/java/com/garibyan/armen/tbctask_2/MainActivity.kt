package com.garibyan.armen.tbctask_2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.garibyan.armen.tbctask_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var inputNumber: String

    lateinit var listBelong20: Array<String?>
    lateinit var listOfTwenties: Array<String?>
    lateinit var listOfHundreds: Array<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listBelong20 = resources.getStringArray(R.array.belong20)
        listOfTwenties = resources.getStringArray(R.array.twenties)
        listOfHundreds = resources.getStringArray(R.array.hondreds)

        binding.edtNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.toString().length == 1 && s.toString().startsWith("0")) {
                    s.clear()
                }
            }
        })

        binding.btnCast.setOnClickListener {
            inputNumber = binding.edtNumber.text.toString()

            if (inputNumber.equals("") || inputNumber.toInt() > 1000 || inputNumber.toInt() < 1) {
                Toast.makeText(this, R.string.incorrect_input, Toast.LENGTH_SHORT).show()
                binding.edtNumber.error = resources.getString(R.string.edt_number_error)
            } else {
                binding.tvNumber.setText(numberToString(inputNumber.toInt()))
                Toast.makeText(this, R.string.correct_cast, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun numberToString(number: Int): String {
        var answer = ""
        val devideByHundred = number / 100
        val moduleByHundred = number % 100

        when (moduleByHundred) {
            0 -> answer = listOfHundreds.get(devideByHundred - 1)!!
            in 1..99 -> {
                if (devideByHundred != 0)
                    answer =
                        listOfHundreds.get(devideByHundred - 1)?.dropLast(1) +
                                withoutHundreds(moduleByHundred)
                else answer = withoutHundreds(moduleByHundred)
            }
        }
        return answer
    }

    fun withoutHundreds(number: Int): String {
        var answer = ""
        val devideByTwenty = number / 20
        val moduleByTwenty = number % 20


        if (number < 20) {
            answer = listBelong20.get(number - 1)!!
        } else if (moduleByTwenty == 0) {
            answer = listOfTwenties.get(devideByTwenty - 1)!!
        } else if (moduleByTwenty != 0) {
            var x = listOfTwenties.get(devideByTwenty - 1)?.dropLast(1) + "და"
            answer = x + listBelong20.get(moduleByTwenty - 1)
        }
        return answer
    }
}