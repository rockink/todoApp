package com.proAndroid.todoapp.ui.todoDisplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.service.RemoteTodoService
import com.proAndroid.todoapp.service.User
import com.proAndroid.todoapp.service.UserService
import kotlinx.coroutines.*

val todo = Todo(
    title = "ProgrammingTodo",
    todoListItem = "Expand our todo App",
    imageResource = R.drawable.programming_image,
    imageResourceOnline = RemoteTodoService.getTodoImages()
)


class TodoViewModel(private val todoService: RemoteTodoService, private val userService: UserService) : ViewModel() {

    private val _todoToDisplayList = mutableListOf<Todo>(todo)
    private val _todoLiveData = MutableLiveData<List<Todo>>()
        .also {
        it.postValue(_todoToDisplayList.toList())
    }
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
        // constructor
        val userListAsyc = backgroundScope.async {
            val userList = userService.getAllUsers()
                ?.map { Pair(it.id, it) }
            val userMap = hashMapOf<Int, User>()
            userMap.putAll(userList ?: emptyList())
            return@async userMap
        }

        // they run in parallel
        val remoteTodoArrayAsync = backgroundScope.async { todoService.getAllTodoList() }

        val userMap = userListAsyc.await()
        val remoteTodoArray = remoteTodoArrayAsync.await()

        val todoListTodDisplay = remoteTodoArray
            ?.take(10)
            ?.map {
                Todo(
                    "${it.completed} description",
                    "${it.title} by ${userMap[it.userId]?.name ?: ""}",
                    R.drawable.programming_image,
                    RemoteTodoService.getTodoImages()
                )
            }
        _todoToDisplayList.addAll(todoListTodDisplay ?: emptyList())
        _todoLiveData.postValue(_todoToDisplayList)
    }

    override fun onCleared() {
        super.onCleared()
        // free up background resource
        backgroundScope.cancel()
    }

    fun addItem(todo: Todo) {
        _todoToDisplayList.add(todo)
        _todoLiveData.postValue(_todoToDisplayList.toList()) // make a copy of our list
    }

}


class TodoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(
                RemoteTodoService(),
                UserService()
            ) as T
        }
        throw RuntimeException("${modelClass.canonicalName} is not assignable from " +
                "${TodoViewModel::class.java.canonicalName}")
    }

}
