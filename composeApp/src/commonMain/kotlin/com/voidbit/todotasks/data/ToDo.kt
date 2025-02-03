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
    ToDo(2,"Shopping", description = "this is a test description dsf sdfsdf fsdf sdfsf sfsdf sdfsf sdfsdff sfsdf ffs fsdfsdf sfsfsfs dsfsf sfsff sdfsfsdf "),
    ToDo(3,"Fishing", description = "what a delight!!", isCompleted = true),
    ToDo(4,"Sleeping")
)
