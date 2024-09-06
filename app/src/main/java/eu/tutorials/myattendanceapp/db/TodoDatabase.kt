package eu.tutorials.myattendanceapp.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import eu.tutorials.myattendanceapp.LoginUser
import eu.tutorials.myattendanceapp.Todo


//@Database(entities = [Todo1::class, LoginUser::class], version = 1)

// need for adding new column to table
@Database(entities = [Todo::class, LoginUser::class], version = 2, exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)])

@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {

    companion object{
        const val NAME = "Master_Attendance"
    }

    abstract fun getTodoDao() : TodoDao

}


