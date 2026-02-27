package com.mine.passwordprotector.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.mine.passwordprotector.R
import com.mine.passwordprotector.data.local.SessionManager
import com.mine.passwordprotector.ui.theme.Background
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

   // var _startDestination by mutableStateOf<String?>(null)
//        private set

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination: StateFlow<String?> = _startDestination


    init {
        checkAuthentication()
    }

    private fun checkAuthentication() {
        viewModelScope.launch {
            delay(2000)
            _startDestination.value = "home"
           // startDestination = "create_password"
            // val userExists = sessionManager.isLoggedIn()

        }
    }

}

@Composable
fun SplashScreen(navController : NavHostController , viewModel: SplashViewModel) {
   // val destination = viewModel.startDestination
    val destination by viewModel.startDestination.collectAsState()
    // LaunchedEffect reacts to the destination change
    LaunchedEffect(destination) {
        destination?.let {
            navController.navigate(it) {
                // Clear the splash from the backstack so user can't go back to it
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    // Your Splash UI (Logo/Animation)
    Column (modifier = Modifier.fillMaxSize().background(color = Background), verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally ) {
        Icon(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "Logo" ,
            tint = Color.White ,
            modifier = Modifier.size(100.dp) ,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Password Protector" ,
            color = Color.White  ,
            fontSize = 32.sp  ,
            fontWeight = FontWeight.Bold ,
        )
      //  Icon(Icons.Default.Android, contentSize = 100.dp, tint = Color.Green)
    }
}