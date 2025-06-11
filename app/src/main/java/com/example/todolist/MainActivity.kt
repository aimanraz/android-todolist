package com.example.todolist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold // Import Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.ui.theme.TodolistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // This is important for edge-to-edge drawing
        setContent {
            TodolistTheme {
                MainPage()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {

    val todoName = remember {
        mutableStateOf("")
    }

    val myContext = LocalContext.current

    val itemList = readData(myContext)

    val focusManager = LocalFocusManager.current

    val deleteDialogStatus = remember {
        mutableStateOf(false)
    }

    val updateDialogStatus = remember {
        mutableStateOf(false)
    }

    val textDialogStatus = remember {
        mutableStateOf(false)
    }

    val clickedIndexValue = remember {
        mutableIntStateOf(0)
    }

    val clickedItem = remember {
        mutableStateOf("")
    }

    // Wrap your entire content in a Scaffold
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding -> // Scaffold provides padding values to correctly place content

        Column (
            // Apply the innerPadding to your Column
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // This is the key change!
        ) {
            Row (
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextField(
                    value = todoName.value,
                    onValueChange = {todoName.value = it},
                    label = {Text(text = "Enter TODO")},
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color.Green,
                        unfocusedLabelColor = Color.White,
                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        cursorColor = Color.White,
                    ),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                        .weight(7f)
                        .height(60.dp)
                    ,
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )

                Spacer(modifier = Modifier.width(5.dp))

                Button(onClick = {
                    if (todoName.value.isNotEmpty()){
                        itemList.add(todoName.value)
                        writeData(itemList, myContext)
                        todoName.value = ""
                        focusManager.clearFocus()
                    } else {
                        Toast.makeText(myContext, "Please enter a TODO", Toast.LENGTH_SHORT).show()
                    }
                },
                    modifier = Modifier
                        .weight(3f)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.green),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.dp, Color.Black),
                ) {
                    Text(text = "Add", fontSize = 20.sp)
                }
            }

            LazyColumn {
                items(
                    count = itemList.size,
                    itemContent = { index ->
                        val item = itemList[index]

                        Card (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 1.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White,
                            ),
                            shape = RoundedCornerShape(0.dp)
                        ) {

                            Row (
                                modifier = Modifier.fillMaxWidth().padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {

                                Text(
                                    text = item,
                                    fontSize = 18.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .width(300.dp)
                                        .clickable {
                                            clickedItem.value = item
                                            textDialogStatus.value = true
                                        }
                                    ,
                                )

                                Row {
                                    IconButton(onClick = {
                                        clickedItem.value = item
                                        updateDialogStatus.value = true
                                    }){
                                        Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.White)
                                    }

                                    IconButton(onClick = {
                                        clickedIndexValue.intValue = index
                                        deleteDialogStatus.value = true
                                    }){
                                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.White)
                                    }
                                }
                            }
                        }
                    }
                )
            }

            if (deleteDialogStatus.value) {
                BasicAlertDialog(
                    onDismissRequest = {
                        deleteDialogStatus.value = false
                    },
                ) {
                    Surface(
                        modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                        shape = MaterialTheme.shapes.large,
                        tonalElevation = AlertDialogDefaults.TonalElevation
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Are you sure you want to delete this item?",
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(
                                    onClick = {
                                        deleteDialogStatus.value = false
                                    },
                                ) {
                                    Text("Cancel")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                TextButton(
                                    onClick = {
                                        deleteDialogStatus.value = false
                                        itemList.removeAt(clickedIndexValue.intValue)
                                        writeData(itemList, myContext)
                                        Toast.makeText(myContext, "Deleted", Toast.LENGTH_SHORT).show()
                                    },
                                ) {
                                    Text("Confirm")
                                }
                            }
                        }
                    }
                }
            }

            if (updateDialogStatus.value) {
                BasicAlertDialog(
                    onDismissRequest = {
                        updateDialogStatus.value = false
                    },
                ) {
                    Surface(
                        modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                        shape = MaterialTheme.shapes.large,
                        tonalElevation = AlertDialogDefaults.TonalElevation
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            TextField(value = clickedItem.value, onValueChange = { clickedItem.value = it })
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(
                                    onClick = {
                                        updateDialogStatus.value = false
                                    },
                                ) {
                                    Text("Cancel")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                TextButton(
                                    onClick = {
                                        itemList[clickedIndexValue.intValue] = clickedItem.value
                                        updateDialogStatus.value = false
                                        writeData(itemList, myContext)
                                        Toast.makeText(myContext, "Updated", Toast.LENGTH_SHORT).show()
                                    },
                                ) {
                                    Text("Confirm")
                                }
                            }
                        }
                    }
                }
            }

            if (textDialogStatus.value) {
                BasicAlertDialog(
                    onDismissRequest = {
                        textDialogStatus.value = false
                    },
                ) {
                    Surface(
                        modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                        shape = MaterialTheme.shapes.large,
                        tonalElevation = AlertDialogDefaults.TonalElevation
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(clickedItem.value)
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Spacer(modifier = Modifier.width(8.dp))
                                TextButton(
                                    onClick = {
                                        textDialogStatus.value = false
                                    },
                                ) {
                                    Text("OK")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}