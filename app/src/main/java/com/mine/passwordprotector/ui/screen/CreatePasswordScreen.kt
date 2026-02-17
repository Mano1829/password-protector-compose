package com.mine.passwordprotector.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.mine.passwordprotector.R
import com.mine.passwordprotector.ui.modal.InputLengthModal
import com.mine.passwordprotector.ui.theme.Background
import com.mine.passwordprotector.ui.theme.Black
import com.mine.passwordprotector.ui.theme.Grey
import com.mine.passwordprotector.ui.theme.White
import com.mine.passwordprotector.ui.view.CircularSeekBar
import kotlin.math.roundToInt
import kotlin.random.Random

enum class OnClick {
    Back , Save , Refresh , Number , UpperCase , Symbol
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreatePasswordScreen(navController: NavHostController) {

    var totalNumbers by remember { mutableIntStateOf(3) }
    var totalUpperCases by remember { mutableIntStateOf(3) }
    var totalSymbols by remember { mutableIntStateOf(1) }
    var totalCharacters by remember { mutableIntStateOf(7) }
    var generatedPassword by remember { mutableStateOf("ABC123$") }

    var selectedInputModal by remember { mutableStateOf<OnClick?>(null) }
    var showInputModal by remember { mutableStateOf(false) }

    if(showInputModal && selectedInputModal != null) {

        val currentLength = if(selectedInputModal!! == OnClick.UpperCase) totalUpperCases
                            else if(selectedInputModal!! == OnClick.Number) totalNumbers
                            else totalSymbols

        var errorText by remember { mutableStateOf("") }

        InputLengthModal(
            selectedInputModal!!,
            currentLength,
            errorText ,
            onDismiss = {
                selectedInputModal = null
                showInputModal = true
            },
        ) { enteredLength ->

            if(enteredLength > 15) {
                errorText = "Maximum Character length is 15"
            }
            else {
                val totalLength = when (selectedInputModal!!) {
                    OnClick.Number -> enteredLength + totalUpperCases + totalSymbols
                    OnClick.UpperCase -> enteredLength + totalNumbers + totalSymbols
                    else -> enteredLength + totalNumbers + totalUpperCases
                }

                if (totalLength <= totalCharacters) {
                    if (selectedInputModal!! == OnClick.UpperCase) {
                        totalUpperCases = enteredLength
                    } else if (selectedInputModal!! == OnClick.Number) {
                        totalNumbers = enteredLength
                    } else {
                        totalSymbols = enteredLength
                    }
                    selectedInputModal = null
                    showInputModal = true
                } else {
                    errorText = "Current Maximum Characters is $totalCharacters"
                }
            }
        }
    }

    Scaffold(
        containerColor = White ,
    ) { padding ->

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .weight(2.5f)
                    .fillMaxWidth()
                    .background(color = Background ,
                        shape = RoundedCornerShape(
                            bottomStart = 20.dp , bottomEnd = 20.dp
                        )
                    )
                    .padding(horizontal = 15.dp )
            ) {
                Column() {
                    ContainerCPTopBar()
                    Spacer(Modifier.height(30.dp))
                    ContainerCPNumCaseSym(totalNumbers , totalUpperCases , totalSymbols) { onClick ->
                        selectedInputModal = onClick
                        showInputModal = true
                    }
                    CircularSeekBar(
                        min = 1.toFloat() ,
                        max = 15.toFloat() ,
                        initialValue = totalCharacters.toFloat() ,
                        strokeWidth = 25.dp ,
                        onValueChange = { value ->
                            totalCharacters = value.roundToInt()
                            Log.e("TAG" , "onValueChange :: $totalCharacters")
                        } ,
                        canvasSize = 250.dp ,
                        title = "Characters" ,
                        progressColor = Color(0xFFE91E63),
                        backgroundColor = Color(0xFFEFEFEF),
                        thumbColor = Color(0xFF8C9AFC),
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxWidth()
                    .background(
                        color = White ,
                    )
            ) {
                ContainerGeneratedPassword( generatedPassword ) { onClick ->
                    when(onClick) {
                        OnClick.Refresh -> {
                            generatedPassword = generatePassword(totalNumbers , totalUpperCases , totalSymbols , totalCharacters)
                        }
                        OnClick.Save -> {}
                        else -> {
                            navController.popBackStack()
                        }
                    }
                }
            }

        }

    }
}

@Composable
fun ContainerCPTopBar() {
    Column() {
        Spacer(Modifier.height(50.dp))
        Text("Create New" ,
            fontSize = 28.sp ,
            fontWeight = FontWeight.W500 ,
            color = White , )
        Spacer(Modifier.height(10.dp))
        Text("Solid Password" ,
            fontSize = 32.sp ,
            fontWeight = FontWeight.W800 ,
            color = White , )
    }
}

@Composable
fun ContainerCPNumCaseSym(totalNumbers : Int , totalUppercases : Int , totalSymbols : Int , onClick : (OnClick) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable {
                    onClick(OnClick.Number)
                },
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Numbers"  , fontSize = 16.sp , color = White)
            Spacer(Modifier.height(10.dp))
            Text(totalNumbers.toString() , fontSize = 22.sp , color = White , fontWeight = FontWeight.W800)
        }
        Spacer(modifier = Modifier.width(2.dp).background(color = Grey))
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable {
                    onClick(OnClick.UpperCase)
                },
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Uppercases"  , fontSize = 16.sp , color = White)
            Spacer(Modifier.height(10.dp))
            Text(totalUppercases.toString() , fontSize = 22.sp , color = White , fontWeight = FontWeight.W800)
        }
        Spacer(modifier = Modifier.width(2.dp).background(color = Grey))
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable {
                    onClick(OnClick.Symbol)
                },
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Symbols"  , fontSize = 16.sp , color = White)
            Spacer(Modifier.height(10.dp))
            Text(totalSymbols.toString() , fontSize = 22.sp , color = White , fontWeight = FontWeight.W800)
        }
    }
}

