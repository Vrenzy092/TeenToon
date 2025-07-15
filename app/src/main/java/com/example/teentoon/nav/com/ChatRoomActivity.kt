package com.example.teentoon.nav.com

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teentoon.Message
import com.example.teentoon.MessageAdapter
import com.example.teentoon.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class ChatRoomActivity : AppCompatActivity() {

    private lateinit var adapter: MessageAdapter
    private val messageList = mutableListOf<Message>()
    private lateinit var communityId: String

    private val db = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val recycler = findViewById<RecyclerView>(R.id.recyclerChat)
        val editText = findViewById<EditText>(R.id.etChat)
        val btnSend = findViewById<Button>(R.id.btnSend)
        val txtCom = findViewById<TextView>(R.id.txtCom)

        communityId = intent.getStringExtra("communityId") ?: return

        adapter = MessageAdapter(messageList)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        listenToMessages(recycler)

        val communityId = intent.getStringExtra("communityId") ?: return

        FirebaseFirestore.getInstance()
            .collection("community")
            .document(communityId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val name = document.getString("name") ?: "Community"
                    txtCom.text = name
                } else {
                    txtCom.text = "Unknown"
                }
            }
            .addOnFailureListener {
                txtCom.text = "Error"
            }


        btnSend.setOnClickListener {
            val text = editText.text.toString().trim()
            if (text.isNotEmpty()) {
                sendMessage(text)
                scrollToBottom(recycler)
                editText.setText("")
            }
        }
    }

    private fun sendMessage(text: String) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
        val userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId)

        userRef.get().addOnSuccessListener { snapshot ->
            val username = snapshot.child("username").getValue(String::class.java) ?: "Anonim"

            val message = Message(
                senderId = currentUserId ?: "",
                senderName = username ?: "Anonim",
                text = text
            )

            db.collection("community")
                .document(communityId)
                .collection("messages")
                .add(message)
        }
    }

    private fun listenToMessages(recycler: RecyclerView) {
        db.collection("community")
            .document(communityId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshots, _ ->
                messageList.clear()
                for (doc in snapshots!!) {
                    val msg = doc.toObject(Message::class.java)
                    messageList.add(msg)
                }
                adapter.notifyDataSetChanged()
                recycler.scrollToPosition(messageList.size - 1)
            }
    }

    private fun scrollToBottom(recycler: RecyclerView) {
         recycler.post{
            recycler.scrollToPosition(adapter.itemCount - 1)
        }
    }
}
