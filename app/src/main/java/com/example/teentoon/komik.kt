package com.example.teentoon

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity




class komik : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_komik)

        val txtjudul = findViewById<TextView>(R.id.txtjudul)
        val txtauthor = findViewById<TextView>(R.id.txtauthor)
        val txtsinopsis = findViewById<TextView>(R.id.txtsinopsis)
        val txtchapters = findViewById<TextView>(R.id.txtchapters)


    }

}