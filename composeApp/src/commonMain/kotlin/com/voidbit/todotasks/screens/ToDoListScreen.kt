package com.voidbit.todotasks.screens

import MyDatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.voidbit.todotasks.theme.LocalThemeIsDark
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import todotasks.composeapp.generated.resources.Res
import todotasks.composeapp.generated.resources.app_name
import todotasks.composeapp.generated.resources.ic_dark_mode
import todotasks.composeapp.generated.resources.ic_light_mode

@Composable
fun ToDoListScreen() {
    Scaffold(
        topBar = {
            MyTopAppBar()
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Your screen content goes here
                Box(modifier = Modifier.fillMaxSize()) {
                    Column {
                        AddNewItemBar()
                        ToDoList(dummyData)
                    }
                }
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {
    var isDark by LocalThemeIsDark.current
    val themeIcon = remember(isDark) {
        if (isDark) Res.drawable.ic_light_mode
        else Res.drawable.ic_dark_mode
    }
    TopAppBar(
        title = {
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )
        },
//        navigationIcon = {
//            IconButton(onClick = { println("Menu clicked!") }) {
//                Icon(
//                    imageVector = Icons.Default.Menu, // Menu icon
//                    contentDescription = "Menu"
//                )
//            }
//        },
        actions = {
            IconButton(onClick = { isDark = !isDark }) {
                Icon(
                    imageVector = vectorResource(themeIcon), // Search icon
                    contentDescription = "Search"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
fun AddNewItemBar() {
    var text by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    Column {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
            placeholder = { Text("Enter a new ToDo here..") },
            maxLines = 7,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { /* Handle "Done" action */ }
            ),
//            shape = RoundedCornerShape(20.dp),
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(
                        onClick = { text = "" } // Clear the text
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear"
                        )
                    }
                }
            }
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (selectedDate != null) {
            TextButton(
                onClick = { showDatePicker = true }
            ) {
                Text(
                    text = "Due Date: ${selectedDate!!}",
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            IconButton(
                onClick = { showDatePicker = true } // Handle click event
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange, // Use an icon from the Material Icons library
                    contentDescription = "Due date", // Accessibility description
                    modifier = Modifier.size(24.dp) // Set the size of the icon
                )
            }
        }

        Button(
            onClick = { /* Handle click */ },
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Add")
        }

        if (showDatePicker) {
            MyDatePicker(
                onDismissRequest = { showDatePicker = false },
                onDateSelected = { date ->
                    selectedDate = date
                    showDatePicker = false
                }
            )
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
            .padding(8.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(4.dp),

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