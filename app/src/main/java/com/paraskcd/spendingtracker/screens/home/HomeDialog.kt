package com.paraskcd.spendingtracker.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.paraskcd.spendingtracker.components.DialogContainer
import com.paraskcd.spendingtracker.model.expenses.ExpensesTable
import com.paraskcd.spendingtracker.model.incomes.IncomeTable
import com.paraskcd.spendingtracker.model.settings.MainSettingsTable
import com.paraskcd.spendingtracker.screens.categories.viewmodel.CategoriesViewModel
import com.paraskcd.spendingtracker.screens.home.viewmodel.HomeViewModel
import com.paraskcd.spendingtracker.screens.settings.viewmodel.SettingsViewModel
import com.paraskcd.spendingtracker.utils.imageVectorFromString
import java.time.Instant
import java.util.Date
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDialog(toggleDialog: () -> Unit, settingsViewModel: SettingsViewModel, homeViewModel: HomeViewModel, categoriesViewModel: CategoriesViewModel) {
    val subcategories = categoriesViewModel.subcategoriesDatabase.collectAsState().value
    val settingsDatabase = settingsViewModel.settingsDatabase.collectAsState().value

    var settings: MainSettingsTable? by remember {
        if (settingsDatabase.isEmpty()) {
            mutableStateOf(null)
        } else {
            mutableStateOf(settingsDatabase[0])
        }
    }

    DialogContainer(toggleDialog = { toggleDialog() }) {
        var selectedTab by remember {
            mutableStateOf(0)
        }
        Column {
            TabRow(
                selectedTabIndex = selectedTab,
                divider = { Divider() }
            ) {
                Row(
                    modifier = Modifier
                        .clickable { selectedTab = 0 }
                        .padding(20.dp)
                ) {
                    Text(text = "Expense")
                }
                Box(
                    modifier = Modifier
                        .clickable { selectedTab = 1 }
                        .padding(20.dp)
                ) {
                    Text(text = "Income")
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                var amount: Float by remember {
                    mutableStateOf(0.00f)
                }
                var description: String by remember {
                    mutableStateOf("")
                }
                var subcategoryId: UUID? by remember {
                    mutableStateOf(null)
                }
                var subcategoryName: String by remember {
                    mutableStateOf("")
                }
                var dropdownExpanded: Boolean by remember {
                    mutableStateOf(false)
                }

                LaunchedEffect(key1 = subcategoryId) {
                    if (subcategoryId != null) {
                        homeViewModel.getProductsBySubcategoryId(subcategoryId.toString())
                    }
                }

                when (selectedTab) {
                    0 -> {
                        Text(text = "Enter Details of Expense: ")
                        Spacer(modifier = Modifier.padding(8.dp))
                        TextField(
                            label = { Text(text = "Amount" ) },
                            placeholder = { Text(text = "Enter the Expense Amount") },
                            value = amount.toString(),
                            onValueChange = { value -> amount = value.toFloat() },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        TextField(
                            label = { Text(text = "Description") },
                            placeholder = { Text(text = "Enter Description for this Expense") },
                            value = description,
                            onValueChange = { value -> description = value },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    1 -> {
                        Text(text = "Enter Details of Income: ")
                        Spacer(modifier = Modifier.padding(8.dp))
                        TextField(
                            label = { Text(text = "Amount" ) },
                            placeholder = { Text(text = "Enter the Income Amount") },
                            value = amount.toString(),
                            onValueChange = { value -> amount = value.toFloat() },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        TextField(
                            label = { Text(text = "Description") },
                            placeholder = { Text(text = "Enter Description for this Income") },
                            value = description,
                            onValueChange = { value -> description = value },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
                ExposedDropdownMenuBox(
                    expanded = dropdownExpanded,
                    onExpandedChange = { change -> dropdownExpanded = change }
                ) {
                    TextField(
                        label = { Text(text = "Subcategory") },
                        value = subcategoryName,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false }
                    ) {
                        subcategories.forEach { subcategoryData ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = imageVectorFromString(iconName = subcategoryData.category.iconName),
                                            contentDescription = subcategoryData.category.iconName
                                        )
                                        Spacer(modifier = Modifier.padding(8.dp))
                                        Text(text = subcategoryData.subcategory.name)
                                    }
                                },
                                onClick = {
                                    subcategoryName = subcategoryData.subcategory.name
                                    subcategoryId = subcategoryData.subcategory.id
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { toggleDialog() }) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        onClick = {
                            if (subcategoryId != null && settings != null) {
                                when (selectedTab) {
                                    0 -> {
                                        homeViewModel.addExpense(
                                            ExpensesTable(
                                                amount = amount,
                                                description = description,
                                                subcategoryId = subcategoryId!!
                                            )
                                        )
                                        settingsViewModel.updateSetting(
                                            MainSettingsTable(
                                                id = settings!!.id,
                                                bankBalance = settings!!.bankBalance - amount,
                                                budget = settings!!.budget,
                                                updated = Date.from(Instant.now())
                                            )
                                        )
                                    }
                                    1 -> {
                                        homeViewModel.addIncome(
                                            IncomeTable(
                                                amount = amount,
                                                description = description,
                                                subcategoryId = subcategoryId!!
                                            )
                                        )
                                        settingsViewModel.updateSetting(
                                            MainSettingsTable(
                                                id = settings!!.id,
                                                bankBalance = settings!!.bankBalance + amount,
                                                budget = settings!!.budget,
                                                updated = Date.from(Instant.now())
                                            )
                                        )
                                    }
                                }
                            }
                            toggleDialog()
                        }
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}