package com.proAndroid.todoapp.service

import android.util.Log
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject


@JsonClass(generateAdapter = true)
data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
)


/**
 * Need to adhere to this json format:
  {"id": 1,
    "name": "Leanne Graham",
    "username": "Bret",
    "email": "Sincere@april.biz"
    }
 */

class UserService @Inject constructor(private val okHttpClient: OkHttpClient) {
    private val url = "https://jsonplaceholder.typicode.com/users"

    private val moshi = Moshi.Builder().build()
    private val jsonListAdapter = moshi.adapter<Array<User>>(arrayOf<User>()::class.java)

    fun getAllUsers(): Array<User>? {
        val request = Request.Builder()
            .url(url)
            .build()
        val response = okHttpClient.newCall(request).execute()
        val responseString = response.body?.string() // this is in json
        Log.d(
            this::class.java.canonicalName,
            "JSON : $responseString"
        )
        if (responseString == null) return null
        // convert json string to json
        return jsonListAdapter.fromJson(responseString)
    }

    fun getUser(userId: Int): User? {
        return getAllUsers()?.first { it.id == userId }
    }
}