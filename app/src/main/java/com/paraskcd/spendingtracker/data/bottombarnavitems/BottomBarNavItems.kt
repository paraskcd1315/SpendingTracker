package com.paraskcd.spendingtracker.data.bottombarnavitems

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarNavItems(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomBarNavItems(route = "home", Icons.Default.Home, "Home")
    object Categories : BottomBarNavItems(route = "categories", Icons.Default.List, "Categories")
    object Settings : BottomBarNavItems(route = "settings", Icons.Default.Settings, "Settings")
}