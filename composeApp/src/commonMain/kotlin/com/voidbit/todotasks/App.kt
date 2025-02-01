package com.voidbit.todotasks

import androidx.compose.runtime.*
import com.voidbit.todotasks.screens.ToDoListScreen
import com.voidbit.todotasks.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    ToDoListScreen()
}
