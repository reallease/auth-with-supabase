package com.example.authwithsupabase.data.network

import io.github.cdimascio.dotenv.Dotenv
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient

// create instancy supabase client, require url and key

val dotenv: Dotenv = Dotenv.configure() // library for files .env
    .directory("user.dir") // get directory where are the files .env
    .filename(".env") // name of files
    .load() // load


object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://djamwvarbdfckisxodel.supabase.co", // reload files how, url and key for operations with supabase
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRqYW13dmFyYmRmY2tpc3hvZGVsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzQyOTY5OTIsImV4cCI6MjA0OTg3Mjk5Mn0.Efj4IUuGC88wTZGjHQyPFmtBQ6OvRiPyHut0n2CfNvc"

    ) {
        install(Auth) // Instala e configura o GoTrue
        // Configurações opcionais, como tempo de expiração do token

    }
}