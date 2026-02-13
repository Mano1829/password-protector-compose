package com.mine.passwordprotector.ui.screen

import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mine.passwordprotector.R
import com.mine.passwordprotector.data.local.Password
import com.mine.passwordprotector.data.local.SessionManager
import com.mine.passwordprotector.ui.modal.DeleteConfirmationModal
import com.mine.passwordprotector.ui.modal.StorePasswordModal
import com.mine.passwordprotector.ui.modal.ViewPasswordModal
import com.mine.passwordprotector.ui.navigation.Screen
import com.mine.passwordprotector.ui.theme.Background
import com.mine.passwordprotector.ui.theme.Black
import com.mine.passwordprotector.ui.theme.Grey
import com.mine.passwordprotector.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val passwordList = remember {
        mutableStateListOf(
            Password(
                1,
                "CID1",
                "Social",
                "Facebook",
                "manojadfasdfasdfdgdsafd@facebook.com",
                "KLFHKDGHKJSAFIEUHJEFKEHU",
                "12-02-2026 02:42 PM"
            ),
            Password(
                2,
                "CID1",
                "Entertainment",
                "Netflix",
                "manoj@facebook.com",
                "KLFHKDGHKJSAFIEUHJEFKEHU",
                "12-02-2026 02:42 PM"
            ),
            Password(
                3,
                "CID1",
                "Games",
                "BGMI",
                "manoj@facebook.com",
                "KLFHKDGHKJSAFIEUHJEFKEHU",
                "12-02-2026 02:42 PM"
            ),
            Password(
                4,
                "CID1",
                "Social",
                "Facebook",
                "manojms@facebook.com",
                "KLFHKDGHKJSAFIEUHJEFKEHU",
                "12-02-2026 02:42 PM"
            ),
            Password(
                5,
                "CID1",
                "Games",
                "COC",
                "manoj@facebook.com",
                "KLFHKDGHKJSAFIEUHJEFKEHU",
                "12-02-2026 02:42 PM"
            ),
            Password(
                6,
                "CID1",
                "Others",
                "Flipkart",
                "manoj@facebook.com",
                "KLFHKDGHKJSAFIEUHJEFKEHU",
                "12-02-2026 02:42 PM"
            ),
        )
    }

    val itemList = listOf<String>("All" , "Social" , "Entertainment" , "Games" , "Others")
    var selectedCategory by remember { mutableStateOf(itemList[0]) }
    var expanded by remember { mutableStateOf(false) }
    var filteredPasswordList by remember { mutableStateOf(passwordList.toList()) }

    var storePasswordModal by remember { mutableStateOf(false) }
    var selectedPasswordItem by remember { mutableStateOf<Password?>(null) }
    var editPassword by remember { mutableStateOf<Password?>(null) }
    var viewPasswordModal by remember { mutableStateOf(false) }
    var deletePasswordModal by remember { mutableStateOf(false) }

    if(storePasswordModal) {
        StorePasswordModal(editPassword
            , onDismiss = {
                storePasswordModal = false
            }
            , onSave = { newPassword ->
                storePasswordModal = false
                Log.e("TAG" , "newPassword :: ${newPassword.id} , ${newPassword.serviceTitle}")
                passwordList.add(newPassword)
                filteredPasswordList = if(selectedCategory == "All") passwordList else passwordList.filter { item -> item.category == selectedCategory }.toList()

                Log.e("TAG" , "passwordList.size :: ${passwordList.size} , filteredPasswordList.size :: ${filteredPasswordList.size}")
            }
        )
    }

    if(viewPasswordModal) {
        ViewPasswordModal(
            selectedPasswordItem!! ,
            onDismiss = {
                viewPasswordModal = false
            } ,
            onCopy = { copiedString ->
                Log.e("TAG" , "CopiedString :: $copiedString")
                viewPasswordModal = false
            } ,
            onEdit =  { password ->
                editPassword = password
                viewPasswordModal = false
                storePasswordModal = true
            }
        )
    }

    if(deletePasswordModal) {
        DeleteConfirmationModal(selectedPasswordItem!! ,
            onConfirm = { password ->
                deletePasswordModal = false
            } ,
            onDismiss = {
                deletePasswordModal = false
            }
        )
    }

    Scaffold(
        containerColor = Background,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExpandableFAB(
                expanded =  expanded ,
                onFabClick = {
                    expanded = !expanded
                },
                onStorePasswordClick = {
                    Log.e("TAG", "Store Password Clicked")
                    expanded = !expanded
                    editPassword = null
                    storePasswordModal = true
                },
                onGeneratePasswordClick = {
                    Log.e("TAG", "Generate New Password Clicked")
                    expanded = !expanded
                }
            )
        },
        topBar = {
            ContainerTopBar()
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
        ) {
            ContainerCategoryRows(itemList) { item ->
                //   filteredPasswordList = passwordList.stream().filter { password ->  password.category == item }.toList()
                selectedCategory = item
                filteredPasswordList =
                    if (item == "All") passwordList else passwordList.filter { password -> password.category == item }.toList()
            }
            Spacer(Modifier.height(20.dp))
            Text(
                "${filteredPasswordList.size} Passwords",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                modifier = Modifier.padding(start = 25.dp)
            )
            ContainerPasswordList(
                filteredPasswordList ,
                onItemClick = { selectedPassword , mode ->
                    selectedPasswordItem = selectedPassword
                    if(mode == 2) {
                        deletePasswordModal = true
                        viewPasswordModal = false
                    }
                    else {
                        viewPasswordModal = true
                        deletePasswordModal = false
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContainerTopBar() {
    TopAppBar(
        modifier = Modifier.height(150.dp).background(Background),
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Background ,
            titleContentColor = Color.White ,
        ) ,
        title = {
            Column() {
                Spacer(Modifier.height(35.dp))
                Text("Keep Your"  , fontSize = 28.sp , fontWeight = FontWeight.W500)
                Spacer(Modifier.height(10.dp))
                Text("Passwords Safe" , fontSize = 32.sp , fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
fun ExpandableFAB( expanded : Boolean , onFabClick : () -> Unit ,  onStorePasswordClick : () -> Unit , onGeneratePasswordClick : () -> Unit ) {
  //  var expanded by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.End ,
    ) {
        if(expanded) {
            Column (
                modifier = Modifier
                    .background(color = Color(0xFFEFEFEF), shape = RoundedCornerShape(5.dp))
                    .width(200.dp)
                    .height(100.dp) ,
                verticalArrangement = Arrangement.SpaceEvenly
             //   horizontalAlignment = Alignment.CenterHorizontally ,
              //  verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Store Password"
                    , fontSize = 16.sp
                    , fontWeight = FontWeight.W500
                    , color = Black
                    , modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth()
                        .clickable {
                            onStorePasswordClick()
                        }
                )
                Text("Generate New"
                    , fontSize = 16.sp
                    , fontWeight = FontWeight.W500
                    , color = Black
                    , modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth()
                        .clickable {
                            onGeneratePasswordClick()
                        }
                )
            }
            Spacer(Modifier.height(10.dp))
        }
        FloatingActionButton(
            onClick = {
                onFabClick()
            } ,
            containerColor = Grey ,
            shape = RoundedCornerShape(30.dp),
        ) {
            Icon(
                if(expanded) Icons.Default.Close else Icons.Default.Add ,
                contentDescription = "ExpandedFloatingButton" ,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}

@Composable
fun ContainerCategoryRows(itemList : List<String> , onItemClick : (String) -> Unit) {

    var selectedItem by remember { mutableStateOf(itemList[0]) }

    val selectedModifier = Modifier
        .padding(start = 10.dp , end = 5.dp)
        .background(shape = RoundedCornerShape(10.dp) , color = Black)
       // .border(width = 1.dp , color = Black , shape = RoundedCornerShape(10.dp) ,)

    val defaultModifier = Modifier
        .padding(start = 10.dp , end = 5.dp)
        .background(shape = RoundedCornerShape(10.dp) , color = Background)

    LazyRow(
        modifier = Modifier.fillMaxWidth().height(60.dp) ,
        verticalAlignment = Alignment.CenterVertically ,
       // horizontalArrangement = Arrangement.SpaceAround ,
        contentPadding = PaddingValues(10.dp)
    ) {
        items(itemList)  { item ->
            Box(
                modifier = if(selectedItem == item) selectedModifier else defaultModifier.clickable {
                    selectedItem = item
                    onItemClick(item)
                }
            ) {
                Text(item ,
                    modifier = Modifier.padding(horizontal = 15.dp , vertical = 10.dp ) ,
                    color =  if(selectedItem == item) White else Grey
                )
            }
        }
    }
}

@Composable
fun ContainerPasswordList(passwordList : List<Password> , onItemClick : (Password , Int) -> Unit ) { //selectedPassword , mode -> 1 View , 2 -Delete
    LazyColumn(
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {
        items(passwordList) { password ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(15.dp)
                    .background(
                        color = Color.White ,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        //selectedPasswordItem = password
                        onItemClick(password , 1)
                    }
            ) {
               Column {
                   Box(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(start = 10.dp , end = 10.dp , top = 15.dp)
                   ) {
                       Text(password.category
                           , fontSize = 14.sp
                           , color = Color.Black
                           , fontWeight = FontWeight.W500
                           , modifier = Modifier
                               .align(Alignment.CenterStart)
                               .background(color = Color(0xFFB1BCFF), shape = RoundedCornerShape(10.dp))
                               .padding(horizontal = 10.dp , vertical = 5.dp)
                       )
                       Icon(
                           painter = painterResource(R.drawable.ic_delete) ,
                           modifier = Modifier.size(25.dp).align(Alignment.CenterEnd).clickable {
                               onItemClick(password , 2)
                           } ,
                           contentDescription = "Delete" ,
                       )
                   }
                   Spacer(Modifier.height(5.dp))
                   Box(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(start = 10.dp , end = 10.dp , top = 10.dp)
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
                               Text(password.serviceUsername , maxLines = 1 , overflow = TextOverflow.Ellipsis , fontSize = 18.sp , fontWeight = FontWeight.W800 , color = Black , modifier = Modifier.width(250.dp))
                               Text(password.serviceTitle , fontSize = 15.sp , color = Black)
                           }
                       }
                   }
               }
            }
        }
    }
}




