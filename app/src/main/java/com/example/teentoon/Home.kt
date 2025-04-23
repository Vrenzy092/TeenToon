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
            val intent = Intent(this, komik::class.java)
            startActivity(intent)
            finish()
        }
    }
}