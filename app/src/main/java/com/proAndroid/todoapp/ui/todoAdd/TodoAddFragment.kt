package com.proAndroid.todoapp.ui.todoAdd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.TodoApplication
import com.proAndroid.todoapp.service.RemoteTodoService
import com.proAndroid.todoapp.ui.models.Todo
import com.proAndroid.todoapp.ui.todoDisplay.TodoViewModel
import com.proAndroid.todoapp.ui.todoDisplay.TodoViewModelFactory
import kotlinx.android.synthetic.main.fragment_todo_add.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoAddFragment : Fragment() {

    private val todoViewModel by activityViewModels<TodoViewModel> { TodoViewModelFactory(
        (requireActivity().application as TodoApplication).db.todoDao()
    ) }

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
                imageResource = R.drawable.programming_image,
                imageResourceOnline = RemoteTodoService.getTodoImages(),
                id = 1
            )
            lifecycleScope.launch(Dispatchers.Default) {// background
                todoViewModel.addTodo(todoToAdd)
                // navigate back to main screen
                findNavController().navigate(R.id.action_todoAddFragment_to_todoDisplayFragment)
            }

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