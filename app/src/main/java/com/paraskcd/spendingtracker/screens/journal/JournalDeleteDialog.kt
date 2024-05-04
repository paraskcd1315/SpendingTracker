package com.paraskcd.spendingtracker.screens.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paraskcd.spendingtracker.components.DialogContainer
import com.paraskcd.spendingtracker.model.settings.MainSettingsTable
import com.paraskcd.spendingtracker.screens.journal.viewmodel.JournalViewModel
import com.paraskcd.spendingtracker.screens.settings.viewmodel.SettingsViewModel
import java.text.NumberFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@Composable
fun JournalDeleteDialog(toggleDialog: () -> Unit, journalViewModel: JournalViewModel, settingsViewModel: SettingsViewModel) {
    val selectedIncome = journalViewModel.selectDeleteIncome.collectAsState().value
    val selectedExpense = journalViewModel.selectDeleteExpense.collectAsState().value
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
    val settings = settingsViewModel.settingsDatabase.collectAsState().value[0]

    if (selectedIncome != null) {
        DialogContainer(toggleDialog = { toggleDialog() }) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Are you sure you want to delete Income of ${formatter.format(selectedIncome.amount)}")
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
                            journalViewModel.deleteIncome(selectedIncome)
                            settingsViewModel.updateSetting(MainSettingsTable(
                                id = settings.id,
                                bankBalance = settings.bankBalance - selectedIncome.amount,
                                budget = settings.budget,
                                updated = Date.from(Instant.now())
                            ))
                            toggleDialog()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    ) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }

    if (selectedExpense != null) {
        DialogContainer(toggleDialog = { toggleDialog() }) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Are you sure you want to delete Expense of ${formatter.format(selectedExpense.amount)}")
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
                            journalViewModel.deleteExpense(selectedExpense)
                            settingsViewModel.updateSetting(MainSettingsTable(
                                id = settings.id,
                                bankBalance = settings.bankBalance + selectedExpense.amount,
                                budget = settings.budget,
                                updated = Date.from(Instant.now())
                            ))
                            toggleDialog()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    ) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
}