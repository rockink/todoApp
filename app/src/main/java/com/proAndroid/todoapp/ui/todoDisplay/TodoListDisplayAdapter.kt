package com.proAndroid.todoapp.ui.todoDisplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.ui.models.Todo
import kotlinx.android.synthetic.main.todo_layout.view.*

class TodoListDisplayAdapter(
    private val todoList: MutableList<Todo>,
    private val glide: RequestManager
) :
    RecyclerView.Adapter<TodoListDisplayAdapter.ViewHolder>() {
    class ViewHolder(mView: View, private val glide: RequestManager) : RecyclerView.ViewHolder(mView) {
        fun updateUi(todo: Todo) {
            itemView.todoTitle.text = todo.title
            itemView.todoListItem.text = todo.todoListItem

            glide
                .load(todo.imageResource)
                .into(itemView.todoImage)

            val imageResources = todo.imageResourceOnline
                .map { glide.load(it) }
                .also {requests ->
                    val requests = requests.map { it.clone().circleCrop() }
                    requests[0].into(itemView.todoImage1)
                    requests[1].into(itemView.todoImage2)
                }

            itemView.todoImage1.setOnClickListener {
                imageResources[0]
                    .centerCrop()
                    .into(itemView.todoImage)
            }

            itemView.todoImage2.setOnClickListener {
                imageResources[1]
                    .centerCrop()
                    .into(itemView.todoImage)
            }

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
        return ViewHolder(view, glide)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateUi(todoList[position])
    }

}