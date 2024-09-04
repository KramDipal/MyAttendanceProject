package eu.tutorials.myattendanceapp

import android.content.Context
import android.content.SharedPreferences




// to save username in preference.
// use:
/*  if application was close, button access limitation will be visible only for kiko1234 user name*/
// Used in: LoginPage and MainMenu
class MyAppPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    /*
    // Store a String
    editor.putString("key_string", "value_string")

    // Store an Int
    editor.putInt("key_int", 123)

    // Store a Boolean
    editor.putBoolean("key_boolean", true)

    // Store a Float
    editor.putFloat("key_float", 1.23f)

    // Store a Long
    editor.putLong("key_long", 123456789L)
    */


    fun saveParameterValue(value: String) {
        val editor = prefs.edit()
        editor.putString("parameter_key", value)
        editor.apply()
    }

    fun getParameterValue(): String? {
        return prefs.getString("parameter_key", null)
    }
}