package com.proAndroid.todoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.proAndroid.todoapp.ui.todoDisplay.TodoListDisplayAdapter
import com.proAndroid.todoapp.ui.todoDisplay.TodoViewModel
import com.proAndroid.todoapp.ui.todoDisplay.TodoViewModelFactory
import com.proAndroid.todoapp.ui.todoDisplay.todo
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val todoViewModel by viewModels<TodoViewModel> { TodoViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.canonicalName, "onCreate called...")
        setContentView(R.layout.activity_main)
        updateUi()
    }

    private fun updateUi() {
        val todoRecyclerAdapter =
            TodoListDisplayAdapter(mutableListOf())
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

