package com.example.authwithsupabase.data.model

sealed class UserState {
    object Loading: UserState() // state of loading
    data class Success(val message: String): UserState() // state of success
    data class Error(val message: String):UserState() // state of error
}