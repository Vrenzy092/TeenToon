package com.example.teentoon

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class SignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val inputUsername = findViewById<EditText>(R.id.Usn)
        val password = findViewById<EditText>(R.id.Pass)
        val btnNext = findViewById<Button>(R.id.btnNext)

        btnNext.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }


        val buttonDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 10f * resources.displayMetrics.density
            setColor(Color.GRAY)
        }

        btnNext.background = buttonDrawable

        /*val isAllFilled = InputUsername.text!!.isNotEmpty() && Password.text!!.isNotEmpty()

        if (isAllFilled) {
                btnNext.isEnabled = true
                buttonDrawable.setColor(Color.parseColor("000080"))
        } else {
                btnNext.isEnabled = false
                buttonDrawable.setColor(Color.GRAY)
        }*/
    }
}