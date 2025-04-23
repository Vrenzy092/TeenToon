package com.example.teentoon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class komik : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_komik)

        /*private fun getClient(){
            val client = createSupabaseClient {
                supabaseUrl = "https://tadgtindnqsyuzkljeoi.supabase.co",
                supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRhZGd0aW5kbnFzeXV6a2xqZW9pIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDUzNzI4MjUsImV4cCI6MjA2MDk0ODgyNX0.1j2CNX1v1id5Oy47Tnl3WdcVhJJfpJDJ9vxWuCjGcK8"
            } {
                install(Postgrest)

            }
         */

    }
}