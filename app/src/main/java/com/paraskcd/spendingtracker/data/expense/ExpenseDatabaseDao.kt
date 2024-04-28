package com.paraskcd.spendingtracker.data.expense

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.paraskcd.spendingtracker.model.expenses.ExpenseAndSubcategory
import com.paraskcd.spendingtracker.model.expenses.ExpensesTable
import com.paraskcd.spendingtracker.model.expenses.SubcategoryAndExpenses
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDatabaseDao {
    @Transaction
    @Query("SELECT * FROM expenses")
    fun getAll(): Flow<List<ExpenseAndSubcategory>>

    @Transaction
    @Query("SELECT * FROM subcategories WHERE id = :id")
    fun getAllExpensesByCategoryId(id: String): Flow<SubcategoryAndExpenses>

    @Transaction
    @Query("SELECT * FROM expenses WHERE id = :id")
    fun getByExpenseId(id: String): Flow<ExpenseAndSubcategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(expense: ExpensesTable)

    @Update
    suspend fun update(expense: ExpensesTable)

    @Delete
    suspend fun delete(expense: ExpensesTable)
}