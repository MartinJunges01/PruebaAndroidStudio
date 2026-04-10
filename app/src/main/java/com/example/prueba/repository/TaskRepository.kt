package com.example.prueba.repository

import com.example.prueba.data.Task
import com.example.prueba.data.TaskDao
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    suspend fun addTask(task: Task) = taskDao.insert(task)
}
