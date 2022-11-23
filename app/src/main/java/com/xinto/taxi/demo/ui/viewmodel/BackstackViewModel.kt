package com.xinto.taxi.demo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BackstackViewModel(id: Int) : ViewModel() {

    var hashedId by mutableStateOf("")
        private set

    init {
        hashedId = (id * (1..100).random()).toString()
    }

}