package com.example.teentoon.nav.ref

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReflectionViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Reflection Fragment"
    }
    val text: LiveData<String> = _text
}