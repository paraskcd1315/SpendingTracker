package com.paraskcd.spendingtracker.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.paraskcd.spendingtracker.data.bottombarnavitems.BottomBarNavItems
import com.paraskcd.spendingtracker.model.settings.MainSettingsTable
import com.paraskcd.spendingtracker.screens.home.viewmodel.HomeViewModel
import com.paraskcd.spendingtracker.screens.settings.viewmodel.SettingsViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale

@Composable
fun Home(navController: NavController, settingsViewModel: SettingsViewModel, homeViewModel: HomeViewModel) {
    val settingsDatabase = settingsViewModel.settingsDatabase.collectAsState().value
    val expensesDatabase = homeViewModel.expensesDatabase.collectAsState().value
    val incomeDatabase = homeViewModel.incomeDatabase.collectAsState().value

    if (settingsDatabase.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { navController.navigate(BottomBarNavItems.Settings.route) }) {
                Text(text = "Initial Setup", fontSize = 24.sp)
            }
        }
    } else {
        val pattern = "d MMMM yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())

        val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
        
        val today = LocalDate.now()
        val endOfMonth = today.withDayOfMonth(today.lengthOfMonth())
        var daysBetween = ChronoUnit.DAYS.between(today, endOfMonth)

        if (daysBetween.toInt() <= 1) {
            daysBetween = 1
        }

        var perDaySpend by remember {
            mutableStateOf(0.00f)
        }

        var spentToday by remember {
            mutableStateOf(0.00f)
        }

        var earntToday by remember {
            mutableStateOf(0.00f)
        }

        var bankBalance by remember {
            mutableStateOf(0.00f)
        }

        var budget by remember {
            mutableStateOf(0.00f)
        }

        LaunchedEffect(key1 = settingsDatabase) {
            if (settingsDatabase[0].bankBalance > settingsDatabase[0].budget) {
                perDaySpend = (settingsDatabase[0].bankBalance - settingsDatabase[0].budget)/daysBetween
            } else {
                perDaySpend = (settingsDatabase[0].bankBalance)/daysBetween
            }

            bankBalance = settingsDatabase[0].bankBalance
            budget = settingsDatabase[0].budget
        }

        LaunchedEffect(key1 = expensesDatabase) {
            if (expensesDatabase.isNotEmpty()) {
                spentToday = 0.0f
                for (expenses in expensesDatabase) {
                    spentToday += expenses.expense.amount
                }
            }
        }

        LaunchedEffect(key1 = incomeDatabase) {
            if (incomeDatabase.isNotEmpty()) {
                earntToday = 0.0f
                for (income in incomeDatabase) {
                    earntToday += income.income.amount
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(
                        text = simpleDateFormat.format(Date.from(Instant.now())),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Spacer(modifier = Modifier.padding(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Daily Expense Limit",
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (perDaySpend <= 0) formatter.format(0.00f)
                                    else formatter.format(perDaySpend),
                                textAlign = TextAlign.Center,
                                fontSize = 48.sp,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                fontWeight = FontWeight.Black
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Spacer(modifier = Modifier.padding(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Spent Today",
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = formatter.format(spentToday),
                                textAlign = TextAlign.Center,
                                fontSize = 48.sp,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                color = if (spentToday > perDaySpend) {
                                    MaterialTheme.colorScheme.error
                                } else {
                                    MaterialTheme.colorScheme.primary
                                },
                                fontWeight = FontWeight.Black
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Spacer(modifier = Modifier.padding(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Earned Today",
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = formatter.format(earntToday),
                                textAlign = TextAlign.Center,
                                fontSize = 48.sp,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                color = if (earntToday == 0f) {
                                    MaterialTheme.colorScheme.onBackground
                                } else if (earntToday > perDaySpend) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.error
                                },
                                fontWeight = FontWeight.Black
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Spacer(modifier = Modifier.padding(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Balance",
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = formatter.format(bankBalance),
                                textAlign = TextAlign.Center,
                                fontSize = 48.sp,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                fontWeight = FontWeight.Black
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Spacer(modifier = Modifier.padding(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Budget",
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = formatter.format(budget),
                                textAlign = TextAlign.Center,
                                fontSize = 48.sp,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                fontWeight = FontWeight.Black
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}