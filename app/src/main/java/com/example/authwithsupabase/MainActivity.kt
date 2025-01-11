package com.example.authwithsupabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authwithsupabase.data.model.UserState
import com.example.authwithsupabase.ui.screens.SignIn
import com.example.authwithsupabase.ui.theme.AuthWithSupabaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuthWithSupabaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            ScreenMain(modifier = Modifier.padding(innerPadding))
                        }
                        composable("signIn") {
                            SignIn()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MainScreen(
        viewModel: SupabaseAuthViewModel = viewModel()
    ) {
        val context = LocalContext.current
        val userState by viewModel.userState // state the user


        // states mutables
        var userEmail by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }

        var currentUserState by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            viewModel.isUserLoggedIn( // check if user are logged in
                context
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = userEmail,
                placeholder = {
                    Text(text = "Enter email")
                },
                onValueChange = {
                    userEmail = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                value = userPassword,
                placeholder = {
                    Text(text = "Enter Password")
                },
                onValueChange = {
                    userPassword = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = {
                    viewModel.signUp(
                        context,
                        userEmail,
                        userPassword
                    )
                }
            ) {
                Text(text = "Sign Up")
            }
            Button(
                onClick = {
                    viewModel.login(
                        context,
                        userEmail,
                        userPassword
                    )
                }
            ) {
                Text(text = "Login")
            }

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                onClick = {
                    viewModel.logout()
                }
            ) {
                Text(text = "Logout")
            }

            when (userState) {
                is UserState.Loading -> {
                    LoadingComponent()
                }

                is UserState.Success -> {
                    val message = (userState as UserState.Success).message
                    currentUserState = message
                }

                is UserState.Error -> {
                    val message = (userState as UserState.Error).message
                    currentUserState = message
                }
            }

            if (currentUserState.isNotEmpty()) {
                Text(text = currentUserState)
            }
        }
    }
}

@Composable
fun ScreenMain(modifier: Modifier = Modifier) {
    Column{
        Text(text = "Welcome to screen main")
    }
}