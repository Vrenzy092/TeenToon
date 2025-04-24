package com.example.teentoon

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


val supabase = createSupabaseClient(
    supabaseUrl = "https://tadgtindnqsyuzkljeoi.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRhZGd0aW5kbnFzeXV6a2xqZW9pIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDUzNzI4MjUsImV4cCI6MjA2MDk0ODgyNX0.1j2CNX1v1id5Oy47Tnl3WdcVhJJfpJDJ9vxWuCjGcK8"
) {
    install(Auth)
    install(Postgrest)
    install(Storage)
}

class komik : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_komik)

        val txtjudul = findViewById<TextView>(R.id.txtjudul)
        val txtauthor = findViewById<TextView>(R.id.txtauthor)
        val txtsinopsis = findViewById<TextView>(R.id.txtsinopsis)
        val txtchapters = findViewById<TextView>(R.id.txtchapters)

        @Serializable
        data class Comic(
            val id: Int,
            val title: String,
            val author: String,
            val sinopsis: String,
            val chapters: String
        )

        suspend fun fetchComics(): List<Comic>{
            return supabase.from("comic").select().decodeList<Comic>()
        }

        lifecycleScope.launch {
            try {
                val comics = fetchComics()

                val firstcomic = comics.firstOrNull()
                if (firstcomic != null) {
                    txtjudul.setText(firstcomic.title)
                    txtauthor.setText(firstcomic.author)
                    txtsinopsis.setText(firstcomic.sinopsis)
                    txtchapters.setText(firstcomic.chapters)
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

}