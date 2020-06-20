package com.proAndroid.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.proAndroid.todoapp.androidService.TodoService


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.canonicalName, "onCreate called...")
        setContentView(R.layout.main_layout)

        startService(
            Intent(this, TodoService::class.java)
        )
    }
}

