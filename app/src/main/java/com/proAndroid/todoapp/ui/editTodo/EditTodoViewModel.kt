package com.proAndroid.todoapp.ui.editTodo

import androidx.lifecycle.*
import com.proAndroid.todoapp.service.RemoteTodoService
import com.proAndroid.todoapp.service.UserService
import com.proAndroid.todoapp.ui.models.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditTodoViewModel(private val todoService: RemoteTodoService) : ViewModel() {

    private val _todoData = MutableLiveData<Todo>()

    fun getTodoById(todoId: Int): LiveData<Todo> {
        viewModelScope.launch(Dispatchers.Main) {
            val todo = todoService.getTodoById(todoId)
            _todoData.postValue(todo)
        }
        return _todoData
    }

    fun updateTodo(todoToUpdate: Todo) {
        todoService.updateTodo(todoToUpdate)
    }

}


class EditTodoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTodoViewModel::class.java)) {
            return EditTodoViewModel(
                RemoteTodoService.getInstance() // it is creating a new class, meanining new data!!
            ) as T
        }
        throw RuntimeException("${modelClass.canonicalName} is not assignable from " +
                "${EditTodoViewModel::class.java.canonicalName}")
    }

}
