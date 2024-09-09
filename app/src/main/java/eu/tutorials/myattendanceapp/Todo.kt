package eu.tutorials.myattendanceapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//Declaration of ENTITY annotation
//Table for registration of User's QRCODE
@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var fname: String,
    var lname: String,
    var designation: String,
    var empid: String,
    var createdAt: String
)


//Table for logged in users
@Entity
data class LoginUser(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var fname: String,
    var lname: String,
    var designation: String,
    var empid: String,
    var latitude: String,
    var longitude: String,
    //var datelog: String
    //var timelog: String
    var createdAt: String,
    @ColumnInfo(defaultValue = "hh:mm:ss") var createdTime: String,
    @ColumnInfo(defaultValue = "hh:mm:ss") var createdTimeOut: String
    //This error occurs because Room requires a default value for new NOT NULL columns when adding them to an existing table
)


