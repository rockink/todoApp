package com.proAndroid.todoapp.ui.todoDisplay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.proAndroid.todoapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class TodoDisplayFragment : Fragment() {
    private val todoViewModel by viewModels<TodoViewModel> { TodoViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todoRecyclerAdapter =
            TodoListDisplayAdapter(mutableListOf())
        view.todoRecyclerView.adapter = todoRecyclerAdapter
        view.todoRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        todoViewModel.todoLiveData
            .observe(viewLifecycleOwner, Observer { todoList ->
                todoRecyclerAdapter.updateListWithItem(todoList)
            })

        addButton.setOnClickListener {
            todoViewModel.addItem(
                todo.copy(title = todo.title + System.currentTimeMillis())
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TodoDisplayFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}