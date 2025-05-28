package com.example.teentoon

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.teentoon.databinding.ActivityKomikBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teentoon.Chapters


class komik : AppCompatActivity() {

    private lateinit var binding: ActivityKomikBinding
    private lateinit var db: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var chapterAdapter: ChapterAdapter
    private val chapterList = mutableListOf<Pair<String, Chapters>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKomikBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        db = database.getReference("comic")

        chapterAdapter = ChapterAdapter(chapterList) { url ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.recyclerviewChapter.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewChapter.adapter = chapterAdapter

        getComicsFromFirebase()

    }

    fun getComicsFromFirebase() {
        val comicId = intent.getStringExtra("comic_id")

        comicId?.let {
            db.child(it).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val cover = snapshot.child("Coverimg").getValue(String::class.java) ?: ""
                    val title = snapshot.child("Title").getValue(String::class.java) ?: "Unknown title"
                    val genre = snapshot.child("Genre").getValue(String::class.java) ?: "Unknown genre"
                    val author = snapshot.child("Author").getValue(String::class.java) ?: "Unknown author"
                    val sinopsis = snapshot.child("Sinopsis").getValue(String::class.java) ?: "Unknown sinopsis"

                    binding.txtjudul.text = title
                    binding.txtgenre.text = genre
                    binding.txtauthor.text = author
                    binding.txtsinopsis.text = sinopsis

                    if (cover.isNotEmpty()) {
                        Glide.with(this@komik)
                            .load(cover)
                            .into(binding.imageCover)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })


            db.child(it).child("Chapter").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    chapterList.clear()
                    for (chapterSnapshot in snapshot.children) {
                        val chapterId = chapterSnapshot.key ?: continue
                        val chapters = chapterSnapshot.getValue(Chapters::class.java) ?: continue
                        chapterList.add(Pair(chapterId, chapters))
                    }
                    chapterList.sortBy { it.first } // urutkan berdasarkan nomor chapter
                    chapterAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}