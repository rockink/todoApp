package com.proAndroid.todoapp.ui.todoAdd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.ui.todoDisplay.Todo
import com.proAndroid.todoapp.ui.todoDisplay.TodoViewModel
import com.proAndroid.todoapp.ui.todoDisplay.TodoViewModelFactory
import kotlinx.android.synthetic.main.fragment_todo_add.view.*

class TodoAddFragment : Fragment() {

    private val todoViewModel by activityViewModels<TodoViewModel> { TodoViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.saveButton.setOnClickListener {
            val title = view.totoTitleEditText.text.toString()
            val todoItem = view.addTodoItemEditText.text.toString()
            val todoToAdd = Todo(
                title = title,
                todoListItem = todoItem,
                imageResource = R.drawable.programming_image
            )
            todoViewModel.addItem(todoToAdd)

            // navigate back to main screen
            findNavController().navigate(R.id.action_todoAddFragment_to_todoDisplayFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TodoAddFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}