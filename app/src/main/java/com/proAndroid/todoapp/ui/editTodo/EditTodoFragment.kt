package com.proAndroid.todoapp.ui.editTodo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.proAndroid.todoapp.EditTodoComponent
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.asTodoApplication
import kotlinx.android.synthetic.main.fragment_todo_add.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditTodoFragment : Fragment() {

    companion object {
        val ID = "ID"
        fun bundalize(id: Int) = Bundle().apply {
            putInt(ID, id)
        }

        fun deBundalize(bundle: Bundle) = bundle.getInt(ID)
    }

    private lateinit var editTodoComponent: EditTodoComponent
    private val editViewModel by viewModels<EditTodoViewModel> {
        editTodoComponent.editTodoViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        editTodoComponent = requireActivity().application.asTodoApplication()
            .appComponent
            .editTodoComponentBuilder()
            .bindTodoId(deBundalize(requireArguments()))
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editViewModel.getTodo()
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

                    lifecycleScope.launch(Dispatchers.Default) {
                        editViewModel.updateTodo(todoToUpdate)
                        findNavController().navigate(R.id.action_editTodoFragment_to_todoDisplayFragment)
                    }
                }

            })

    }

}