package com.paraskcd.spendingtracker.data.dropdowniconlistitems

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DropdownIconListItems(val vector: ImageVector, val label: String, val value: String) {
    object Settings : DropdownIconListItems(vector = Icons.Filled.Settings, label = "Settings", value = "Settings")
    object Edit : DropdownIconListItems(vector = Icons.Filled.Edit, label = "Edit", value = "Edit")
    object ShoppingCart : DropdownIconListItems(vector = Icons.Filled.ShoppingCart, label = "Shopping Cart", value = "ShoppingCart")
    object Home : DropdownIconListItems(vector = Icons.Filled.Home, label = "Home", value = "Home")
    object Build : DropdownIconListItems(vector = Icons.Filled.Build, label = "Build", value = "Build")
    object Call : DropdownIconListItems(vector = Icons.Filled.Call, label = "Call", value = "Call")
    object Star : DropdownIconListItems(vector = Icons.Filled.Star, label = "Star", value = "Star")
}