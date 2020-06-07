package com.proAndroid.todoapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbTodo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}