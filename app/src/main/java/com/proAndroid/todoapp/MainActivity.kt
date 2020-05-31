package com.proAndroid.todoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.proAndroid.todoapp.ui.todoDisplay.TodoDisplayFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.canonicalName, "onCreate called...")
        setContentView(R.layout.main_layout)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, TodoDisplayFragment.newInstance())
            .commit()
    }
}

