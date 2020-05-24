package com.proAndroid.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todoTitleOriginalText = todoTitle.text

        todoTitle.setOnClickListener {
            appTitle.text = "TodoTitle clicked!!"
        }

        todoImage.setOnClickListener {
            appTitle.text = todoTitleOriginalText
        }
    }
}
