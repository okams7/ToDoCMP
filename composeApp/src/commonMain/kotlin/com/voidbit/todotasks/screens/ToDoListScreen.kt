package com.voidbit.todotasks.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.voidbit.todotasks.data.ToDo
import com.voidbit.todotasks.data.dummyData

@Composable
fun ToDoListScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            AddNewItemBar()
            ToDoList(dummyData)
        }
    }
}

@Composable
fun AddNewItemBar(){
    var text by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.weight(1f)
                .padding(16.dp),
            placeholder = { Text("Enter a new ToDo here..") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { /* Handle "Done" action */ }
            ),
            shape = RoundedCornerShape(20.dp)
        )
        Button(
            onClick = { /* Handle click */ },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Add")
        }
    }
}

@Composable
fun ToDoList(itemList: List<ToDo>) {
    LazyColumn {
        items(itemList) { item ->
            ToDoCard(modifier = Modifier.padding(start = 16.dp, end = 16.dp), item)
        }
    }
}

@Composable
fun ToDoCard(
    modifier: Modifier = Modifier,
    toDo: ToDo
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Column {
                Text(
                    text = toDo.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                toDo.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}