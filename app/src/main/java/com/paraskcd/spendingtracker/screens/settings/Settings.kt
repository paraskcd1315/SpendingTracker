package com.paraskcd.spendingtracker.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.paraskcd.spendingtracker.model.settings.MainSettingsTable
import com.paraskcd.spendingtracker.screens.settings.viewmodel.SettingsViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale


@Composable
fun Settings(viewModel: SettingsViewModel) {
    val settings = viewModel.settingsDatabase.collectAsState().value
    var accountBalance by remember {
        if (settings.isEmpty()) {
            mutableStateOf(0.00f)
        } else {
            mutableStateOf(settings[0].bankBalance)
        }
    }

    if (settings.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            LazyColumn(modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)) {
                item {
                    Text(text = "Enter below the Bank Balance you've got right now, you probably wont have to look anymore once you put the Data.")
                }
                item { 
                    Spacer(modifier = Modifier.padding(16.dp))
                }
                item {
                    TextField(
                        label = { Text(text = "Account Balance") },
                        value = accountBalance.toString(),
                        onValueChange = {
                            accountBalance = it.toFloat()
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            Column(Modifier.padding(16.dp)) {
                Button(onClick = { viewModel.saveSetting(MainSettingsTable(bankBalance = accountBalance)) }) {
                    Text("Save")
                }
            }
        }
    } else {
        val setting = settings[0]
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            LazyColumn(modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)) {
                item {
                    Text(text = "Enter below the Bank Balance you've got right now, this will update the data if there are any inconsistencies.")
                }
                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                }
                item {
                    TextField(
                        label = { Text(text = "Account Balance") },
                        value = accountBalance.toString(),
                        onValueChange = {
                            accountBalance = it.toFloat()
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                }
                item {
                    val pattern = "d MMMM yyyy - HH:mm"
                    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
                    val updated = simpleDateFormat.format(setting.updated)
                    Text(text = "Updated on $updated")
                }
            }
            Column(Modifier.padding(16.dp)) {
                Button(
                    onClick = {
                        viewModel.updateSetting(
                            MainSettingsTable(
                                id = setting.id,
                                bankBalance = accountBalance,
                                updated = Date.from(Instant.now())
                            )
                        )
                    }
                ) {
                    Text("Edit")
                }
            }
        }
    }
}