package com.example.cecs453lab3.model

import java.text.DecimalFormat
import kotlin.math.pow

class Mortgage {
    private val MONEY: DecimalFormat = DecimalFormat("$#,##0.00")
    private var amount: Float = 0f
    private var years: Int = 0
    private var rate: Float = 0f

    init {
        setAmount(100000.0f)
        setYears(30)
        setRate(0.035f)
    }

    // --- Setters (with basic validation) ---
    fun setAmount(newAmount: Float) {
        if (newAmount >= 0) amount = newAmount
    }

    fun setYears(newYears: Int) {
        if (newYears > 0) years = newYears
    }

    fun setRate(newRate: Float) {
        if (newRate >= 0) rate = newRate
    }

    // --- Getters ---
    fun getAmount(): Float = amount
    fun getYears(): Int = years
    fun getRate(): Float = rate

    // --- Calculation Logic ---
    fun monthlyPayment(): Float {
        if (amount == 0f || years == 0 || rate == 0f) return 0f

        val mRate = rate / 12
        val totalMonths = (years * 12).toDouble()
        // Math.pow((1 / (1 + mRate)) toDouble()
        val temp = (1.0 / (1.0 + mRate.toDouble())).pow(totalMonths)

        return (amount * mRate / (1.0 - temp)).toFloat()
    }

    fun totalPayment(): Float {
        return monthlyPayment() * years * 12
    }

    // --- Formatted Getters ---
    fun getFormattedAmount(): String? = MONEY.format(amount)
    fun getFormattedRate(): String = String.format("%.3f", rate) // .035
    fun formattedMonthlyPayment(): String? = MONEY.format(monthlyPayment())
    fun formattedTotalPayment(): String? = MONEY.format(totalPayment())
    fun getFormattedYears(): String = years.toString()
}