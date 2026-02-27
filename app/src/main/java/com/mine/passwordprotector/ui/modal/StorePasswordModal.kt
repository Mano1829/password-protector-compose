package com.mine.passwordprotector.ui.modal

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mine.passwordprotector.R
import com.mine.passwordprotector.data.local.Password
import com.mine.passwordprotector.ui.theme.Background
import com.mine.passwordprotector.ui.theme.Grey
import com.mine.passwordprotector.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorePasswordModal(password: Password? , onDismiss : () -> Unit , onSave : (Password) -> Unit) {

    val categoryList = listOf<String>("Social" , "Entertainment" , "Games" , "Others")
    val sheetState = rememberModalBottomSheetState (
        skipPartiallyExpanded = false
    )

    var categorySelection by remember { mutableStateOf(password?.category ?: categoryList[0]) }
    var serviceTitle by remember { mutableStateOf<String>( password?.serviceTitle ?: "" ) }
    var serviceUsername by remember { mutableStateOf<String>(password?.serviceUsername ?: "") }
    var servicePassword by remember { mutableStateOf<String>(password?.serviceEncryptedHash ?: "") }
    var serviceTitleError by remember { mutableStateOf("") }
    var serviceUsernameError by remember { mutableStateOf("") }
    var servicePasswordError by remember { mutableStateOf("") }
    var servicePasswordVisible by remember { mutableStateOf(false) }


    ModalBottomSheet(
        onDismissRequest = onDismiss ,
        sheetState = sheetState ,
        shape = RoundedCornerShape(topStart = 10.dp , topEnd = 10.dp) ,
        containerColor = White ,
    ) {
        Box(
            modifier = Modifier
                .padding(start = 20.dp , end = 15.dp , top = 10.dp , bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Column {
                CategoryDropDown(categoryList , categorySelection) { selection ->
                    categorySelection = selection
                    Log.e("TAG" , "SelectedCategory :: $categorySelection")
                }
                Spacer(Modifier.height(15.dp))
                OutlinedTextField(
                    value = serviceTitle ,
                    onValueChange = { value ->
                        serviceTitle = value.trim()
                    } ,
                    label = {
                        Text("Enter Service Title")
                    } ,
                    supportingText = {
                        Text(serviceTitleError , color = Color.Red )
                    } ,
                    shape = RoundedCornerShape(10.dp) ,
                    modifier = Modifier.fillMaxWidth() ,
                )
                OutlinedTextField(
                    value = serviceUsername ,
                    onValueChange = { value ->
                        serviceUsername = value.trim()
                    } ,
                    label = {
                        Text("Enter Service Username")
                    } ,
                    supportingText = {
                        Text(serviceUsernameError , color = Color.Red )
                    } ,
                    shape = RoundedCornerShape(10.dp) ,
                    modifier = Modifier.fillMaxWidth() ,
                )
                OutlinedTextField(
                    value = servicePassword ,
                    onValueChange = { value ->
                        servicePassword = value.trim()
                    } ,
                    label = {
                        Text("Enter Service Password")
                    } ,
                    supportingText = {
                        Text(servicePasswordError , color = Color.Red )
                    } ,
                    shape = RoundedCornerShape(10.dp) ,
                    modifier = Modifier.fillMaxWidth() ,
                    visualTransformation = if(servicePasswordVisible) VisualTransformation.None  else PasswordVisualTransformation() ,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(
                                if(servicePasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility_on
                            ) ,
                            contentDescription = "Password Visible" ,
                            tint = Grey ,
                            modifier = Modifier.clickable {
                                servicePasswordVisible = !servicePasswordVisible
                            }
                        )
                    }
                )
                Spacer(Modifier.height(40.dp))
                Row(
                    modifier = Modifier.fillMaxWidth() ,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onDismiss ,
                        border = BorderStroke(
                            width = 1.dp ,
                            color = Background ,
                        ) ,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = White ,
                            contentColor = Background ,
                        ),
                        modifier = Modifier
                            .width(150.dp)
                            .height(50.dp)
                            .padding(end = 10.dp)
                        ,
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 2.dp ,
                        )
                    ) {
                        Text(
                            "Cancel"  ,
                            fontSize = 18.sp ,
                            fontWeight = FontWeight.W800 ,
                            color = Background
                        )
                    }

                    Button(
                        onClick = {
                            if(serviceTitle.isEmpty()) {
                                serviceTitleError = "Please Enter Service Title"
                            }
                            else if(serviceUsername.isEmpty()) {
                                serviceTitleError = ""
                                serviceUsernameError = "Please Enter Service Username"
                            }
                            else if(servicePassword.isEmpty()) {
                                serviceUsernameError = ""
                                servicePasswordError = "Please Enter Service Password"
                            }
                            else if(servicePassword.length > 15) {
                                servicePasswordError = "Maximum Password Length is 15 Characters"
                            }
                            else {
                                servicePasswordError = ""

                                val id = if(password == null) System.currentTimeMillis()
                                         else {
                                             if(password.id.toInt() == 0) System.currentTimeMillis()
                                             else password.id
                                         }

                                val newPassword = Password(
                                    id,
                                    password?.custId ?: "CID1",
                                    categorySelection ,
                                    serviceTitle ,
                                    serviceUsername ,
                                    servicePassword ,
                                    ""
                                )
                                onSave(newPassword)
                            }
                        //    onSave()
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .height(50.dp)
                            .padding(start = 10.dp) ,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Background ,
                            contentColor = White ,
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 2.dp ,
                        )
                    ) {
                        Text(
                            "Save"  ,
                            fontSize = 18.sp ,
                            fontWeight = FontWeight.W800 ,
                            color = White
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropDown(categoryList : List<String> , selectedCategoryItem : String ,  onSelection : (String) -> Unit ) {

    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(selectedCategoryItem) }

    ExposedDropdownMenuBox(
        expanded = expanded ,
        onExpandedChange = {
            expanded = !expanded
        } ,
        modifier = Modifier.padding(end = 15.dp)
    ) {
        TextField(
            value = selectedItem ,
            onValueChange = {} ,
            readOnly = true ,
            label = { Text("Select Option") } ,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            } ,
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded ,
            onDismissRequest = { expanded = false }
        ) {
            categoryList.forEach { value ->
                DropdownMenuItem(
                    text = {
                        Text(value)
                    } ,
                    onClick = {
                        selectedItem = value
                        expanded = false
                        onSelection(selectedItem)
                    }
                )
            }
        }
    }
}