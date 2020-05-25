package com.proAndroid.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todo_layout.view.*

data class Todo(
    val title: String,
    val todoListItem: String,
    @DrawableRes val imageResource : Int
)

class MainActivity : AppCompatActivity() {
    private val todoToDisplayList = listOf<Todo>(
        Todo(
            title = "ProgrammingTodo",
            todoListItem = "Expand our todo App",
            imageResource = R.drawable.programming_image
        ),
        Todo(
            title = "Gaming todo",
            todoListItem = "Lets play the game",
            imageResource = R.drawable.programming_image
        ),
        Todo(
            title = "Grocery todo",
            todoListItem = "Lets do the grocery",
            imageResource = R.drawable.programming_image
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateUi(todoToDisplayList)

    }

    private fun updateUi(todoList: List<Todo>) {
        todoRecyclerView.adapter = TodoListDisplayAdapter(todoList)
        todoRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}


class TodoListDisplayAdapter(private val todoList: List<Todo>) :
    RecyclerView.Adapter<TodoListDisplayAdapter.ViewHolder>() {
    class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        fun updateUi(todo: Todo) {
            itemView.todoTitle.text = todo.title
            itemView.todoListItem.text = todo.todoListItem
            itemView.todoImage.setImageResource(todo.imageResource)

            val todoTitleOriginalText = itemView.todoTitle.text

            itemView.todoTitle.setOnClickListener {
                itemView.todoTitle.text = "TodoTitle clicked!!"
            }

            itemView.todoImage.setOnClickListener {
                itemView.todoTitle.text = todoTitleOriginalText
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateUi(todoList[position])
    }

}