package com.proAndroid.todoapp.ui.todoDisplay

import androidx.annotation.DrawableRes

data class Todo(
    val title: String,
    val todoListItem: String,
    @DrawableRes val imageResource : Int
)