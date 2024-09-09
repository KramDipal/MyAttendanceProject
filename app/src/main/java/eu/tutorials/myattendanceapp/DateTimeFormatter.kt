package eu.tutorials.myattendanceapp

import android.util.Log
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

//Get time difference
fun timeFormatterX(startTimeX: String, endTimeX: String): String {

    Log.i("timeFormatterX","$startTimeX $endTimeX")

    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    val startTime = LocalTime.parse(startTimeX, formatter)
    val endTime = LocalTime.parse(endTimeX, formatter)

    val duration = Duration.between(startTime, endTime)

    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}