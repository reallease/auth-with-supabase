package com.example.authwithsupabase

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authwithsupabase.data.model.UserState
import com.example.authwithsupabase.data.network.SupabaseClient.client
import com.example.authwithsupabase.utils.SharedPreferenceHelper
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class SupabaseAuthViewModel : ViewModel() {
    private val _userState =
        mutableStateOf<UserState>(UserState.Loading) // current status user is loading
    val userState: State<UserState> = _userState

    // features are here
    fun signUp(
        context: Context, userEmail: String, userPassword: String
    ) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.auth.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("registered user successfuly!")
            } catch (e: Exception) { // error
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }

    private fun saveToken(context: Context) {
        viewModelScope.launch { // get token the user
            val accessToken = client.auth.currentAccessTokenOrNull()
            val sharedPref = SharedPreferenceHelper(context)
            sharedPref.saveDataString("accessToken", accessToken) // save token the user
        }
    }

    private fun getToken(context: Context): String? {
        val sharedPref = SharedPreferenceHelper(context)
        return sharedPref.getStringData("accessToken")
    }

    fun login(
        context: Context, userEmail: String, userPassword: String
    ) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.auth.signInWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Logged in successuly!")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.auth.signOut()
                _userState.value = UserState.Success("Logged out successfuly!")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }

    fun isUserLoggedIn(
        context: Context
    ) {
        viewModelScope.launch {
            try {
                val token = getToken(context)
                if(token.isNullOrEmpty()){ // if in token not have nothing
                    _userState.value = UserState.Error("User isn't logged in!")
                } else { // recovery user with token
                    client.auth.retrieveUser(token) // receive token the user
                    client.auth.refreshCurrentSession() // update session the user
                    saveToken(context)
                    _userState.value = UserState.Success("User is already logged in!")
                }
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }
}