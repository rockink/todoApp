package com.proAndroid.todoapp.ui.todoDisplay

import androidx.lifecycle.*
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.db.TodoDao
import com.proAndroid.todoapp.service.RemoteTodoService
import com.proAndroid.todoapp.service.UserService
import com.proAndroid.todoapp.ui.models.Todo
import kotlinx.coroutines.*
import javax.inject.Inject

val todo = Todo(
    title = "ProgrammingTodo",
    todoListItem = "Expand our todo App",
    imageResource = R.drawable.programming_image,
    imageResourceOnline = RemoteTodoService.getTodoImages(),
    id = 1
)


class TodoViewModel(private val todoService: RemoteTodoService) : ViewModel() {

    val todoLiveData = Transformations.map(todoService.getAllTodoListLiveData()) {it}
    private val backgroundScope =
        CoroutineScope(Dispatchers.Default + SupervisorJob())

    init {
        backgroundScope.launch { //this is a Good practice! This is for demonstration
            // no calls for a while, so we have less amount of todos for test
            todoService.getAllTodoList()
        }
    }

    override fun onCleared() {
        super.onCleared()
        // free up background resource
        backgroundScope.cancel()
    }

    fun addTodo(todo: Todo) {
        return todoService.addTodo(todo)
    }

}


class TodoViewModelFactory @Inject constructor(private val remoteTodoService: RemoteTodoService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(remoteTodoService) as T
        }
        throw RuntimeException("${modelClass.canonicalName} is not assignable from " +
                "${TodoViewModel::class.java.canonicalName}")
    }

}
