package com.example.expensetracker1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "ExpenseDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE expenses (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT, " +
                    "amount REAL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS expenses")
        onCreate(db)
    }

    // ➕ INSERT
    fun insertExpense(expense: Expense): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", expense.title)
            put("amount", expense.amount)
        }
        return db.insert("expenses", null, values)
    }

    // 📥 FETCH ALL
    fun getAllExpenses(): MutableList<Expense> {
        val list = mutableListOf<Expense>()
        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM expenses", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val title = cursor.getString(1)
                val amount = cursor.getDouble(2)

                list.add(Expense(id, title, amount))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }

    // ❌ DELETE
    fun deleteExpense(id: Int) {
        val db = writableDatabase
        db.delete("expenses", "id=?", arrayOf(id.toString()))
    }

    // ✏️ UPDATE
    fun updateExpense(expense: Expense) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", expense.title)
            put("amount", expense.amount)
        }
        db.update("expenses", values, "id=?", arrayOf(expense.id.toString()))
    }
}