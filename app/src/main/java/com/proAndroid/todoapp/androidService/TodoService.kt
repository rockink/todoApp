package com.proAndroid.todoapp.androidService

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationManagerCompat
import com.proAndroid.todoapp.asTodoApplication
import com.proAndroid.todoapp.notification.AppNotification
import kotlinx.coroutines.*

class TodoService : Service() {

    private lateinit var notification: AppNotification
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val todoServiceBinder = TodoServiceBinder()

    inner class TodoServiceBinder: Binder() {
        fun sendNotification(){
            this@TodoService.sendNotification()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return todoServiceBinder
    }

    override fun onCreate() {
        super.onCreate()
        notification = AppNotification(NotificationManagerCompat.from(this), this)

        serviceScope.launch {
            while (isActive) {
                delay(1000 * 60) // 60 seconds
                sendNotification()
            }
        }
    }

    private fun sendNotification() {
        serviceScope.launch {
                val todos = application.asTodoApplication().appComponent.todoService()
                    .getAllTodos()

                withContext(Dispatchers.Main) {
                    //notify user
                    notification.notifyTodosSelected(todos)
                }
            }
        }
}
