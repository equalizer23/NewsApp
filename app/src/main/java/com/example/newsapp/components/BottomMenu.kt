package com.example.newsapp.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsapp.BottomMenuScreen

@Composable
fun BottomMenu(navController: NavController){

    val menu = listOf(
        BottomMenuScreen.TopNews,
        BottomMenuScreen.Categories,
        BottomMenuScreen.Source)
    
    BottomNavigation(contentColor = Color.White) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        menu.forEach {
            BottomNavigationItem(
                selected = currentRoute == it.route,
                alwaysShowLabel = true,
                label = { Text(text = it.title)},
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                onClick = {
                    navController.navigate(it.route){
                        navController.graph.startDestinationRoute?.let{
                            route ->
                            popUpTo(route){
                                saveState = true
                            }
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = it.icon, contentDescription = "Icon")})
        }

    }
}