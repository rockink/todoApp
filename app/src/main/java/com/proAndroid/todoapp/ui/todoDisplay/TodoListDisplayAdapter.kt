package com.proAndroid.todoapp.ui.todoDisplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.ui.editTodo.EditTodoFragment
import com.proAndroid.todoapp.ui.models.Todo
import com.proAndroid.todoapp.ui.todoPhotoSelection.TodoPhotoSelectFragment
import kotlinx.android.synthetic.main.todo_layout.view.*

class TodoListDisplayAdapter(
    private val todoList: MutableList<Todo>,
    private val glide: RequestManager,
    private val navController: NavController
) :
    RecyclerView.Adapter<TodoListDisplayAdapter.ViewHolder>() {
    class ViewHolder(mView: View, private val glide: RequestManager, private val navController: NavController) : RecyclerView.ViewHolder(mView) {
        fun updateUi(todo: Todo) {
            itemView.todoTitle.text = todo.title
            itemView.todoListItem.text = todo.todoListItem

            glide
                .load(todo.imageResource)
                .into(itemView.todoImage)

            val todoTitleOriginalText = itemView.todoTitle.text

            itemView.todoTitle.setOnClickListener {
                navController.navigate(
                    R.id.action_todoDisplayFragment_to_editTodoFragment,
                    EditTodoFragment.bundalize(todo.id)
                )
            }

            itemView.todoImage.setOnClickListener {
                itemView.todoTitle.text = todoTitleOriginalText
            }

            itemView.changeButton.setOnClickListener {
                navController.navigate(
                    R.id.action_todoDisplayFragment_to_todoPhotoSelectFragment,
                    TodoPhotoSelectFragment.bundalize(todo.id)
                )
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
        return ViewHolder(view, glide, navController)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateUi(todoList[position])
    }

}