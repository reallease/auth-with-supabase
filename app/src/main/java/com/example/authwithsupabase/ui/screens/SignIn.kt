package com.example.authwithsupabase.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SignIn(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    val password by remember { mutableStateOf("") }


    Column {
        Text(
            modifier = Modifier
                .padding(top = 30.dp),
            text = "Faça seu registro"
        )
        TextField(
            value = email,
            onValueChange = { it ->
                email = it
            },
            label = { Text("Email")}
        )
    }
}