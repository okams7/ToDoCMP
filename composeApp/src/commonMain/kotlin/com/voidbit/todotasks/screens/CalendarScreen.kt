package com.voidbit.todotasks.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayAt

@Composable
fun CalendarScreen(){
    MonthViewCalendar()
}

fun daysInMonth(year: Int, month: Month): Int {
    return when (month) {
        Month.FEBRUARY -> if (isLeapYear(year)) 29 else 28
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        else -> 31
    }
}

fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

@Composable
fun MonthViewCalendar() {
    // State for the current date
//    val currentDate = LocalDate.now(TimeZone.currentSystemDefault())
    var currentDate by remember { mutableStateOf(Clock.System.todayAt(TimeZone.currentSystemDefault())) }
    println(currentDate)

    // State for the selected date
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    selectedDate = currentDate
    // State for the custom date input
    var customDateInput by remember { mutableStateOf("") }

    var showAddTask by remember { mutableStateOf(false) }

    // Function to navigate to the previous month
    fun goToPreviousMonth() {
        currentDate = currentDate.minus(1, DateTimeUnit.MONTH)
    }

    // Function to navigate to the next month
    fun goToNextMonth() {
        currentDate = currentDate.plus(1, DateTimeUnit.MONTH)
    }

    // Function to handle custom date selection
    fun handleCustomDateSelection() {
        try {
            val parsedDate = LocalDate.parse(customDateInput)
            selectedDate = parsedDate
            currentDate = parsedDate
        } catch (e: Exception) {
            println("Invalid date format")
        }
    }

    Row {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Display the current month and year
            Text(
                text = "${currentDate.year}-${currentDate.month}",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Calendar grid for the month
            val daysInMonth = daysInMonth(currentDate.year, currentDate.month)
            val firstDayOfMonth = LocalDate(currentDate.year, currentDate.month, 1)
            val firstDayOfWeek = firstDayOfMonth.dayOfWeek
            println("firstDayOfWeek: $firstDayOfWeek")

            // Weekday names – Multiplatform compatible
            val dayOfWeekNames = remember {
                listOf(
                    "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
                )
            }

            // Navigation buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = ::goToPreviousMonth) {
                    Text("Previous Month")
                }
                Button(onClick = { currentDate = Clock.System.todayAt(TimeZone.currentSystemDefault()) }) {
                    Text("Today")
                }
                Button(onClick = ::goToNextMonth) {
                    Text("Next Month")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Create a grid for the calendar
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.fillMaxWidth()
            ) {

                // Display day of the week headers
                items(7) { index ->
                    Text(
                        text = dayOfWeekNames[index],
                        modifier = Modifier
                            .size(40.dp)
//                        .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Add empty cells for days before the first day of the month
                items((firstDayOfWeek.ordinal + 1) % 7) {
                    Box(modifier = Modifier.size(40.dp))
                }

                // Add cells for each day in the month
                items(daysInMonth) { day ->
                    val date = LocalDate(currentDate.year, currentDate.month, day + 1)
                    val isSelected = date == selectedDate

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                selectedDate = date
                                showAddTask = true
                            }
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surface
                            )
                            .then(
                                if (date == Clock.System.todayAt(TimeZone.currentSystemDefault())) { // Replace `yourCondition` with your actual condition
                                    Modifier.border(BorderStroke(1.dp, Color.Cyan))
                                } else {
                                    Modifier
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (day + 1).toString(),
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))



            // Custom date input
            OutlinedTextField(
                value = customDateInput,
                onValueChange = { customDateInput = it },
                label = { Text("Enter a custom date (YYYY-MM-DD)") },
                singleLine = true
            )

            Button(onClick = ::handleCustomDateSelection) {
                Text("Go to Custom Date")
            }
        }
        if(showAddTask)
            Box (modifier = Modifier.weight(1f)){
                IconButton(
                    onClick = { showAddTask = false } // Clear the text
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear"
                    )
                }
            }
    }


}