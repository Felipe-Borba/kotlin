package com.example.basicnavigation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.basicnavigation.screens.DetailScreen
import com.example.basicnavigation.screens.HomeScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.HomeScreen.name) {
        composable(Screens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }

        composable(
            Screens.DetailScreen.name + "/{id}",
            arguments = listOf(navArgument(name = "id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            Log.d("detail id", "$id")

            DetailScreen(navController = navController)
        }
    }
}

