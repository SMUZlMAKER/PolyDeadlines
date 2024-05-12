package com.example.polydeadlines.View

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.polydeadlines.R
import com.example.polydeadlines.ViewModel.DeadlinesViewModel
import com.example.polydeadlines.ui.theme.Green80
import com.example.polydeadlines.ui.theme.Grey40



lateinit var viewModel: DeadlinesViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Auth(onDismis: () -> Unit) {
    var mail by remember { mutableStateOf("") }
    BasicAlertDialog(
        onDismissRequest = onDismis,
    ) {
        Column(Modifier.background(Color.White, MaterialTheme.shapes.medium)) {
            Text(
                text = "Введите логин:",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Color.Black,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
            TextField(
                value = mail,
                onValueChange = { newText -> mail = newText },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            )
            Row(
                modifier = Modifier.padding(5.dp)
            ) {
                Button(
                    modifier = Modifier
                        .weight(1.0F),
                    onClick = onDismis
                ) {
                    Text("Отмена")
                }
                Spacer(modifier = Modifier.width(5.dp))
                Button(
                    modifier = Modifier
                        .weight(1.0F),
                    onClick = onDismis
                ) {
                    Text("Ок")
                }
            }

        }
    }
}

@Composable
fun DropDownMenu() {
    var expanded by remember { mutableStateOf(false) }
    val subjects = viewModel.getSubjects().value
    val icon = if(expanded){
        Icons.Default.KeyboardArrowUp
    }else{
        Icons.Default.KeyboardArrowDown
    }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = icon,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            subjects.forEach { label ->
                DropdownMenuItem(text = { Text(text = label) }, onClick = {
                    viewModel.getFilter().value = label
                    expanded = false
                })
            }
        }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onClick: () -> Unit) {
    val topBatText = if(viewModel.getFilter().value == stringResource(R.string.all)){
        stringResource(R.string.app_name)
    }else{
        viewModel.getFilter().value
    }
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = when {
                isSystemInDarkTheme() -> Grey40
                else -> Color.White
            },
            titleContentColor = when{
                isSystemInDarkTheme() -> Color.White
                else -> Color.Black
            }
        ),
        title = {

            Text(topBatText)
        }, navigationIcon = {
            DropDownMenu()

        },
        actions = {
            IconButton(
                onClick = onClick,
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Log in",
                    tint = Green80
                )
            }
        }
    )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Deadlines(createdViewModel: DeadlinesViewModel = hiltViewModel()) {
    viewModel = createdViewModel
    var openDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TopBar { openDialog = true } },
    ) { innerPadding ->
        DeadLineColumn(modifier = Modifier.padding(innerPadding))
    }

    if (openDialog)
        Auth { openDialog = false }
}


