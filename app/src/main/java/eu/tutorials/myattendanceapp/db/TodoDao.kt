package eu.tutorials.myattendanceapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import eu.tutorials.myattendanceapp.LoginUser
import eu.tutorials.myattendanceapp.Todo


@Dao //DAO - Data Access Annotation
interface TodoDao {

    @Query("SELECT * FROM TODO ORDER BY createdAt DESC")
    fun getAllTodo() : LiveData<List<Todo>>

    @Query("Select * from TODO where empid = :empid")
    fun selectTodoAny(empid : String):LiveData<List<Todo>>

    @Insert
    fun addTodo(todo : Todo)

    @Query("DELETE FROM TODO where id = :id")
    fun deleteTodo(id : Int)


    //LoginUser table
    @Insert
    fun addLoginUser(todo : LoginUser)

    @Query("Select * from LoginUser where empid = :empID ORDER BY createdTime DESC")
    fun selectTodoAnyLoginUser(empID : String):LiveData<List<LoginUser>>

    @Query("SELECT * FROM LoginUser ORDER BY createdAt ASC")
    fun getAllTodoLogUser() : LiveData<List<LoginUser>>

    @Query("DELETE FROM LoginUser where id = :id")
    fun deleteTodoLog(id : Int)
    //LoginUser table



    //Update table where empid = empid and createDate = createDate (current date)
    @Query("UPDATE LoginUser SET createdTimeOut = :createdTimeOut WHERE empId = :empID and createdAt = :createdAt")
    fun updateLoginUserId(empID: String, createdAt: String, createdTimeOut: String)

}