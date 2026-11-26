package com.example.counterhistoryapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf

class CounterViewModel : ViewModel() {
    var count by mutableStateOf(0)
        private set

    val history = mutableStateListOf<Int>()

    fun increment() {
        count++
        history.add(count)
    }

    fun decrement() {
        count--
        history.add(count)
    }

    fun reset() {
        count = 0
        history.clear()
    }
}
