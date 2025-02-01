package com.voidbit.todotasks.data

import kotlinx.datetime.LocalDate

data class ToDo(
    val id: Int,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val isImportant: Boolean? = false,
    val dueDate: LocalDate? = null
)

val dummyData = listOf(
    ToDo(1,"Cocking"),
    ToDo(2,"Shopping", description = "this is a test description"),
    ToDo(3,"Fishing", description = "what a delight!!"),
    ToDo(4,"Sleeping")
)
