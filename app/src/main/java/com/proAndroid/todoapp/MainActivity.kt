package com.proAndroid.todoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.proAndroid.todoapp.notification.AppNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.canonicalName, "onCreate called...")
        setContentView(R.layout.main_layout)

        //could be created by notification as well!
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
}

