package com.paraskcd.spendingtracker.screens.journal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paraskcd.spendingtracker.model.categories.CategoriesTable
import com.paraskcd.spendingtracker.model.expenses.ExpenseAndSubcategory
import com.paraskcd.spendingtracker.model.expenses.ExpensesTable
import com.paraskcd.spendingtracker.model.incomes.IncomeAndSubcategory
import com.paraskcd.spendingtracker.model.incomes.IncomeTable
import com.paraskcd.spendingtracker.repository.expenses.ExpensesRepository
import com.paraskcd.spendingtracker.repository.income.IncomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(private val incomeRepository: IncomeRepository, private val expensesRepository: ExpensesRepository): ViewModel() {
    private var _incomeDatabase: MutableStateFlow<List<IncomeAndSubcategory>> = MutableStateFlow(emptyList())
    var incomeDatabase: StateFlow<List<IncomeAndSubcategory>> = _incomeDatabase.asStateFlow()

    private var _expensesDatabase: MutableStateFlow<List<ExpenseAndSubcategory>> = MutableStateFlow(emptyList())
    var expensesDatabase: StateFlow<List<ExpenseAndSubcategory>> = _expensesDatabase.asStateFlow()

    private var _selectDeleteIncome: MutableStateFlow<IncomeTable?> = MutableStateFlow(null)
    val selectDeleteIncome: StateFlow<IncomeTable?> = _selectDeleteIncome.asStateFlow()

    private var _selectDeleteExpense: MutableStateFlow<ExpensesTable?> = MutableStateFlow(null)
    val selectDeleteExpense: StateFlow<ExpensesTable?> = _selectDeleteExpense.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllIncomes()
        }
        viewModelScope.launch(Dispatchers.IO) {
            getAllExpenses()
        }
    }

    private suspend fun getAllIncomes() {
        incomeRepository.getAllIncomes().distinctUntilChanged().collect {
                listOfAllIncomes ->
            if (listOfAllIncomes.isEmpty()) {
                _incomeDatabase.value = emptyList()
            } else {
                _incomeDatabase.value = listOfAllIncomes
            }
        }
    }

    private suspend fun getAllExpenses() {
        expensesRepository.getAllExpenses().distinctUntilChanged().collect {
                listOfAllExpenses ->
            if (listOfAllExpenses.isEmpty()) {
                _expensesDatabase.value = emptyList()
            } else {
                _expensesDatabase.value = listOfAllExpenses
            }
        }
    }

    fun selectDeleteIncome(incomeData: IncomeAndSubcategory) = viewModelScope.launch(Dispatchers.IO) {
        _selectDeleteIncome.value = incomeData.income
        _selectDeleteExpense.value = null
    }

    fun selectDeleteExpense(expenseData: ExpenseAndSubcategory) = viewModelScope.launch(Dispatchers.IO) {
        _selectDeleteExpense.value = expenseData.expense
        _selectDeleteIncome.value = null
    }

    fun deleteIncome(income: IncomeTable) = viewModelScope.launch(Dispatchers.IO) {
        incomeRepository.deleteIncome(income)
    }

    fun deleteExpense(expense: ExpensesTable) = viewModelScope.launch(Dispatchers.IO) {
        expensesRepository.deleteExpense(expense)
    }
}