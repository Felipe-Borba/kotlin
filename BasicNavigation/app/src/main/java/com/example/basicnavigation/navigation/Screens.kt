package com.example.basicnavigation.navigation

import java.lang.IllegalArgumentException

enum class Screens {
    HomeScreen,
    DetailScreen;
    companion object {
        fun fromRoute(route: String?): Screens
        = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            DetailScreen.name -> DetailScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognize")
        }
    }
}