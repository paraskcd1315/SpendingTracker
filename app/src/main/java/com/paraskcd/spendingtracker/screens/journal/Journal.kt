package com.paraskcd.spendingtracker.screens.journal

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paraskcd.spendingtracker.model.expenses.ExpenseAndSubcategory
import com.paraskcd.spendingtracker.model.incomes.IncomeAndSubcategory
import com.paraskcd.spendingtracker.screens.categories.viewmodel.CategoriesViewModel
import com.paraskcd.spendingtracker.screens.journal.viewmodel.JournalViewModel
import com.paraskcd.spendingtracker.screens.settings.viewmodel.SettingsViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun Journal(viewModel: JournalViewModel, toggleShowJournalDeleteDialog: () -> Unit) {
    val pattern = "d MMMM yyyy - HH:mm"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())

    val incomeDatabase = viewModel.incomeDatabase.collectAsState().value
    val expensesDatabase = viewModel.expensesDatabase.collectAsState().value
    var journal: List<Any> by remember {
        mutableStateOf(emptyList())
    }

    LaunchedEffect(key1 = incomeDatabase, key2 = expensesDatabase) {
        if (incomeDatabase.isNotEmpty() && expensesDatabase.isNotEmpty()) {
            journal = (incomeDatabase + expensesDatabase)
                .sortedByDescending {
                    when (it) {
                        is IncomeAndSubcategory -> it.income.entryDate
                        is ExpenseAndSubcategory -> it.expense.entryDate
                        else -> error("")
                    }
                }
        }
    }

    if (journal.isNotEmpty()) {
        LazyColumn {
            items(journal) {
                Spacer(modifier = Modifier.padding(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable {
                           when(it) {
                               is ExpenseAndSubcategory -> {
                                   viewModel.selectDeleteExpense(it)
                                   toggleShowJournalDeleteDialog()
                               }
                               is IncomeAndSubcategory -> {
                                   viewModel.selectDeleteIncome(it)
                                   toggleShowJournalDeleteDialog()
                               }
                           }
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    when(it) {
                        is ExpenseAndSubcategory -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = simpleDateFormat.format(it.expense.entryDate),
                                )
                                Text(text = "Expense")
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                            ) {
                                Text(
                                    text = "Category: ",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = it.subcategory.name,
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                            ) {
                                Text(
                                    text = "Description: ",
                                    fontWeight = FontWeight.Bold
                                )
                                it.expense.description?.let { it1 ->
                                    Text(
                                        text = it1,
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = formatter.format(it.expense.amount),
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                        is IncomeAndSubcategory -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = simpleDateFormat.format(it.income.entryDate),
                                )
                                Text(text = "Income")
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                            ) {
                                Text(
                                    text = "Category: ",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = it.subcategory.name,
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                            ) {
                                Text(
                                    text = "Description: ",
                                    fontWeight = FontWeight.Bold
                                )
                                it.income.description?.let { it1 ->
                                    Text(
                                        text = it1,
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = formatter.format(it.income.amount),
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}