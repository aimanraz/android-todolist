package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorRes
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.ui.theme.TodolistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodolistTheme {
                MainPage()
            }
        }
    }
}

@Composable
fun MainPage() {

    val todoName = remember {
        mutableStateOf("")
    }

    Column (modifier = Modifier.fillMaxSize()) {
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
                    .weight(7f) // 3f for the button wight
                    .height(60.dp)
                ,
                textStyle = TextStyle(textAlign = TextAlign.Center)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Button(onClick = { /*TODO*/ },
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
    }

}
