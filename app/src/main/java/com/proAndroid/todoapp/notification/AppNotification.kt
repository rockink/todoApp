package com.proAndroid.todoapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.proAndroid.todoapp.MainActivity
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.notification.AppNotification.Actions.Delete.DELETE_REQUEST_CODE
import com.proAndroid.todoapp.notification.AppNotification.Actions.NOTIFICATION_ID
import com.proAndroid.todoapp.ui.models.Todo

/**
 * Handles all sorts of notification for the application
 */
class AppNotification(private val notificationManager: NotificationManagerCompat, private val context: Context) {

    // constants
    object Actions {
        val NOTIFICATION_ID = 1
        val ACTION_TYPE_KEY = "ACTION_TYPE_KEY"

        object Delete {
            val DELETE_REQUEST_CODE = 2
            val ACTION_TYPE = "DELETE_KEY"
        }
    }

    val CHANNEL_ID = "TodoDisplayChannel"

    companion object {
        val NOTIFICATION_PRESSED_REQUEST_CODE = 1
    }

    fun notifyTodosSelected(todoList: List<Todo>) {
        createNotificationChannel()
        val notificatioNBuilder = NotificationCompat.Builder(context, CHANNEL_ID)

        notificatioNBuilder
            .setSmallIcon(R.drawable.programming_image)
            .setContentTitle("TotalTodos: ${todoList.count()}")
            .setContentText("Click here to see the todos of your app")
            .setContentIntent(PendingIntent.getActivity(context, NOTIFICATION_PRESSED_REQUEST_CODE, Intent(context, MainActivity::class.java), FLAG_CANCEL_CURRENT))
            .addAction(
                R.drawable.delete,
                "Delete First Todo",
                getActivity(
                    context,
                    DELETE_REQUEST_CODE,
                    Intent(context, MainActivity::class.java).apply {
                        putExtra(Actions.ACTION_TYPE_KEY, Actions.Delete.ACTION_TYPE)
                    },
                    FLAG_CANCEL_CURRENT
                )
            )

        notificationManager.notify(
            NOTIFICATION_ID,
            notificatioNBuilder.build()
        )
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val descriptionText = "Simple Todo"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }
    }


}