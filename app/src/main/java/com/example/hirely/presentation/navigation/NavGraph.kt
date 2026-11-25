package com.example.hirely.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import kotlinx.coroutines.launch
import com.example.hirely.data.DataStoreManager
import com.example.hirely.presentation.ui.screen.home.HomeScreen
import com.example.hirely.presentation.ui.screen.joblisting.JobListingScreen
import com.example.hirely.presentation.ui.screen.login.LoginScreen
import com.example.hirely.presentation.ui.screen.onboarding.OnboardingScreen
import com.example.hirely.presentation.ui.screen.profile.ProfileScreen
import com.example.hirely.presentation.ui.screen.signup.SignupScreen
import kotlinx.coroutines.flow.first

@Composable
fun NavGraph() {

    // Navigation controller
    val navController = rememberNavController()

    // Context
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    // DataStore Manager object
    val dataStoreManager = DataStoreManager(context)

    // Onboarding status
    val onboardingCompleted = remember {
        mutableStateOf<Boolean?>(null)
    }

// DataStore se actual value read karenge
    LaunchedEffect(Unit) {
        onboardingCompleted.value =
            dataStoreManager.isOnboardingCompleted.first()
    }

// Jab tak DataStore ka result nahi aata,
// tab tak screen show nahi hogi
    if (onboardingCompleted.value == null) {
        return
    }


    // -------------------------------------------------
    // Navigation Graph
    // -------------------------------------------------

    NavHost(
        navController = navController,

        // First time → Onboarding
        // Already completed → Login
       // startDestination = "job_listing"

                //startDestination = "home"
        startDestination =
            if (onboardingCompleted.value == true) {
                "login"
            } else {
                "onboarding"
            }
    ) {

        composable("job_listing") {
            JobListingScreen()
        }

        // -------------------------------------------------
        // ONBOARDING
        // -------------------------------------------------

        composable("onboarding") {

            OnboardingScreen(

                onOnboardingComplete = {

                    // Onboarding complete/skip save done
                    scope.launch {
                        dataStoreManager.completeOnboarding()

                        navController.navigate("login") {
                            popUpTo("onboarding") {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }


        // -------------------------------------------------
        // LOGIN
        // -------------------------------------------------

        composable("login") {

            LoginScreen(

                onLoginSuccess = { role ->

                    navController.navigate("home/$role") {

                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },

                onSignupClick = {

                    navController.navigate("signup")
                }
            )
        }

        // -------------------------------------------------
// HOME
// -------------------------------------------------

        composable("home/{role}") { backStackEntry ->

            val role =
                backStackEntry.arguments?.getString("role")
                    ?: "USER"

            HomeScreen(

                userName = if (role == "ADMIN") {
                    "Admin"
                } else {
                    "User"
                },

                role = role,

                onCreateJobClick = {

                    navController.navigate("create_job")
                },

                onJobListClick = {

                    navController.navigate("job_listing")
                }
            )
        }



        // -------------------------------------------------
        // SIGNUP
        // -------------------------------------------------

        composable("signup") {

            SignupScreen(

                onSignupSuccess = {

                    Toast.makeText(
                        context,
                        "Registration successful!",
                        Toast.LENGTH_SHORT
                    ).show()

                    navController.navigate("login") {

                        popUpTo("signup") {
                            inclusive = true
                        }
                    }
                },

                onLoginClick = {

                    navController.navigate("login") {

                        popUpTo("signup") {
                            inclusive = true
                        }
                    }
                }
            )
        }


        // -------------------------------------------------
        // PROFILE
        // -------------------------------------------------

        composable("profile") {

            ProfileScreen(

                onLogoutClick = {

                    navController.navigate("login") {

                        popUpTo("profile") {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}