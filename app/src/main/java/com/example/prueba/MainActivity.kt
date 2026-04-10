package com.example.prueba

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.prueba.ui.theme.PruebaTheme

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.prueba.data.AppDatabase
import com.example.prueba.repository.TaskRepository
import com.example.prueba.viewmodel.TaskViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.prueba.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicialización manual
        val database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "task-db").build()
        val repository = TaskRepository(database.taskDao())
        val factory = TaskViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            PruebaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: TaskViewModel, modifier: Modifier = Modifier) {
    val tasks by viewModel.tasks.collectAsState()
    
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Tareas: ${tasks.size}")
        Button(onClick = { viewModel.addTask("Nueva tarea") }) {
            Text("Añadir tarea")
        }
    }
}
