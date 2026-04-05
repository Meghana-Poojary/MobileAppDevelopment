package com.example.expensetracker

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val tvAmount = findViewById<TextView>(R.id.tvAmount)
        val btnBack = findViewById<Button>(R.id.btnBackHomeSummary)

        val amount = intent.getStringExtra("amount")

        tvAmount.text = "Expense Entered: ₹$amount"

        btnBack.setOnClickListener {
            finish()
        }
    }
}