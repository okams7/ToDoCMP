package com.voidbit.todotasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.voidbit.todotasks.screens.CalendarScreen
import com.voidbit.todotasks.screens.ToDoListScreen
import com.voidbit.todotasks.theme.AppTheme
import com.voidbit.todotasks.theme.LocalThemeIsDark
import com.voidbit.todotasks.viewmodels.ToDosVViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import todotasks.composeapp.generated.resources.Res
import todotasks.composeapp.generated.resources.app_name
import todotasks.composeapp.generated.resources.ic_dark_mode
import todotasks.composeapp.generated.resources.ic_light_mode

val rcorner = 10.dp
val hsize = 32.dp

@Composable
internal fun App() = AppTheme {
    val viewModel = ToDosVViewModel()
    Scaffold(
        topBar = {
            MyTopAppBar(viewModel)
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Your screen content goes here

                SegmentedButtonScreenSwitcher(viewModel.selectedIndex.value)
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(viewModel: ToDosVViewModel) {
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
            ToDoButtonComposable(
//
                selectedIndex = viewModel.selectedIndex.value,
                onSelectionChange = { viewModel.updateSelectedIndex(0) },
            )
            CalendarButtonComposable(
                selectedIndex = viewModel.selectedIndex.value,
                onSelectionChange = { viewModel.updateSelectedIndex(1) },
            )

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
fun SegmentedButton(
    selectedIndex: Int,
    onSelectionChange: (Int) -> Unit,
    options: List<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEachIndexed { index, option ->
            Button(
                onClick = { onSelectionChange(index) },
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedIndex == index) Color.Blue else Color.LightGray
                )
            ) {
                Text(option, color = if (selectedIndex == index) Color.White else Color.Black)
            }
        }
    }
}

@Composable
fun SegmentedButtonScreenSwitcher(selectedIndex: Int) {
    when (selectedIndex) {
        0 -> ToDoListScreen()
        1 -> CalendarScreen()
    }
}

//@Composable
//fun SegmentedButtonPair(
//    onLeftClick: () -> Unit,
//    onRightClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Row(
//        modifier = modifier
////            .fillMaxWidth()
//            .height(hsize),
////            .width(100.dp)
////            .padding(horizontal = 16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        // Left Button
//
//
//        // Divider (Visual Separation)
////        Box(
////            modifier = Modifier
////                .width(1.dp)
//////                .width(48.dp)
////                .fillMaxHeight()
////                .background(Color.Gray)
////        )
//
//
//    }
//}

@Composable
fun ToDoButtonComposable(
    selectedIndex: Int,
    onSelectionChange: (Int) -> Unit,
) {
    FilledIconButton(
        onClick = { onSelectionChange(selectedIndex) },
        modifier = Modifier
            .height(hsize)
            .fillMaxHeight(),
        colors = IconButtonColors(
            containerColor = if (selectedIndex == 0) MaterialTheme.colorScheme.surfaceContainer else MaterialTheme.colorScheme.primaryContainer,
            contentColor = if (selectedIndex == 0) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onTertiary,
        ),
        shape = MaterialTheme.shapes.small.copy(
            topEnd = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp),
            topStart = CornerSize(rcorner),
            bottomStart = CornerSize(rcorner),
        )
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Todo"
        )
    }
}

@Composable
fun CalendarButtonComposable(
    selectedIndex: Int,
    onSelectionChange: (Int) -> Unit,
) {
    // Right Button
    FilledIconButton(
        onClick = { onSelectionChange(selectedIndex) },
        modifier = Modifier.height(hsize)
//                .weight(1f)
//                .width(wsize)
            .fillMaxHeight(),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.LightGray,
//                contentColor = Color.Black
//            ),
        colors = IconButtonColors(
            containerColor = if (selectedIndex == 1) MaterialTheme.colorScheme.surfaceContainer else MaterialTheme.colorScheme.primaryContainer,
            contentColor = if (selectedIndex == 1) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onTertiary,
        ),
        shape = MaterialTheme.shapes.small.copy(
            topStart = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp),
            topEnd = CornerSize(rcorner),
            bottomEnd = CornerSize(rcorner)
        ),
//        colors =
    ) {
        Icon(
            imageVector = Icons.Default.DateRange, // Search icon
            contentDescription = "Calendar"
        )
    }
}
