package com.paraskcd.spendingtracker.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.paraskcd.spendingtracker.data.bottombarnavitems.BottomBarNavItems
import com.paraskcd.spendingtracker.screens.categories.viewmodel.CategoriesViewModel
import com.paraskcd.spendingtracker.utils.imageVectorFromString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, toggleDialog: () -> Unit, categoriesViewModel: CategoriesViewModel) {
    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentRoute: String? = navBackStackEntry?.destination?.route
    val navBarItems: List<BottomBarNavItems> = listOf(
        BottomBarNavItems.Home,
        BottomBarNavItems.Categories,
        BottomBarNavItems.Settings
    )
    val selectedNavBarItem = navBarItems.find { it.route == currentRoute }
    val categoryById = categoriesViewModel.categoryById.collectAsState().value

    TopAppBar(
        title = {
            if (selectedNavBarItem?.label != null) {
                Text(text = selectedNavBarItem.label)
            } else {
                if (currentRoute != null) {
                    if (
                        currentRoute.split('/').count() > 1
                        && currentRoute.split('/')[0].equals(BottomBarNavItems.Categories.route)
                    ) {
                        if (categoryById != null) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = imageVectorFromString(iconName = categoryById.category.iconName),
                                    contentDescription = categoryById.category.iconName
                                )
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text(text = categoryById.category.name)
                            }
                        }
                    }
                }
            }
        },
        navigationIcon = {
            if (currentRoute != null) {
                if (currentRoute.split('/').count() > 1){
                    IconButton(
                        onClick = {
                            if (categoryById != null) {
                                categoriesViewModel.resetCategoryById()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            }
        },
        actions = {
            if (currentRoute != null) {
                if (
                    currentRoute.split('/').count() > 1
                    && currentRoute.split('/')[0].equals(BottomBarNavItems.Categories.route)
                ) {
                    IconButton(
                        onClick = { toggleDialog() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit ${BottomBarNavItems.Categories.label}"
                        )
                    }
                } else {
                    selectedNavBarItem?.label?.let {
                        if (it != "Settings") {
                            IconButton(
                                onClick = { toggleDialog() }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add ${selectedNavBarItem.label}"
                                )
                            }
                        }
                    }
                }
            }

        },
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))

    )
}