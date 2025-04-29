package com.example.teentoon

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val em = findViewById<EditText>(R.id.email)
        val usn = findViewById<EditText>(R.id.usn)
        val pass = findViewById<EditText>(R.id.pass)

        auth = FirebaseAuth.getInstance()

        val btnNextt = findViewById<Button>(R.id.btnNextt)
        val txtPndh = findViewById<TextView>(R.id.txtpndh)

        txtPndh.setTextColor(Color.CYAN)
        btnNextt.setOnClickListener {
            val email = em.text.toString().trim()
            val username = usn.text.toString().trim()
            val password = pass.text.toString().trim()

            val isAllFilled = !em.text.isNullOrEmpty() &&
                    !usn.text.isNullOrEmpty() &&
                    !pass.text.isNullOrEmpty()

            val emailPattern = Patterns.EMAIL_ADDRESS
            if (!emailPattern.matcher(email).matches()) {
                Toast.makeText(this, "Format email tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Akun berhasil dibuat!", Toast.LENGTH_SHORT).show()

                        val uid = auth.currentUser?.uid
                        val user = mapOf("username" to username, "email" to email)

                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(uid ?: "")
                            .setValue(user)

                        val intent = Intent(this, Home::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Registrasi gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
        txtPndh.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
        }
    }
}