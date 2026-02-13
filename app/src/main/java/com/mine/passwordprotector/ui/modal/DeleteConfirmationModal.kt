package com.mine.passwordprotector.ui.modal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mine.passwordprotector.data.local.Password
import com.mine.passwordprotector.ui.theme.Background
import com.mine.passwordprotector.ui.theme.Black
import com.mine.passwordprotector.ui.theme.Grey
import com.mine.passwordprotector.ui.theme.White

@Composable
fun DeleteConfirmationModal(password: Password, onConfirm : (Password) -> Unit, onDismiss : () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        } ,
        title = {
            Text("Delete Password")
        } ,
        text = {
            Text("Are you sure want to delete this password ?")
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(password) } ,
                shape = RoundedCornerShape(10.dp) ,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Background ,
                    contentColor =  White
                )
            ) {
                Text("Confirm")
            }
        } ,
        dismissButton = {
            Button(
                onClick = { onDismiss() } ,
                shape = RoundedCornerShape(10.dp) ,
                colors = ButtonDefaults.buttonColors(
                    containerColor = White ,
                    contentColor = Background
                ) ,
                border = BorderStroke(
                    width = 1.dp ,
                    color = Background ,
                )
            ) {
                Text("Cancel")
            }
        } ,
        shape = RoundedCornerShape(10.dp) ,
        containerColor = White ,
        titleContentColor = Black ,
        textContentColor = Grey ,
    )
}