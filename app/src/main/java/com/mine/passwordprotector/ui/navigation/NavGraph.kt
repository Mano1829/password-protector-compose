package com.mine.passwordprotector.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mine.passwordprotector.ui.screen.HomeScreen
import com.mine.passwordprotector.ui.screen.SplashScreen
import com.mine.passwordprotector.ui.screen.SplashViewModel


sealed class Screen(val route : String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
}


@Composable
fun AppNavigation(navController : NavHostController) {

    NavHost(navController = navController , startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            val viewModel : SplashViewModel = hiltViewModel()
            SplashScreen(navController , viewModel)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
    }

}