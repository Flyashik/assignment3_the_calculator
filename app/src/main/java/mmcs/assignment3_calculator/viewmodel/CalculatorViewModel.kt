package mmcs.assignment3_calculator.viewmodel

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import mmcs.assignment3_calculator.R
import kotlin.math.log

class CalculatorViewModel: BaseObservable(), Calculator {
    override var display = ObservableField<String>()
    init {
        display.set("0")
    }

    private var curValue = "0"
    private var curOp = ""
    private var resultVal = "0"
    private var reset = false

    override fun addDigit(dig: Number)
    {
        if(resultVal == "0" || resultVal == "Infinity" || resultVal == "NaN" || reset) {
            resultVal = ""
            reset = false
        }
        resultVal += dig.toString()
        display.set(resultVal)
    }

    override fun addPoint() {
        if(!resultVal.contains(".") && !reset)
            resultVal += "."
        display.set(resultVal)
    }

    override fun addOperation(op: Operation) {
        if(curValue != "0" && !reset)
            calculate()
        curValue = resultVal
        curOp = when(op) {
            Operation.ADD -> "+"
            Operation.MUL -> "*"
            Operation.SUB -> "-"
            Operation.DIV -> "/"
        }
        reset = true
    }

    override fun compute() {
        calculate()
        curValue = "0"
    }

    override fun invertSign() {
        var dig = resultVal.toDouble() * -1
        resultVal = dig.toString()
        display.set(resultVal)
    }

    override fun clear() {
        if(resultVal == "NaN" || resultVal == "Infinity") {
            resultVal == "0"
        } else {
            resultVal = resultVal.dropLast(1)
        }
        display.set(resultVal)
    }

    override fun reset() {
        curValue = "0"
        resultVal = "0"
        curOp = ""
        display.set("0")
    }

    private fun calculate() {
        var prevValDbl = curValue.toDouble()
        var newValDbl = resultVal.toDouble()
        resultVal = when(curOp){
            "+" -> (prevValDbl + newValDbl).toString()
            "-" -> (prevValDbl - newValDbl).toString()
            "*" -> (prevValDbl * newValDbl).toString()
            "/" -> (prevValDbl / newValDbl).toString()
            else -> resultVal
        }
        display.set(resultVal)
    }
}