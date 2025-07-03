package com.example.teentoon.nav.com

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teentoon.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class NewComActivity : AppCompatActivity() {

    private lateinit var etCommunityName: EditText
    private lateinit var btnCreate: Button
    private lateinit var btnAddMember: Button

    private val firestore = FirebaseFirestore.getInstance()
    private val db = FirebaseDatabase.getInstance().reference
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_com)

        etCommunityName = findViewById(R.id.etCommunityName)
        btnCreate = findViewById(R.id.btnCreate)

        btnCreate.setOnClickListener {
            val name = etCommunityName.text.toString().trim()
            if (name.isNotEmpty()) {
                createCommunity(name)
            } else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createCommunity(name: String) {
        val communityId = name.lowercase().replace(" ", "_")
        val data = hashMapOf(
            "name" to name,
            "adminId" to currentUserId,
            "members" to listOf(currentUserId)
        )

        firestore.collection("community").document(communityId)
            .set(data)
            .addOnSuccessListener {
                db.child("Users").child(currentUserId).child("joinedGroups").child(communityId).setValue(true)
                Toast.makeText(this, "Community Created", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to create", Toast.LENGTH_SHORT).show()
            }
    }
}
