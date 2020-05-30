package com.proAndroid.todoapp

import android.os.Bundle
import androidx.activity.viewModels
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.proAndroid.todoapp.network.RemoteTodoService
import com.proAndroid.todoapp.network.User
import com.proAndroid.todoapp.network.UserService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.concurrent.thread

private val todo = Todo(
    title = "ProgrammingTodo",
    todoListItem = "Expand our todo App",
    imageResource = R.drawable.programming_image
)

class TodoViewModel : ViewModel() {

    private val todoService = RemoteTodoService()
    private val userService = UserService()

    private val _todoToDisplayList = mutableListOf<Todo>(todo)
    private val _todoLiveData = MutableLiveData<List<Todo>>().also {
        it.postValue(_todoToDisplayList.toList())
    }
    val todoLiveData: LiveData<List<Todo>> = _todoLiveData

    private val backgroundScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    init {
        backgroundScope.launch { //this is a Good practice! This is for demonstration
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
                    "${it.title} by ${userMap[it.userId]?.name?:""}",
                    R.drawable.programming_image
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

class MainActivity : AppCompatActivity() {

    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.canonicalName, "onCreate called...")
        setContentView(R.layout.activity_main)
        updateUi()
    }

    private fun updateUi() {
        val todoRecyclerAdapter = TodoListDisplayAdapter(mutableListOf())
        todoRecyclerView.adapter = todoRecyclerAdapter
        todoRecyclerView.layoutManager = LinearLayoutManager(this)

        todoViewModel.todoLiveData
            .observe(this, Observer { todoList ->
                todoRecyclerAdapter.updateListWithItem(todoList)
            })

        addButton.setOnClickListener {
            todoViewModel.addItem(
                todo.copy(title = todo.title + System.currentTimeMillis())
            )
        }
    }
}

