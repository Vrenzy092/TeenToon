package com.example.teentoon

data class Message(
    val senderId: String = "",
    val senderName: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val photoUrl: String = ""
)
