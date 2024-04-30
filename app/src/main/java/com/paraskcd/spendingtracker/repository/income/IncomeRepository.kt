package com.paraskcd.spendingtracker.repository.income

import com.paraskcd.spendingtracker.data.income.IncomeDatabaseDao
import com.paraskcd.spendingtracker.model.incomes.IncomeTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.util.Date
import javax.inject.Inject

class IncomeRepository @Inject constructor(private val incomeDatabaseDao: IncomeDatabaseDao) {
    suspend fun saveIncome(income: IncomeTable) = incomeDatabaseDao.save(income)
    suspend fun updateIncome(income: IncomeTable) = incomeDatabaseDao.update(income)
    suspend fun deleteIncome(income: IncomeTable) = incomeDatabaseDao.delete(income)
    fun getByIncomeDateRange(from: Date, to: Date) = incomeDatabaseDao.getByIncomeEntryDate(from, to).flowOn(Dispatchers.IO).conflate()
    fun getByIncomeId(id: String) = incomeDatabaseDao.getByIncomeId(id).flowOn(Dispatchers.IO).conflate()
    fun getAllIncomes() = incomeDatabaseDao.getAll().flowOn(Dispatchers.IO).conflate()
    fun getIncomesBySubcategoryId(id: String) = incomeDatabaseDao.getIncomesBySubcategoryId(id).flowOn(
        Dispatchers.IO).conflate()
}