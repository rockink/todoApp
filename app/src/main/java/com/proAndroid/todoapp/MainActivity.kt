package com.proAndroid.todoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val todo =  Todo(
        title = "ProgrammingTodo",
        todoListItem = "Expand our todo App",
        imageResource = R.drawable.programming_image
    )

    private val todoToDisplayList = mutableListOf<Todo>(todo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.canonicalName, "onCreate called...")
        setContentView(R.layout.activity_main)
        updateUi(todoToDisplayList)
    }

    private fun updateUi(todoList: MutableList<Todo>) {
        val todoRecyclerAdapter = TodoListDisplayAdapter(todoList)
        todoRecyclerView.adapter = todoRecyclerAdapter
        todoRecyclerView.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {
            todoRecyclerAdapter.updateListWithItem(
                todo.copy(
                    title = todo.title + System.currentTimeMillis()
                )
            )
          }
    }
}
