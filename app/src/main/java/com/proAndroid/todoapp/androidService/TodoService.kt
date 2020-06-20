package com.proAndroid.todoapp.androidService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationManagerCompat
import com.proAndroid.todoapp.asTodoApplication
import com.proAndroid.todoapp.notification.AppNotification
import kotlinx.coroutines.*

class TodoService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val notification = AppNotification(
            NotificationManagerCompat.from(this),
            this
        )
        serviceScope.launch {
            while (isActive) {
                val todos = application.asTodoApplication()
                    .appComponent
                    .todoService()
                    .getAllTodos()

                withContext(Dispatchers.Main) {
                    //notify user
                    notification.notifyTodosSelected(todos)
                }
                delay(1000 * 60) // a minute
            }
        }
    }

}
