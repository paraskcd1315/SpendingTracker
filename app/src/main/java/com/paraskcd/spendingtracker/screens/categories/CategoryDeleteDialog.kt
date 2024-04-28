package com.paraskcd.spendingtracker.screens.categories

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
import com.paraskcd.spendingtracker.screens.categories.viewmodel.CategoriesViewModel

@Composable
fun CategoryDeleteDialog(toggleDialog: () -> Unit, viewModel: CategoriesViewModel) {
    viewModel.selectDeleteCategory.collectAsState().value?.let {
        DialogContainer(toggleDialog = { toggleDialog() }) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Are you sure you want to delete Category: ${it.name}, if it contains Subcategories, they'll be deleted as well.")
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
                            viewModel.deleteCategory(it)
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

@Composable
fun SubcategoryDeleteDialog(toggleDialog: () -> Unit, viewModel: CategoriesViewModel) {
    val categoryById = viewModel.categoryById.collectAsState().value
    viewModel.selectDeleteSubcategory.collectAsState().value?.let {
        DialogContainer(toggleDialog = { toggleDialog() }) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Are you sure you want to delete Subcategory: ${it.name}")
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
                            if (categoryById != null) {
                                viewModel.deleteSubcategory(subcategory = it)
                            } else {
                                viewModel.deleteSubcategory(subcategory = it)
                            }
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