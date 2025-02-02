import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate?) -> Unit
) {
    // State for the DatePicker
    val datePickerState = rememberDatePickerState()

    // Show the DatePickerDialog
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    // Get the selected date in milliseconds
                    val selectedDateMillis = datePickerState.selectedDateMillis
                    if (selectedDateMillis != null) {
                        // Convert milliseconds to LocalDate //ofEpochMilli
//                        val selectedDate = Instant.fromEpochMilliseconds(selectedDateMillis)
//                            .atZone(ZoneId.systemDefault())
//                            .toLocalDate()
                        // Convert milliseconds to LocalDate
                        val selectedDate =
                            Instant.fromEpochMilliseconds(selectedDateMillis).toLocalDateTime(
                                TimeZone.currentSystemDefault()
                            ).date
                        onDateSelected(selectedDate)
                    } else {
                        onDateSelected(null)
                    }
                    onDismissRequest()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDateSelected(null)
                    onDismissRequest()
                }
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}