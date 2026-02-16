package com.mine.passwordprotector.ui.modal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mine.passwordprotector.ui.screen.OnClick
import com.mine.passwordprotector.ui.theme.Background
import com.mine.passwordprotector.ui.theme.Black
import com.mine.passwordprotector.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputLengthModal(onClick: OnClick, currentLength : Int, onDismiss : () -> Unit, onSave : (Int) -> Unit ) {

    var totalLength by remember { mutableIntStateOf(currentLength) }

    AlertDialog(
        onDismissRequest = onDismiss ,
        shape = RoundedCornerShape(10.dp) ,
        containerColor = White ,
        confirmButton = {
            Button(
                onClick = {
                    if(totalLength > 15) {

                    }
                    else {
                        onSave(totalLength)
                    }
                  //  onSave(1)
                } ,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Background ,
                    contentColor = White
                )
            ) {
                Text("Save" , fontWeight = FontWeight.W800 )
            }
        } ,
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                } ,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(width = 1.dp , color = Background),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White ,
                    contentColor = Background
                )
            ) {
                Text("Cancel" , fontWeight = FontWeight.W800 )
            }
        } ,
        title = {
            val title = when (onClick) {
                OnClick.UpperCase -> "Total Uppercases"
                OnClick.Number -> "Total Numbers"
                else -> "Total Symbols"
            }
            Text(title , color = Black)
        } ,
        text = {
            OutlinedTextField(
                value = totalLength.toString() ,
                onValueChange = { value ->
                    if(value.toIntOrNull() != null) {
                        totalLength = value.toInt()
                    }
                } ,
                label = {
                    Text("Enter Length")
                } ,
                modifier = Modifier.fillMaxWidth()
              //  placeholder = "Enter Length" ,

            )
        }
    )

}