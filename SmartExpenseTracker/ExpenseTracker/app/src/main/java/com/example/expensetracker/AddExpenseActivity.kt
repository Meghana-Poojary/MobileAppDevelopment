package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val etAmount = findViewById<EditText>(R.id.etAmount)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitExpense)
        val btnBack = findViewById<Button>(R.id.btnBackHome)

        // Submit Expense → Send data to SummaryActivity
        btnSubmit.setOnClickListener {

            val amount = etAmount.text.toString()

            if (amount.isEmpty()) {
                Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, SummaryActivity::class.java)
                intent.putExtra("amount", amount)
                startActivity(intent)
            }
        }

        // Back to Home
        btnBack.setOnClickListener {
            finish()
        }
    }
}