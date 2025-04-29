package com.example.teentoon

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val em = findViewById<EditText>(R.id.Email)
        val pass = findViewById<EditText>(R.id.Pass)
        val btnNext = findViewById<Button>(R.id.btnNext)

        auth = FirebaseAuth.getInstance()

        val buttonDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 10f * resources.displayMetrics.density
            setColor(Color.GRAY)
        }

        btnNext.background = buttonDrawable

        btnNext.setOnClickListener {
            val email = em.text.toString()
            val password = pass.text.toString()

            val emailPattern = Patterns.EMAIL_ADDRESS
            if (!emailPattern.matcher(email).matches()) {
                Toast.makeText(this, "Format email tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Sign In berhasil", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, Home::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Sign In gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }


        }
    }
}