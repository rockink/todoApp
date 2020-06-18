package com.proAndroid.todoapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.proAndroid.todoapp.service.RemoteTodoService
import com.proAndroid.todoapp.ui.models.Todo


@Entity(tableName = "todo")
data class DbTodo(
    val title: String,
    val todoListItem: String,
    val imageResource : String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null //cause want to autogenerate
) {
    fun mapToTodo(): Todo {
        return Todo(
            title=title,
            id = id!!,
            todoListItem = todoListItem,
            imageResource = imageResource
        )
    }
}


@Dao
interface TodoDao {

    @Query("SELECT * FROM TODO")
    fun getAllTodos() : List<DbTodo> //this is not reactive

    @Query("SELECT * FROM TODO")
    fun getAllTodoLiveData() : LiveData<List<DbTodo>> //this is not reactive

    @Insert
    fun insert(dbTodo: DbTodo) //dbTodo id=null

    @Insert
    fun insertAll(list: List<DbTodo>)

    @Update
    fun update(dbTodo: DbTodo) //dbTodo with id=<number>

    @Query("SELECT COUNT(*) FROM TODO")
    fun todoCount() : Int

    @Query("SELECT * FROM TODO where id=:todoId")
    fun getTodoById(todoId: Int): LiveData<DbTodo>

}