@Composable
fun ContainerGeneratedPassword(generatedPassword : String , onClick : (OnClick) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp) ,
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Generated Password".uppercase() , fontSize = 16.sp , fontWeight = FontWeight.W500 , color = Background )
        Spacer(Modifier.height(10.dp))
        Text(generatedPassword , fontSize = 25.sp , fontWeight = FontWeight.Bold , color = Black)
        Spacer(Modifier.height(50.dp))
        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back) ,
                contentDescription = "Back" ,
                modifier = Modifier.size(50.dp).align(Alignment.CenterStart).clickable {
                    onClick(OnClick.Back)
                } ,
                tint = Color.Red
            )
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFFB1BCFF) ,  shape = RoundedCornerShape(30.dp))
                    .align(Alignment.Center)
                    .clickable {
                        onClick(OnClick.Refresh)
                    },
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_refresh) ,
                    contentDescription = "Back" ,
                    modifier = Modifier.size(30.dp).align(Alignment.Center),
                    tint = Background
                )
            }
            Icon(
                painter = painterResource(R.drawable.ic_next) ,
                contentDescription = "Next" ,
                modifier = Modifier.size(50.dp).align(Alignment.CenterEnd).clickable {
                    onClick(OnClick.Save)
                } ,
                tint = Color.Green
            )
        }
    }
}

fun generatePassword(totalNumbers : Int , totalUppercases : Int , totalSymbols : Int , totalCharacters: Int) : String {

    val numbers = "0123456789"
    val uppercases = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val lowercases = "abcdefghijklmnopqrstuvwxyz"
    val symbols = "!@#$%^&*()-_=+[]{}?.,"
    val generatedPassword = mutableListOf<Char>()

    repeat(totalNumbers) {
        generatedPassword += numbers.random()
    }

    repeat(totalUppercases) {
        generatedPassword += uppercases.random()
    }

    repeat(totalSymbols) {
        generatedPassword += symbols.random()
    }

    if(totalCharacters != (totalNumbers + totalUppercases + totalSymbols)) {
        repeat(totalCharacters - (totalNumbers + totalUppercases + totalSymbols)) {
            generatedPassword += lowercases.random()
        }
    }

    generatedPassword.shuffle()

    return generatedPassword.joinToString("")
}

