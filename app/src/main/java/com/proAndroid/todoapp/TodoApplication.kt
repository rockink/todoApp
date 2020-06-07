package com.proAndroid.todoapp

import android.app.Application
import androidx.room.Room
import com.proAndroid.todoapp.db.TodoDatabase

class TodoApplication : Application() {

    lateinit var db: TodoDatabase //because we will access this variable later on

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, TodoDatabase::class.java, "todo-db")
            .build()
    }

}