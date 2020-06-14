package com.proAndroid.todoapp

import android.content.Context
import androidx.room.Room
import com.proAndroid.todoapp.db.TodoDao
import com.proAndroid.todoapp.db.TodoDatabase
import com.proAndroid.todoapp.service.RemoteTodoService
import com.proAndroid.todoapp.service.UserService
import com.proAndroid.todoapp.ui.editTodo.EditTodoViewModelFactory
import com.proAndroid.todoapp.ui.todoDisplay.TodoViewModelFactory
import dagger.*
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient()

    @Provides
    @Singleton
    fun provideTodoDatabase(context: Context) : TodoDatabase =
        Room.databaseBuilder(context, TodoDatabase::class.java, "todo-db")
            .build()

    @Provides
    @Singleton
    fun provideTodoDao(todoDatabase: TodoDatabase): TodoDao = todoDatabase.todoDao()
}


@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun todoViewModelFactory() : TodoViewModelFactory
    fun editTodoComponentBuilder(): EditTodoComponent.Builder

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance fun bindApplication(context: Context): Builder
    }
}


@Subcomponent
interface EditTodoComponent {
    fun editTodoViewModelFactory() : EditTodoViewModelFactory

    @Subcomponent.Builder
    interface Builder {
        fun build(): EditTodoComponent
        @BindsInstance fun bindTodoId(todoId: Int) : Builder
    }

}