package com.example.expensetracker1

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ExpenseAdapter
    private lateinit var dbHelper: DBHelper
    private val expenses = mutableListOf<Expense>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)

        dbHelper = DBHelper(this)

        // 📥 LOAD DATA FROM SQLITE
        expenses.addAll(dbHelper.getAllExpenses())

        adapter = ExpenseAdapter(expenses) { position ->
            editExpense(position)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        updateTotal(tvTotal)

        // ➕ ADD
        btnAdd.setOnClickListener {
            val title = etTitle.text.toString()
            val amount = etAmount.text.toString().toDoubleOrNull()

            if (title.isNotEmpty() && amount != null) {
                val expense = Expense(title = title, amount = amount)

                val id = dbHelper.insertExpense(expense).toInt()
                expense.id = id

                expenses.add(expense)
                adapter.notifyItemInserted(expenses.size - 1)
                updateTotal(tvTotal)

                etTitle.text.clear()
                etAmount.text.clear()
            } else {
                Toast.makeText(this, "Enter valid data", Toast.LENGTH_SHORT).show()
            }
        }

        // 👉 SWIPE DELETE
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.bindingAdapterPosition

                val expense = expenses[pos]
                dbHelper.deleteExpense(expense.id)

                expenses.removeAt(pos)
                adapter.notifyItemRemoved(pos)
                updateTotal(tvTotal)
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    // ✏️ EDIT
    private fun editExpense(position: Int) {
        val expense = expenses[position]
        expense.amount += 10

        dbHelper.updateExpense(expense)

        adapter.notifyItemChanged(position)
        updateTotal(findViewById(R.id.tvTotal))
    }

    private fun updateTotal(tv: TextView) {
        val total = expenses.sumOf { it.amount }
        tv.text = "Total: ₹$total"
    }
}