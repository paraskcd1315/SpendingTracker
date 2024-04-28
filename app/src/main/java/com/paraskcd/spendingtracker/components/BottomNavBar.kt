package com.paraskcd.spendingtracker.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.paraskcd.spendingtracker.data.bottombarnavitems.BottomBarNavItems
import com.paraskcd.spendingtracker.screens.categories.viewmodel.CategoriesViewModel

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentRoute: String? = navBackStackEntry?.destination?.route
    val navBarItems: List<BottomBarNavItems> = listOf(BottomBarNavItems.Home, BottomBarNavItems.Categories, BottomBarNavItems.Settings)

    if (currentRoute != null) {
        if (currentRoute.split('/').count() <= 1) {
            NavigationBar {
                navBarItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = item.label)
                        },
                        label = {
                            Text(text = item.label)
                        }
                    )
                }
            }
        }
    }
}
