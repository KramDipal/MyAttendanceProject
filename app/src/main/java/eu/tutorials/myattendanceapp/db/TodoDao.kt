package eu.tutorials.myattendanceapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import eu.tutorials.myattendanceapp.LoginUser
import eu.tutorials.myattendanceapp.Todo


@Dao //DAO annotation
interface TodoDao {

    @Query("SELECT * FROM TODO ORDER BY createdAt DESC")
    fun getAllTodo() : LiveData<List<Todo>>

    //@Query("SELECT empid from TODO where empid = :empid")
    //fun selectTodoAny(empid : String)

    @Query("Select * from TODO where empid = :empid")
    fun selectTodoAny(empid : String):LiveData<List<Todo>>

    @Insert
    fun addTodo(todo : Todo)

    @Query("DELETE FROM TODO where id = :id")
    fun deleteTodo(id : Int)


    //Insert to LoginUser table
    @Insert
    fun addLoginUser(todo : LoginUser)

}