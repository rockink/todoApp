package com.proAndroid.todoapp.ui.todoDisplay

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.ui.models.Todo
import kotlinx.android.synthetic.main.todo_layout.view.*

@Composable
fun TodoDisplayList(todoViewModel: TodoViewModel, onTodoCardClick: (Todo) -> Unit){
    // Compose functions are expected to be stateless. Since ViewModel has a state, we create
    // another compose function to take in the state, rather than viewModel.
    val state by todoViewModel.todoLiveData.observeAsState()
    TodoDisplayList(state?: emptyList(), onTodoCardClick = onTodoCardClick)
}

@Composable
fun TodoDisplayList(state: List<Todo>, onTodoCardClick: (Todo) -> Unit) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(state) { todo ->
            TodoDisplayCard(
                title = todo.title,
                description = todo.todoListItem,
                imageUri = todo.imageResource,
                onCardClick =  {onTodoCardClick(todo)},
                onCardDeleteAction = {},
                onCardChangeAction = {}
            )
        }
    }
}

@Preview
@Composable
fun TodoDisplayListPreview(){
    TodoDisplayList(
        listOf(
            Todo(
                title = "Programming Todo",
                todoListItem = "Expand our todo App",
                id = R.drawable.programming_image
            ),
            Todo(
                title = "False Description",
                todoListItem = "Some random Description",
                id = R.drawable.programming_image
            ),
        ),
        onTodoCardClick = {}
    )
}