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
    private val todo =  Todo(
        title = "ProgrammingTodo",
        todoListItem = "Expand our todo App",
        imageResource = R.drawable.programming_image
    )

    private val todoToDisplayList = mutableListOf<Todo>(todo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


class TodoListDisplayAdapter(private val todoList: MutableList<Todo>) :
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

    fun updateListWithItem(todo: Todo) {
        todoList.add(todo)
        notifyItemChanged(todoList.size - 1) // last item
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