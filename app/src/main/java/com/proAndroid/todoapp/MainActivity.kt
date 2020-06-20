package com.proAndroid.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.proAndroid.todoapp.androidService.TodoService
import androidx.lifecycle.lifecycleScope
import com.proAndroid.todoapp.notification.AppNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.canonicalName, "onCreate called...")
        setContentView(R.layout.main_layout)

        startService(
            Intent(this, TodoService::class.java)
        )
        //could be created by notification as well!
        handleNotificationAppStarting(intent)
    }

    private fun handleNotificationAppStarting(intent: Intent?) {
        intent?:return
        val notificationActionType = intent.getStringExtra(AppNotification.Actions.ACTION_TYPE_KEY)?:return
        if (notificationActionType == AppNotification.Actions.Delete.ACTION_TYPE) {
            lifecycleScope.launch(Dispatchers.Default) {
                application.asTodoApplication()
                    .appComponent
                    .todoService()
                    .deleteFirstTodo()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent: intent Received")
        handleNotificationAppStarting(intent)
    }
}

