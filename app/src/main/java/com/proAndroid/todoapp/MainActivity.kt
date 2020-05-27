package com.proAndroid.todoapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class TodoViewModel: ViewModel() {
    private val todo =  Todo(
        title = "ProgrammingTodo",
        todoListItem = "Expand our todo App",
        imageResource = R.drawable.programming_image
    )

    private val _todoToDisplayList = mutableListOf<Todo>(todo)
    val todoToDisplayList : MutableList<Todo>
        get() = _todoToDisplayList.toMutableList()


    fun addItem(todo: Todo) {
        _todoToDisplayList.add(todo)
    }

}

class MainActivity : AppCompatActivity() {

    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateUi(todoViewModel.todoToDisplayList)
    }

    private fun updateUi(todoList: MutableList<Todo>) {
        val todoRecyclerAdapter = TodoListDisplayAdapter(todoList)
        todoRecyclerView.adapter = todoRecyclerAdapter
        todoRecyclerView.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {
            todoViewModel.addItem(
                todoList.first().copy(title = todoList.first().title + System.currentTimeMillis())
            )
            todoRecyclerAdapter.updateListWithItem(todoViewModel.todoToDisplayList)
          }
    }
}
