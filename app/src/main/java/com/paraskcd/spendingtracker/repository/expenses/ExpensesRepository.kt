package com.paraskcd.spendingtracker.repository.expenses

import com.paraskcd.spendingtracker.data.expense.ExpenseDatabaseDao
import com.paraskcd.spendingtracker.model.expenses.ExpensesTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ExpensesRepository @Inject constructor(private val expensesDatabaseDao: ExpenseDatabaseDao) {
    suspend fun saveExpense(expense: ExpensesTable) = expensesDatabaseDao.save(expense)
    suspend fun updateExpense(expense: ExpensesTable) = expensesDatabaseDao.update(expense)
    suspend fun deleteExpense(expense: ExpensesTable) = expensesDatabaseDao.delete(expense)
    fun getByExpenseId(id: String) = expensesDatabaseDao.getByExpenseId(id).flowOn(Dispatchers.IO).conflate()
    fun getAllExpenses() = expensesDatabaseDao.getAll().flowOn(Dispatchers.IO).conflate()
    fun getExpensesBySubcategoryId(id: String) = expensesDatabaseDao.getAllExpensesByCategoryId(id).flowOn(
        Dispatchers.IO).conflate()
}