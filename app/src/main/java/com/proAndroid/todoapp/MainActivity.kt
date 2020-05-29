package com.proAndroid.todoapp

import android.os.Bundle
import androidx.activity.viewModels
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.proAndroid.todoapp.network.RemoteTodoService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

private val todo =  Todo(
    title = "ProgrammingTodo",
    todoListItem = "Expand our todo App",
    imageResource = R.drawable.programming_image
)

 class TodoViewModel: ViewModel() {

    private val todoService = RemoteTodoService()

    private val _todoToDisplayList = mutableListOf<Todo>(todo)
    private val _todoLiveData = MutableLiveData<List<Todo>>().also {
        it.postValue(_todoToDisplayList.toList())
    }
    val todoLiveData : LiveData<List<Todo>> = _todoLiveData

    init {
        viewModelScope.launch(Dispatchers.Default) { //this is a Good practice! This is for demonstration
            // constructor
            val remoteTodoArray = todoService.getAllTodoList()
                ?.take(10)
                ?.map {
                    Todo(
                        it.title,
                        "${it.completed} description",
                        R.drawable.programming_image
                    )
                }
            _todoToDisplayList.addAll(remoteTodoArray ?: emptyList())
            _todoLiveData.postValue(_todoToDisplayList)
        }
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
            .observe(this, Observer {todoList ->
                todoRecyclerAdapter.updateListWithItem(todoList)
            })

        addButton.setOnClickListener {
            todoViewModel.addItem(
                todo.copy(title =todo.title + System.currentTimeMillis())
            )
          }
    }
}

