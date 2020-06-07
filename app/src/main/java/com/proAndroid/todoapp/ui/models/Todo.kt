package com.proAndroid.todoapp.ui.models

import androidx.annotation.DrawableRes
import com.proAndroid.todoapp.db.DbTodo

data class Todo(
    val title: String,
    val todoListItem: String,
    @DrawableRes val imageResource : Int,
    val imageResourceOnline : List<String>,
    val id: Int
) {
    fun mapToDbTodo(): DbTodo {
        return DbTodo(
            title, todoListItem, imageResource, id
        )
    }
}