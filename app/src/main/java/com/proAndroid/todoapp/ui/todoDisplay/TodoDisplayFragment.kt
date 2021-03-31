package com.proAndroid.todoapp.ui.todoDisplay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.androidService.TodoService
import com.proAndroid.todoapp.asTodoApplication
import com.proAndroid.todoapp.ui.editTodo.EditTodoFragment
import com.proAndroid.todoapp.ui.models.Todo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class TodoDisplayFragment : Fragment() {
    private val todoViewModel by activityViewModels<TodoViewModel> {
        requireActivity().application.asTodoApplication().appComponent.todoViewModelFactory()
    }

    // todoViewModel defined in the fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Surface {
                        TodoDisplayList(
                            todoViewModel = todoViewModel,
                            //**** Lets define some action here.
                            onTodoCardClick = {
                                findNavController().navigate(
                                    R.id.action_todoDisplayFragment_to_editTodoFragment,
                                    EditTodoFragment.bundalize(todo.id)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
    
    companion object {
        @JvmStatic
        fun newInstance() =
            TodoDisplayFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}