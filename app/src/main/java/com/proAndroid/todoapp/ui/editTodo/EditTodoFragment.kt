package com.proAndroid.todoapp.ui.editTodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.proAndroid.todoapp.R
import kotlinx.android.synthetic.main.fragment_todo_add.view.*

class EditTodoFragment : Fragment() {

    companion object {
        val ID = "ID"
        fun bundalize(id: Int) = Bundle().apply {
            putInt(ID, id)
        }

        fun deBundalize(bundle: Bundle) = bundle.getInt(ID)
    }

    private val editViewModel by viewModels<EditTodoViewModel> { EditTodoViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editViewModel.getTodoById(deBundalize(requireArguments()))
            .observe(viewLifecycleOwner, Observer { todo ->
                view.totoTitleEditText.setText(todo.title)
                view.addTodoItemEditText.setText(todo.todoListItem)

                view.saveButton.setOnClickListener {
                    val title = view.totoTitleEditText.text.toString()
                    val todoItem = view.addTodoItemEditText.text.toString()
                    val todoToUpdate = todo.copy(
                        title = title,
                        todoListItem = todoItem
                    )

                    editViewModel.updateTodo(todoToUpdate)
                    findNavController().navigate(R.id.action_editTodoFragment_to_todoDisplayFragment)
                }

            })

    }

}