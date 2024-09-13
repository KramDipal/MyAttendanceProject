package eu.tutorials.myattendanceapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class TodoViewModel :ViewModel() {


    // Define the formatter to include the day of the week
    //09/13/24
    val year = LocalDateTime.now().year
    val month = LocalDateTime.now().monthValue
    val day = LocalDateTime.now().dayOfMonth
    val dateX = LocalDate.of(year, month, day)
    val formatDate = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy", Locale.ENGLISH)
    val formattedDate = dateX.format(formatDate)
    //09/13/24

    //Access DB
    val todoDao = AttendanceApp.todoDatabase.getTodoDao()

    val todoList : LiveData<List<Todo>> = todoDao.getAllTodo()
    val todoListAll : LiveData<List<LoginUser>> = todoDao.getAllTodoLogUser()
    val todoListAny : LiveData<List<LoginUser>> = todoDao.selectTodoAnyLoginUser("000005")

    //09/13/24
    val todoLoggedInUserQuery : LiveData<List<LoginUser>> = todoDao.selectLoggedInUser("000005", formattedDate)
    //09/13/24

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




    fun selectALlTodo(){
        todoDao.getAllTodo()
        //Log.i("selectALlTodo: TodoX","selectALlTodo $TodoX")
    }

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
        lati: String, longi: String, dDate: String, dTime: String
    ){


       Log.i("Kiko/addLoginUser","${fname}, ${lname}, " +
                "${designation}, ${empid}")
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addLoginUser(
                LoginUser(
                    fname = fname,
                    lname = lname,
                    designation = designation,
                    empid = empid,
                    latitude = lati,
                    longitude = longi,
                    createdAt = dDate,//Date().toString()
                    createdTime = dTime,
                    createdTimeOut = "hh:mm:ss"
                    //createdAt = dDate
                    ))
        }
    }

    fun deleteTodoLog(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodoLog(id)
        }
    }

    fun selectTodoAnyLoginUser(empid : String){
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.selectTodoAnyLoginUser(empid)
        }
    }

    fun updateLoginUser(empid: String, createdAt: String, createdTimeOut: String){
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.updateLoginUserId(empid, createdAt, createdTimeOut)
        }
    }


    //09/13/24
    fun selectLoggedInUser(empID: String, createdAt: String) {

        Log.i("selectLoggedInUser","$empID $createdAt")
        viewModelScope.launch(Dispatchers.IO) {
          todoDao.selectLoggedInUser(empID, createdAt)
        }
    }

    fun updateLoggedInUser(empid: String, createdTime: String, createdAt: String){

        viewModelScope.launch(Dispatchers.IO) {
            todoDao.updateLoggedInUser(empid, createdTime, createdAt)
        }
    }
    //09/13/24
    //for logged in Users



}