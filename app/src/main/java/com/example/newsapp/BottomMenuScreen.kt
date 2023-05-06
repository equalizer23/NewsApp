package com.example.newsapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Source
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenuScreen(
    val route: String,
    val icon: ImageVector,
    val title: String
)
{
    object TopNews: BottomMenuScreen(
        "home",
        Icons.Outlined.Home,
        "Home"
    )

    object Categories: BottomMenuScreen(
        "categories",
        Icons.Outlined.Category,
        "Categories"
    )

    object Source: BottomMenuScreen(
        "sources",
        Icons.Outlined.Source,
        "Sources"
    )
}
