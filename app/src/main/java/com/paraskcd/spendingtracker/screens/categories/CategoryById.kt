package com.paraskcd.spendingtracker.screens.categories

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.paraskcd.spendingtracker.screens.categories.viewmodel.CategoriesViewModel
import com.paraskcd.spendingtracker.utils.imageVectorFromString
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryById(toggleDialog: () -> Unit, navController: NavController, viewModel: CategoriesViewModel, toggleSubcategoryDeleteDialog: () -> Unit) {
    val categoryById = viewModel.categoryById.collectAsState().value

    BackHandler {
        viewModel.resetCategoryById()
        navController.navigate("categories") {
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        }
    }

    LaunchedEffect(key1 = categoryById) {
        if (categoryById == null) {
            navController.navigate("categories") {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    if (categoryById != null) {
        if (categoryById.subcategories.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { toggleDialog() }) {
                    Text(text = "Add a subcategory")
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                items(categoryById.subcategories) { subcategory ->
                    Column {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.selectDeleteSubcategory(
                                        category = categoryById,
                                        subcategoryId = subcategory.id
                                    )
                                    toggleSubcategoryDeleteDialog()
                                },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                        ) {
                            Row(modifier = Modifier.padding(16.dp)) {
                                Text(text = subcategory.name)
                            }
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }

                }
            }
        }
    }
}