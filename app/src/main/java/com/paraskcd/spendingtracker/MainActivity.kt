package com.paraskcd.spendingtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paraskcd.spendingtracker.components.BottomNavBar
import com.paraskcd.spendingtracker.components.ScreenContainer
import com.paraskcd.spendingtracker.components.TopBar
import com.paraskcd.spendingtracker.data.bottombarnavitems.BottomBarNavItems
import com.paraskcd.spendingtracker.screens.categories.Categories
import com.paraskcd.spendingtracker.screens.categories.CategoryById
import com.paraskcd.spendingtracker.screens.categories.CategoryDeleteDialog
import com.paraskcd.spendingtracker.screens.categories.CategoryDialog
import com.paraskcd.spendingtracker.screens.categories.SubcategoryDeleteDialog
import com.paraskcd.spendingtracker.screens.categories.SubcategoryDialog
import com.paraskcd.spendingtracker.screens.categories.viewmodel.CategoriesViewModel
import com.paraskcd.spendingtracker.screens.home.Home
import com.paraskcd.spendingtracker.screens.home.HomeDialog
import com.paraskcd.spendingtracker.screens.home.viewmodel.HomeViewModel
import com.paraskcd.spendingtracker.screens.journal.Journal
import com.paraskcd.spendingtracker.screens.journal.JournalDeleteDialog
import com.paraskcd.spendingtracker.screens.journal.viewmodel.JournalViewModel
import com.paraskcd.spendingtracker.screens.settings.Settings
import com.paraskcd.spendingtracker.screens.settings.viewmodel.SettingsViewModel
import com.paraskcd.spendingtracker.ui.theme.SpendingTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpendingTrackerTheme {
                // A surface container using the 'background' color from the theme
                var showDialog by remember { mutableStateOf(false) }
                var showSubcategoryEditDialog by remember { mutableStateOf(false) }
                var showCategoryDeleteDialog by remember { mutableStateOf(false) }
                var showSubcategoryDeleteDialog by remember { mutableStateOf(false) }
                var showJournalDeleteDialog by remember { mutableStateOf(false) }
                val navController = rememberNavController()
                val homeViewModel: HomeViewModel = hiltViewModel()
                val settingsViewModel: SettingsViewModel = hiltViewModel()
                val categoriesViewModel: CategoriesViewModel = hiltViewModel()
                val journalViewModel: JournalViewModel = hiltViewModel()

                fun toggleDialog() {
                    showDialog = !showDialog
                }

                fun toggleCategoryDeleteDialog() {
                    showCategoryDeleteDialog = !showCategoryDeleteDialog
                    showDialog = showCategoryDeleteDialog == true
                }

                fun toggleSubcategoryDeleteDialog() {
                    showSubcategoryDeleteDialog = !showSubcategoryDeleteDialog
                    showDialog = showSubcategoryDeleteDialog == true
                }

                fun toggleSubcategoryEditDialog() {
                    showSubcategoryEditDialog = !showSubcategoryEditDialog
                    showDialog = showSubcategoryEditDialog == true
                }

                fun toggleShowJournalDeleteDialog() {
                    showJournalDeleteDialog = !showJournalDeleteDialog
                    showDialog = showJournalDeleteDialog == true
                }

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (showDialog) Modifier
                                .blur(20.dp)
                            else Modifier
                        ),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomNavBar(
                                navController = navController
                            )
                        },
                        topBar = {
                            TopBar(
                                navController = navController,
                                toggleDialog = { toggleDialog() },
                                categoriesViewModel = categoriesViewModel
                            )
                        }
                    ) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = "home"
                        ){
                            composable(route = "home") {
                                ScreenContainer(paddingValues = paddingValues) {
                                    Home(
                                        navController = navController,
                                        settingsViewModel = settingsViewModel,
                                        homeViewModel = homeViewModel
                                    )
                                }
                            }
                            composable(route = "categories") {
                                ScreenContainer(paddingValues = paddingValues) {
                                    Categories(
                                        toggleDialog = { toggleDialog() },
                                        navController = navController,
                                        viewModel = categoriesViewModel,
                                        toggleCategoryDeleteDialog = { toggleCategoryDeleteDialog() }
                                    )
                                }
                            }
                            composable(route = "journal") {
                                ScreenContainer(paddingValues = paddingValues) {
                                    Journal(
                                        viewModel = journalViewModel,
                                        toggleShowJournalDeleteDialog = { toggleShowJournalDeleteDialog() }
                                    )
                                }
                            }
                            composable(route = "categories/{categoryName}") {
                                ScreenContainer(paddingValues = paddingValues) {
                                    CategoryById(
                                        toggleDialog = { toggleDialog() },
                                        navController = navController,
                                        viewModel = categoriesViewModel,
                                        toggleSubcategoryEditDialog = { toggleSubcategoryEditDialog() }
                                    )
                                }
                            }
                            composable(route = "settings") {
                                ScreenContainer(paddingValues = paddingValues) {
                                    Settings(
                                        viewModel = settingsViewModel
                                    )
                                }
                            }
                        }
                    }

                    if (showDialog) {
                        if (showCategoryDeleteDialog) {
                            CategoryDeleteDialog(
                                toggleDialog = { toggleCategoryDeleteDialog() },
                                viewModel = categoriesViewModel
                            )
                        } else if (showSubcategoryDeleteDialog) {
                            SubcategoryDeleteDialog(
                                toggleDialog = { toggleSubcategoryDeleteDialog() },
                                viewModel = categoriesViewModel
                            )
                        } else if (showSubcategoryEditDialog) {
                            SubcategoryDialog(
                                toggleDialog = { toggleSubcategoryEditDialog() },
                                viewModel = categoriesViewModel,
                                toggleSubcategoryDeleteDialog = { toggleSubcategoryDeleteDialog() }
                            )
                        } else if (showJournalDeleteDialog) {
                            JournalDeleteDialog(
                                toggleDialog = { toggleShowJournalDeleteDialog() },
                                journalViewModel = journalViewModel,
                                settingsViewModel = settingsViewModel
                            )
                        } else {
                            if (navController.currentDestination?.route?.split('/')?.get(0)?.equals(BottomBarNavItems.Home.route) == true) {
                                HomeDialog(
                                    toggleDialog = { toggleDialog() },
                                    homeViewModel = homeViewModel,
                                    categoriesViewModel = categoriesViewModel,
                                    settingsViewModel = settingsViewModel
                                )
                            }
                            if (navController.currentDestination?.route?.split('/')?.get(0)?.equals(BottomBarNavItems.Categories.route) == true) {
                                CategoryDialog(
                                    toggleDialog = { toggleDialog() },
                                    viewModel = categoriesViewModel,
                                    toggleCategoryDeleteDialog = { toggleCategoryDeleteDialog() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}