package com.example.teentoon.nav.com

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommunityViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Community Fragment"
    }
    val text: LiveData<String> = _text
}