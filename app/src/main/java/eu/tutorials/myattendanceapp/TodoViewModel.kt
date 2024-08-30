package eu.tutorials.myattendanceapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class TodoViewModel :ViewModel() {

    //Access DB
    val todoDao = AttendanceApp.todoDatabase.getTodoDao()

    fun addTodo(fname: String, lname: String, designation: String, empid: String){
        Log.i("Kiko/addTodo","${fname}, ${lname}, " +
                "${designation}, ${empid}, ${Date()} ${Date.from(Instant.now())}")

        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addTodo(
                Todo(
                    fname = fname,
                    lname = lname,
                    designation = designation,
                    empid = empid,
                    createdAt = Date().toString()//Date.from(Instant.now())
                )
            )
        }
    }



    fun selectAllTodo(): LiveData<List<Todo>> = todoDao.getAllTodo()//wishDao.getAllWishes()

    fun selectTodoAny(empid : String){
        Log.i("Kiko/selectTodoAny", empid)
        todoDao.selectTodoAny(empid)
    }

    fun deleteTodo(id : Int){
        todoDao.deleteTodo(id)
    }



    //for logged in Users
    fun addLoginUser(
        fname: String, lname: String,
        designation: String, empid: String,
        lati: String, longi: String
    ){


       Log.i("Kiko/addLoginUser","${fname}, ${lname}, " +
                "${designation}, ${empid}, ${Date()}")
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addLoginUser(
                LoginUser(
                    fname = fname,
                    lname = lname,
                    designation = designation,
                    empid = empid,
                    latitude = lati,
                    longitude = longi,
                    createdAt = Date().toString()
                    ))
        }
    }


}