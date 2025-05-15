package com.example.teentoon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Welcome : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)

        btnSignIn.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}