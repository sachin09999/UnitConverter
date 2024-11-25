package com.project.unitconverter.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.unitconverter.model.ConversionCategory
import com.project.unitconverter.ui.screens.ConverterScreen
import com.project.unitconverter.ui.screens.HomeScreen

@Composable
fun UnitConverterApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onCategoryClick = { category ->
                    navController.navigate(category.route)
                }
            )
        }

        ConversionCategory.categories.forEach { category ->
            composable(category.route) {
                ConverterScreen(
                    category = category,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}