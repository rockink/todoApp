package com.proAndroid.todoapp.ui.editTodo

import androidx.lifecycle.*
import com.proAndroid.todoapp.db.TodoDao
import com.proAndroid.todoapp.service.RemoteTodoService
import com.proAndroid.todoapp.service.UserService
import com.proAndroid.todoapp.ui.models.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditTodoViewModel(private val todoService: RemoteTodoService) : ViewModel() {

    fun getTodoById(todoId: Int): LiveData<Todo> {
        return todoService.getTodoById(todoId)
    }

    fun updateTodo(todoToUpdate: Todo) {
        todoService.updateTodo(todoToUpdate)
    }

}


class EditTodoViewModelFactory(val todoDao: TodoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTodoViewModel::class.java)) {
            return EditTodoViewModel(
                RemoteTodoService.getInstance(todoDao) // it is creating a new class, meanining new data!!
            ) as T
        }
        throw RuntimeException("${modelClass.canonicalName} is not assignable from " +
                "${EditTodoViewModel::class.java.canonicalName}")
    }

}
