package com.paraskcd.spendingtracker.screens.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.paraskcd.spendingtracker.components.DialogContainer
import com.paraskcd.spendingtracker.data.dropdowniconlistitems.DropdownIconListItems
import com.paraskcd.spendingtracker.model.categories.CategoriesTable
import com.paraskcd.spendingtracker.model.categories.CategoryAndSubcategories
import com.paraskcd.spendingtracker.model.categories.SubcategoriesTable
import com.paraskcd.spendingtracker.screens.categories.viewmodel.CategoriesViewModel
import com.paraskcd.spendingtracker.utils.imageVectorFromString
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDialog(toggleDialog: () -> Unit, viewModel: CategoriesViewModel, toggleCategoryDeleteDialog: () -> Unit) {
    val categories = viewModel.categoriesDatabase.collectAsState().value
    val categoryById = viewModel.categoryById.collectAsState().value

    DialogContainer(toggleDialog = { toggleDialog() }) {
        var selectedTab by remember { mutableStateOf(0) }
        Column {
            TabRow(
                selectedTabIndex = selectedTab,
                divider = { Divider() }
            ) {
                Box(
                    modifier = Modifier
                        .clickable { selectedTab = 0 }
                        .padding(16.dp)
                ) {
                    Text("Category")
                }
                if (categories.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .clickable { selectedTab = 1 }
                            .padding(16.dp)
                    ) {
                        Text("Subcategory")
                    }
                }
            }
            when (selectedTab) {
                0 -> {
                    Column(modifier = Modifier.padding(16.dp)) {
                        var name: String by remember {
                            if (categoryById != null) {
                                mutableStateOf(categoryById.category.name)
                            } else {
                                mutableStateOf("")
                            }
                        }
                        var iconName: String by remember {
                            if (categoryById != null) {
                                mutableStateOf(categoryById.category.iconName)
                            } else {
                                mutableStateOf("ShoppingCart")
                            }
                        }
                        var dropdownExpanded: Boolean by remember {
                            mutableStateOf(false)
                        }
                        var dropdownIconListItems = listOf(
                            DropdownIconListItems.Home,
                            DropdownIconListItems.Settings,
                            DropdownIconListItems.Build,
                            DropdownIconListItems.Call,
                            DropdownIconListItems.Edit,
                            DropdownIconListItems.ShoppingCart,
                            DropdownIconListItems.Star
                        )
                        Text("Enter details for a Category: ")
                        Spacer(modifier = Modifier.padding(8.dp))
                        TextField(
                            label = { Text(text = "Category Name" ) },
                            placeholder = { Text(text = "Enter Category Name") },
                            value = name,
                            onValueChange = { value -> name = value },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        ExposedDropdownMenuBox(
                            expanded = dropdownExpanded,
                            onExpandedChange = { change -> dropdownExpanded = change }
                        ) {
                            TextField(
                                label = { Text(text = "Icon Name") },
                                value = iconName,
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
                                dropdownIconListItems.forEach { item ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = item.vector,
                                                    contentDescription = item.label
                                                )
                                                Spacer(modifier = Modifier.padding(8.dp))
                                                Text(text = item.label)
                                            }
                                        },
                                        onClick = {
                                            iconName = item.value
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
                            if (categoryById != null) {
                                Button(
                                    onClick = {
                                        viewModel.selectDeleteCategory(categoryById)
                                        toggleCategoryDeleteDialog()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer,
                                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                ) {
                                    Text(text = "Delete")
                                }
                                Spacer(modifier = Modifier.padding(8.dp))
                            }
                            Button(
                                onClick = {
                                    if (categoryById != null) {
                                        viewModel.editCategory(
                                            category = CategoriesTable(
                                                id = categoryById.category.id,
                                                name = name,
                                                iconName = iconName
                                            ),
                                        )
                                        toggleDialog()
                                    } else {
                                        viewModel.addCategory(
                                            category = CategoriesTable(
                                                name = name,
                                                iconName = iconName
                                            )
                                        )
                                        toggleDialog()
                                    }
                                }
                            ) {
                                if (categoryById != null) {
                                    Text(text = "Edit")
                                } else {
                                    Text(text = "Save")
                                }
                            }
                        }
                    }
                }
                1 -> {
                    Column(modifier = Modifier.padding(16.dp)) {
                        var name: String by remember {
                            mutableStateOf("")
                        }
                        var categoryId: UUID? by remember {
                            if (categoryById != null) {
                                mutableStateOf(categoryById.category.id)
                            } else {
                                mutableStateOf(null)
                            }
                        }
                        var categoryName: String by remember {
                            if (categoryById != null) {
                                mutableStateOf(categoryById.category.name)
                            } else {
                                mutableStateOf("")
                            }
                        }
                        var dropdownExpanded: Boolean by remember {
                            mutableStateOf(false)
                        }
                        Text("Enter details for a Subcategory: ")
                        Spacer(modifier = Modifier.padding(8.dp))
                        TextField(
                            label = { Text(text = "Subcategory Name" ) },
                            placeholder = { Text(text = "Enter Subcategory Name") },
                            value = name,
                            onValueChange = { value -> name = value },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        ExposedDropdownMenuBox(
                            expanded = dropdownExpanded,
                            onExpandedChange = { change -> dropdownExpanded = change }
                        ) {
                            TextField(
                                label = { Text(text = "Category") },
                                value = categoryName,
                                onValueChange = {},
                                readOnly = true,
                                enabled = categoryById == null,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(if (categoryById == null) Modifier.menuAnchor() else Modifier),
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
                                categories.forEach { categoryData ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(
                                                horizontalArrangement = Arrangement.Start,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = imageVectorFromString(iconName = categoryData.category.iconName),
                                                    contentDescription = categoryData.category.iconName
                                                )
                                                Spacer(modifier = Modifier.padding(8.dp))
                                                Text(text = categoryData.category.name)
                                            }
                                        },
                                        onClick = {
                                            categoryName = categoryData.category.name
                                            categoryId = categoryData.category.id
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
                            Button(onClick = {
                                    if (categoryById != null) {
                                        viewModel.addSubcategory(
                                            subcategory = SubcategoriesTable(
                                                name = name,
                                                categoryId = categoryById.category.id
                                            )
                                        )
                                        toggleDialog()
                                    } else {
                                        categoryId?.let {
                                                catId ->
                                            viewModel.addSubcategory(
                                                subcategory = SubcategoriesTable(
                                                    name = name,
                                                    categoryId = catId
                                                )
                                            )
                                        }
                                        toggleDialog()
                                    }
                                }
                            ) {
                                Text(text = "Save")
                            }
                        }
                    }
                }
            }
        }
    }
}