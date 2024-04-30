package com.paraskcd.spendingtracker.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paraskcd.spendingtracker.model.expenses.ExpenseAndSubcategory
import com.paraskcd.spendingtracker.model.expenses.SubcategoryAndExpenses
import com.paraskcd.spendingtracker.model.incomes.IncomeAndSubcategory
import com.paraskcd.spendingtracker.model.incomes.SubcategoryAndIncomes
import com.paraskcd.spendingtracker.model.products.ProductAndSubcategory
import com.paraskcd.spendingtracker.model.products.SubcategoryAndProducts
import com.paraskcd.spendingtracker.repository.expenses.ExpensesRepository
import com.paraskcd.spendingtracker.repository.income.IncomeRepository
import com.paraskcd.spendingtracker.repository.products.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productsRepository: ProductsRepository, private val incomeRepository: IncomeRepository, private val expensesRepository: ExpensesRepository): ViewModel() {
    private var _incomeDatabase: MutableStateFlow<List<IncomeAndSubcategory>> = MutableStateFlow(emptyList())
    var incomeDatabase: StateFlow<List<IncomeAndSubcategory>> = _incomeDatabase.asStateFlow()

    private var _expensesDatabase: MutableStateFlow<List<ExpenseAndSubcategory>> = MutableStateFlow(emptyList())
    var expensesDatabase: StateFlow<List<ExpenseAndSubcategory>> = _expensesDatabase.asStateFlow()

    private var _productsDatabase: MutableStateFlow<List<ProductAndSubcategory>> = MutableStateFlow(emptyList())
    var productsDatabase: StateFlow<List<ProductAndSubcategory>> = _productsDatabase.asStateFlow()

    private var _subcategoryByIdIncome: MutableStateFlow<SubcategoryAndIncomes?> = MutableStateFlow(null)
    var subcategoryByIdIncome: StateFlow<SubcategoryAndIncomes?> = _subcategoryByIdIncome.asStateFlow()

    private var _subcategoryByIdExpenses: MutableStateFlow<SubcategoryAndExpenses?> = MutableStateFlow(null)
    var subcategoryByIdExpenses: StateFlow<SubcategoryAndExpenses?> = _subcategoryByIdExpenses.asStateFlow()

    private var _subcategoryByIdProducts: MutableStateFlow<SubcategoryAndProducts?> = MutableStateFlow(null)
    var subcategoryByIdProducts: StateFlow<SubcategoryAndProducts?> = _subcategoryByIdProducts.asStateFlow()

    private var _incomeById: MutableStateFlow<IncomeAndSubcategory?> = MutableStateFlow(null)
    var incomeById: StateFlow<IncomeAndSubcategory?> = _incomeById.asStateFlow()

    private var _expenseById: MutableStateFlow<ExpenseAndSubcategory?> = MutableStateFlow(null)
    var expenseById: StateFlow<ExpenseAndSubcategory?> = _expenseById.asStateFlow()

    private var _productById: MutableStateFlow<ProductAndSubcategory?> = MutableStateFlow(null)
    var productById: StateFlow<ProductAndSubcategory?> = _productById.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllIncomes()
        }
        viewModelScope.launch(Dispatchers.IO) {
            getAllExpenses()
        }
        viewModelScope.launch(Dispatchers.IO) {
            getAllProducts()
        }
    }

    suspend fun getAllIncomes() {
        incomeRepository.getAllIncomes().distinctUntilChanged().collect {
                listOfAllIncomes ->
            if (listOfAllIncomes.isEmpty()) {
                _incomeDatabase.value = emptyList()
            } else {
                _incomeDatabase.value = listOfAllIncomes
            }
        }
    }

    suspend fun getAllExpenses() {
        expensesRepository.getAllExpenses().distinctUntilChanged().collect {
            listOfAllExpenses ->
            if (listOfAllExpenses.isEmpty()) {
                _expensesDatabase.value = emptyList()
            } else {
                _expensesDatabase.value = listOfAllExpenses
            }
        }
    }

    suspend fun getAllProducts() {
        productsRepository.getAllProducts().distinctUntilChanged().collect {
            listOfAllProducts ->
            if (listOfAllProducts.isEmpty()) {
                _productsDatabase.value = emptyList()
            } else {
                _productsDatabase.value = listOfAllProducts
            }
        }
    }

    suspend fun getIncomesBySubcategoryId(id: String) {
        incomeRepository.getIncomesBySubcategoryId(id).distinctUntilChanged().collect {
            incomeBySubcat ->
            if (incomeBySubcat != null) {
                _subcategoryByIdIncome.value = incomeBySubcat
            }
        }
    }

    suspend fun getExpensesBySubcategoryId(id: String) {
        expensesRepository.getExpensesBySubcategoryId(id).distinctUntilChanged().collect {
            expensesBySubcat ->
            if (expensesBySubcat != null) {
                _subcategoryByIdExpenses.value = expensesBySubcat
            }
        }
    }

    suspend fun getProductsBySubcategoryId(id: String) {
        productsRepository.getProductsBySubcategoryId(id).distinctUntilChanged().collect {
            productsBySubcat ->
            if (productsBySubcat != null) {
                _subcategoryByIdProducts.value = productsBySubcat
            }
        }
    }

    suspend fun getByIncomeId(id: String) {
        incomeRepository.getByIncomeId(id).distinctUntilChanged().collect {
                income ->
            if (income != null) {
                _incomeById.value = income
            }
        }
    }

    suspend fun getByExpenseId(id: String) {
        expensesRepository.getByExpenseId(id).distinctUntilChanged().collect {
                expense ->
            if (expense != null) {
                _expenseById.value = expense
            }
        }
    }

    suspend fun getByProductId(id: String) {
        productsRepository.getByProductId(id).distinctUntilChanged().collect {
                product ->
            if (product != null) {
                _productById.value = product
            }
        }
    }
}