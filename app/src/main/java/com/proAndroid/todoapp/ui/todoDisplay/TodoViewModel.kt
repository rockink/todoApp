package com.proAndroid.todoapp.ui.todoDisplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.service.RemoteTodoService
import com.proAndroid.todoapp.service.UserService
import com.proAndroid.todoapp.ui.models.Todo
import kotlinx.coroutines.*

val todo = Todo(
    title = "ProgrammingTodo",
    todoListItem = "Expand our todo App",
    imageResource = R.drawable.programming_image,
    imageResourceOnline = RemoteTodoService.getTodoImages(),
    id = 1
)


class TodoViewModel(private val todoService: RemoteTodoService) : ViewModel() {

    private val _todoLiveData = MutableLiveData<List<Todo>>()
    val todoLiveData: LiveData<List<Todo>> = _todoLiveData

    private val backgroundScope =
        CoroutineScope(Dispatchers.Default + SupervisorJob())

    init {
        backgroundScope.launch { //this is a Good practice! This is for demonstration
            // no calls for a while, so we have less amount of todos for test
            updateDataFromRemoteCalls()
        }
    }

    // can only run from coroutines
    private suspend fun updateDataFromRemoteCalls() {
        _todoLiveData.postValue(todoService.getAllTodoList())
    }

    override fun onCleared() {
        super.onCleared()
        // free up background resource
        backgroundScope.cancel()
    }

    fun addTodo(todo: Todo) {
        todoService.addTodo(todo)
        _todoLiveData.postValue(todoService.getAllTodoList())
    }

}


class TodoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(
                RemoteTodoService(UserService())
            ) as T
        }
        throw RuntimeException("${modelClass.canonicalName} is not assignable from " +
                "${TodoViewModel::class.java.canonicalName}")
    }

}
