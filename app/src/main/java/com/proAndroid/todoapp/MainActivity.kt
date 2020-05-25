package com.proAndroid.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.activity_main.*

data class Todo(
    val title: String,
    val todoListItem: String,
    @DrawableRes val imageResource : Int
)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateUi(
            Todo(
                title = "ProgrammingTodo",
                todoListItem = "Expand our todo App",
                imageResource = R.drawable.programming_image
            )
        )

    }

    private fun updateUi(todo: Todo) {
        todoTitle.text = todo.title
        todoListItem.text = todo.todoListItem
        todoImage.setImageResource(todo.imageResource)

        val todoTitleOriginalText = todoTitle.text

        todoTitle.setOnClickListener {
            appTitle.text = "TodoTitle clicked!!"
        }

        todoImage.setOnClickListener {
            appTitle.text = todoTitleOriginalText
        }
    }
}
