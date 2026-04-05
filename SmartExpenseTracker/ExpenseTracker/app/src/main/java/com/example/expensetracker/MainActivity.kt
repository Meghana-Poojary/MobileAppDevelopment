package com.example.expensetracker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddExpense = findViewById<Button>(R.id.btnAddExpense)
        val btnSendData = findViewById<Button>(R.id.btnSendData)
        val btnImplicit = findViewById<Button>(R.id.btnImplicit)
        val btnFragment = findViewById<Button>(R.id.btnFragment)

        // 1️⃣ Navigation without sending data
        btnAddExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        // 2️⃣ Navigation WITH sending data
        btnSendData.setOnClickListener {
            val intent = Intent(this, SummaryActivity::class.java)
            intent.putExtra("amount", "₹5000")
            startActivity(intent)
        }

        // 3️⃣ Implicit Intent
        btnImplicit.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.google.com")
            startActivity(intent)
        }

        // 4️⃣ Fragment Navigation
        btnFragment.setOnClickListener {
            replaceFragment(ProfileFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}