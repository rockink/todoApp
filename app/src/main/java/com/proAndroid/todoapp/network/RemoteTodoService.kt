package com.proAndroid.todoapp.network

import android.util.Log
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
class RemoteTodoService {
    private val okHttpClient = OkHttpClient()
    private val url = "https://jsonplaceholder.typicode.com/todos"

    private val moshi = Moshi.Builder().build()
    private val jsonListAdapter = moshi.adapter<Array<JsonPlaceHolderTodo>>(arrayOf<JsonPlaceHolderTodo>()::class.java)

    fun getAllTodoList(): Array<JsonPlaceHolderTodo>? {
        val request = Request.Builder()
            .url(url)
            .build()
        val response = okHttpClient.newCall(request).execute()
        val responseString = response.body?.string() // this is in json
        Log.d(this::class.java.canonicalName, "JSON : $responseString")
        if (responseString == null) return null
        // convert json string to json
        return jsonListAdapter.fromJson(responseString)
    }
}