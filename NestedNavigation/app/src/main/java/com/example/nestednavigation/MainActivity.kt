package com.example.nestednavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.nestednavigation.ui.theme.NestedNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NestedNavigationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "auth") {
                    composable("about") {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "about page")
                        }
                    }
                    navigation(startDestination = "login", route = "auth") {
                        composable("login") {
                            val viewModel =
                                it.sharedViewModel<AuthViewModel>(navController) // this is a shared state between auth navigation and it's cleared when this backStack goes off

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "login page")
                                Button(onClick = {
                                    navController.navigate("calendar") {
                                        popUpTo("auth") {
                                            inclusive = true
                                        }
                                    }
                                }) {
                                    Text(text = "go to calendar")
                                }
                                Button(onClick = {
                                    navController.navigate("register")
                                }) {
                                    Text(text = "go to register")
                                }
                                Button(onClick = {
                                    navController.navigate("forgot_password")
                                }) {
                                    Text(text = "go to forgot password")
                                }
                                Button(onClick = {
                                    navController.navigate("about")
                                }) {
                                    Text(text = "go to about")
                                }
                            }
                        }
                        composable("register") {
                            val viewModel =
                                it.sharedViewModel<AuthViewModel>(navController) // this is a shared state between auth navigation and it's cleared when this backStack goes off

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "register page")
                            }
                        }
                        composable("forgot_password") {
                            val viewModel =
                                it.sharedViewModel<AuthViewModel>(navController) // this is a shared state between auth navigation and it's cleared when this backStack goes off

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "forgot password page")
                            }
                        }
                    }
                    navigation(startDestination = "calendar_overview", route = "calendar") {
                        composable("calendar_overview") {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "calendar overview page")
                            }
                        }
                        composable("calendar_entry") {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "calendar entry page")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}

