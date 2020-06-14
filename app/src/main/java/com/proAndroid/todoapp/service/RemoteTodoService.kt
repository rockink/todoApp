package com.proAndroid.todoapp.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.db.TodoDao
import com.proAndroid.todoapp.ui.models.Todo
import com.proAndroid.todoapp.ui.todoDisplay.todo
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request

@JsonClass(generateAdapter = true)
data class JsonPlaceHolderTodo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)

/**
 * Format of json looks something like this:
 * [
    {
    "userId": 1,
    "id": 1,
    "title": "delectus aut autem",
    "completed": false
    }
    ]
 */
class RemoteTodoService @Inject constructor(
    private val userService: UserService,
    private val todoDao: TodoDao,
    private val okHttpClient: OkHttpClient
    ) {

    private val _todoDisplayListLiveData = Transformations.map(todoDao.getAllTodoLiveData()) {
        it.map { it.mapToTodo() }
    }

    private val okHttpClient = OkHttpClient()
    private val url = "https://jsonplaceholder.typicode.com/todos"

    private val moshi = Moshi.Builder().build()
    private val jsonListAdapter = moshi.adapter<Array<JsonPlaceHolderTodo>>(arrayOf<JsonPlaceHolderTodo>()::class.java)

    fun getAllTodoList(): LiveData<List<Todo>> {
        if (todoDao.todoCount() <= 5) {
            todoDao.insert(todo.mapToDbTodo().copy(id = null)) //because insert should be null
            val remoteTodos = getRemoteTodos()
            val mappedLocalTodo = remoteTodos
                ?.map {
                    Todo(
                        title = "${it.completed} description",
                        todoListItem =  "${it.title} by ${userService.getUser(it.userId)?.name ?: ""}",
                        imageResource = R.drawable.programming_image,
                        imageResourceOnline = getTodoImages(),
                        id = it.id
                    )
                }?.map { it.mapToDbTodo().copy(id = null) }
            todoDao.insertAll(mappedLocalTodo?: emptyList())
        }
        return _todoDisplayListLiveData
    }

    private fun getRemoteTodos(): List<JsonPlaceHolderTodo>? {
        val request = Request.Builder()
            .url(url)
            .build()
        val response = okHttpClient.newCall(request).execute()
        val responseString = response.body?.string() // this is in json
        Log.d(this::class.java.canonicalName, "JSON : $responseString")
        if (responseString == null) return null
        // convert json string to json
        return jsonListAdapter.fromJson(responseString)
            ?.take(5)
    }

    /**
     * When you add [todo], the id will be generated. Therefore, even if you pass any id,
     * that id will be overridden by the remoteTodoService
     */
    fun addTodo(todo: Todo) {
        todoDao.insert(todo.mapToDbTodo().copy(id = null)) //now automatically liveDAta is updated
    }

    fun getTodoById(todoId: Int): LiveData<Todo> {
        return Transformations.map(todoDao.getTodoById(todoId)){
            it.mapToTodo()
        }
    }

    fun updateTodo(todoToUpdate: Todo) {
        todoDao.update(todoToUpdate.mapToDbTodo())
    }

    fun getAllTodoListLiveData(): LiveData<List<Todo>> {
        return _todoDisplayListLiveData
    }

    companion object {
        fun getTodoImages(): List<String> {
            val urlTemplate = "https://picsum.photos/id/%d/200/300" // d as in integer, decimal
            val ids = listOf(1001, 1002, 1003, 1004)

            return ids
                .shuffled()
                .take(2)
                .map { urlTemplate.format(it) }
        }

        private var INSTANCE : RemoteTodoService? = null

        fun getInstance(todoDao: TodoDao) : RemoteTodoService {
            if (INSTANCE == null)
                INSTANCE = RemoteTodoService(UserService(), todoDao)
            return INSTANCE!!
        }

    }
}
