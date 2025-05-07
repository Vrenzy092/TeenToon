package com.example.teentoon

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btn1 = findViewById<ImageButton>(R.id.btn1)

        btn1.setOnClickListener {
            Detailkomik("Comic1")
        }
    }

    private fun Detailkomik(comicId: String){
        val intent = Intent(this, komik::class.java)
        intent.putExtra("comic_id", comicId)
        startActivity(intent)
        finish()
    }
}