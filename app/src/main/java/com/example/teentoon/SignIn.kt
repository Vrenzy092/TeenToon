package com.example.teentoon

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        var inputUsername = findViewById<EditText>(R.id.Usn)
        var password = findViewById<EditText>(R.id.Pass)
        val btnNext = findViewById<Button>(R.id.btnNext)


        val buttonDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 10f * resources.displayMetrics.density
            setColor(Color.GRAY)
        }


        btnNext.setOnClickListener {
            val usn = inputUsername.text.toString()
            val pass = password.text.toString()

            if (usn.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Username dan Password harus diisi!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (usn == "user" && pass == "password123") {
                    Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()


                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}