package com.proAndroid.todoapp

import androidx.annotation.DrawableRes

data class Todo(
    val title: String,
    val todoListItem: String,
    @DrawableRes val imageResource : Int
)