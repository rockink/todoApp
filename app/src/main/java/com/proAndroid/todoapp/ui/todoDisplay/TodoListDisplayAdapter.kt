package com.proAndroid.todoapp.ui.todoDisplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.proAndroid.todoapp.R
import kotlinx.android.synthetic.main.todo_layout.view.*

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

    fun updateListWithItem(todo: List<Todo>) {
        todoList.clear()
        todoList.addAll(todo)
        notifyDataSetChanged() // as same as saying update everything again!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_layout, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateUi(todoList[position])
    }

}