package com.proAndroid.todoapp.ui.todoDisplay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.androidService.TodoService
import com.proAndroid.todoapp.asTodoApplication
import com.proAndroid.todoapp.notification.AppNotification
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class TodoDisplayFragment : Fragment() {
    private val todoViewModel by activityViewModels<TodoViewModel> {
        requireActivity().application.asTodoApplication().appComponent.todoViewModelFactory()
    }


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
            TodoListDisplayAdapter(mutableListOf(), Glide.with(this), findNavController())
        view.todoRecyclerView.adapter = todoRecyclerAdapter
        view.todoRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        todoViewModel.todoLiveData
            .observe(viewLifecycleOwner, Observer { todoList ->
                todoRecyclerAdapter.updateListWithItem(todoList)

                view.notifyButton.setOnClickListener {
                    val serviceBinder =
                        requireActivity().application.asTodoApplication().serviceBinder

                    serviceBinder.observe(
                        viewLifecycleOwner,
                        object : Observer<TodoService.TodoServiceBinder> {
                            override fun onChanged(t: TodoService.TodoServiceBinder?) {
                                t?.sendNotification()
                                serviceBinder.removeObserver(this)
                            }

                        })
                }
            })

        addButton.setOnClickListener {
            // go to the todoAddFragment
            findNavController()
                .navigate(R.id.action_todoDisplayFragment_to_todoAddFragment)
        }

        addTodoButton.setOnClickListener {
            findNavController()
                .navigate(R.id.action_todoDisplayFragment_to_todoAddFragment)
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