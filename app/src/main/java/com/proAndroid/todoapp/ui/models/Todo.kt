package com.proAndroid.todoapp.ui.models

import androidx.annotation.DrawableRes

data class Todo(
    val title: String,
    val todoListItem: String,
    @DrawableRes val imageResource : Int,
    val imageResourceOnline : List<String>,
    val id: Int
)