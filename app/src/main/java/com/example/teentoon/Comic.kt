package com.example.teentoon

data class Comic(
    val Title: String = "",
    val Author: String = "",
    val Genre: String = "",
    val Sinopsis: String = "",
    val Coverimg: String = "",
    val Chapter: Map<String, String>? = null

)
