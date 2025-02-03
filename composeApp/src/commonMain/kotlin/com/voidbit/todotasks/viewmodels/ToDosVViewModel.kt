package com.voidbit.todotasks.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ToDosVViewModel: ViewModel() {
    private var _selectedIndex = mutableStateOf(0)
    val selectedIndex: State<Int> get() = _selectedIndex

    fun updateSelectedIndex(newIndex: Int) {
        _selectedIndex.value = newIndex
    }
}