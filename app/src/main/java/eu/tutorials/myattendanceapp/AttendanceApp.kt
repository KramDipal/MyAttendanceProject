package eu.tutorials.myattendanceapp

import android.app.Application
import androidx.room.Room
import eu.tutorials.myattendanceapp.db.TodoDatabase

class AttendanceApp : Application() {

    companion object{
        lateinit var todoDatabase: TodoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        todoDatabase = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            TodoDatabase.NAME
        ).build()
    }

}