package com.proAndroid.todoapp

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.proAndroid.todoapp.androidService.TodoService
import com.proAndroid.todoapp.db.TodoDatabase

class TodoApplication : Application() {

    lateinit var appComponent: AppComponent
    lateinit var db: TodoDatabase //because we will access this variable later on

    private var _serviceBinder = MutableLiveData<TodoService.TodoServiceBinder>()
    val  serviceBinder : LiveData<TodoService.TodoServiceBinder> = _serviceBinder

    private var serviceConnected: Boolean = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            serviceConnected = false
            _serviceBinder.postValue(null)
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceConnected = true
            _serviceBinder.postValue(service as TodoService.TodoServiceBinder)
        }
    }


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .bindApplication(this)
            .build()

        bindService(
            Intent(this, TodoService::class.java),
            serviceConnection,
            AppCompatActivity.BIND_AUTO_CREATE
        )

        db = Room.databaseBuilder(this, TodoDatabase::class.java, "todo-db")
            .build()
    }

}

fun Application.asTodoApplication(): TodoApplication {
    return this as TodoApplication
}