package com.proAndroid.todoapp

import android.os.Bundle
import androidx.activity.viewModels
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

private val todo =  Todo(
    title = "ProgrammingTodo",
    todoListItem = "Expand our todo App",
    imageResource = R.drawable.programming_image
)

class TodoViewModel: ViewModel() {

    private val _todoToDisplayList = mutableListOf<Todo>(todo)
    private val _todoLiveData = MutableLiveData<List<Todo>>().also {
        it.postValue(_todoToDisplayList.toList())
    }
    val todoLiveData : LiveData<List<Todo>> = _todoLiveData


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

