package com.mine.passwordprotector.ui.modal


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mine.passwordprotector.R
import com.mine.passwordprotector.data.local.Password
import com.mine.passwordprotector.ui.theme.Background
import com.mine.passwordprotector.ui.theme.Black
import com.mine.passwordprotector.ui.theme.Grey
import com.mine.passwordprotector.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPasswordModal(password: Password , onDismiss : () -> Unit , onCopy : (String) -> Unit , onEdit : (Password) -> Unit ) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var passwordVisible by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss ,
        sheetState = sheetState ,
        shape = RoundedCornerShape(topStart = 10.dp , topEnd = 10.dp) ,
        containerColor = White ,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp , vertical = 10.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp , end = 10.dp)
                ) {
                    Text(
                        password.category,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .background(
                                color = Color(0xFFB1BCFF),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(35.dp)
                            .background(
                                color = Grey,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .align(Alignment.CenterEnd)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.align(Alignment.Center).clickable {
                                onEdit(password)
                            }
                        ) {
                            Text("Edit", fontSize = 15.sp, color = Black)
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Black,
                                modifier = Modifier
                                    .size(23.dp)
                                    .padding(start = 5.dp)
                            )
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
                Box(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(start = 10.dp , end = 10.dp)
                ) {
                    Row() {
                        Box(modifier = Modifier
                            .size(50.dp)
                            .background(color = Color(0xFFB1BCFF) , shape = RoundedCornerShape(10.dp))
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_logo) ,
                                contentDescription = "ItemLogo" ,
                                tint = White ,
                                modifier = Modifier.size(35.dp).align(Alignment.Center) ,
                            )
                        }
                        Spacer(Modifier.width(25.dp))
                        Column() {
                            Text(password.serviceUsername , maxLines = 1 , overflow = TextOverflow.Ellipsis , fontSize = 18.sp , fontWeight = FontWeight.W800 , color = Black , modifier = Modifier.width(300.dp))
                            Text(password.serviceTitle , fontSize = 15.sp , color = Black)
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
                Column(
                    verticalArrangement = Arrangement.Center ,
                    horizontalAlignment = Alignment.CenterHorizontally ,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Password" , color = Background , fontSize = 18.sp , fontWeight = FontWeight.W500)
                    Spacer(Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text( if(passwordVisible) password.serviceEncryptedHash else "•".repeat(password.serviceEncryptedHash.length)
                            , fontSize = if(passwordVisible) 18.sp else 25.sp
                            , fontWeight = if(passwordVisible) FontWeight.Bold else FontWeight.ExtraBold
                            , color = Black

                        )
                        Spacer(Modifier.width(10.dp))
                        Icon(
                            painter = painterResource( if(passwordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility_on ) ,
                            contentDescription = "Password Visible" ,
                            modifier = Modifier.size(30.dp) .clickable { passwordVisible = !passwordVisible },
                        )
                    }
                }
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
                        onClick = { onCopy(password.serviceEncryptedHash) },
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
                            "Copy"  ,
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

