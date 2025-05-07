package com.example.teentoon

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class komik : AppCompatActivity() {

    private lateinit var db: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var txtjudul: TextView
    private lateinit var txtauthor: TextView
    private lateinit var txtsinopsis: TextView
    private lateinit var txtgenre: TextView
    private lateinit var txtchapters: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_komik)

        database = FirebaseDatabase.getInstance()
        db = database.getReference("comic")

        txtjudul = findViewById(R.id.txtjudul)
        txtauthor = findViewById(R.id.txtauthor)
        txtsinopsis = findViewById(R.id.txtsinopsis)
        txtgenre = findViewById(R.id.txtgenre)
        txtchapters = findViewById(R.id.txtchapters)

        getComicsFromFirebase()

        val btnchap = findViewById<ImageButton>(R.id.imageButton2)

        btnchap.setOnClickListener {
            val url = "https://drive.google.com/file/d/1LwloMQpzAAZ4BxsRmI92sOxbHKrLJjTD/view?pli=1" // Ganti dengan URL komik kamu
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

    }

    fun getComicsFromFirebase() {
        val comicId = intent.getStringExtra("comic_id")

        comicId?.let {
            db.child(it).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val title = snapshot.child("Title").getValue(String::class.java) ?: "Unknown title"
                        txtjudul.text = title
                    val genre = snapshot.child("Genre").getValue(String::class.java) ?: "Unknown genre"
                        txtgenre.text = genre
                    val author = snapshot.child("Author").getValue(String::class.java) ?: "Unknown author"
                        txtauthor.text = author
                    val sinopsis = snapshot.child("Sinopsis").getValue(String::class.java) ?: "Unknown sinopsis"
                        txtsinopsis.text = sinopsis
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}