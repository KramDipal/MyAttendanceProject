package eu.tutorials.myattendanceapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun sendSMS(context: Context, phoneNumber: String, message: String) {
    val sendSMSPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            //Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    try {
        sendSMSPermissionLauncher.launch(Manifest.permission.SEND_SMS)
        val smsManager: SmsManager = SmsManager.getDefault()

        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        Toast.makeText(context, "SMS sent successfully!", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "SMS failed to send. Please try again.", Toast.LENGTH_SHORT).show()
    }
}



@SuppressLint("QueryPermissionsNeeded")
fun isEmailClientInstalled(context: Context): Boolean {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
    }

    //Log.i("intentx", "isEmailClientInstalled ${intent}")

    val packageManager = context.packageManager
    val resolveInfoList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    return resolveInfoList.isNotEmpty()
}

@SuppressLint("QueryPermissionsNeeded")
fun sendEmail(context: Context, email: String, subject: String, body: String) {

    val emailAddresses = email.split(",").toTypedArray()
    //val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
        //putExtra(Intent.EXTRA_STREAM, uri)
        //addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    Log.i("intentx", "sendEmail ${intent}")
    //intent.type = "image/png"
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
    }
}


//Convert Drawable to Bitmap
fun getBitmapFromDrawable(context: Context, drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)
    val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

// Save Bitmap to File
fun saveBitmapToFile(context: Context, bitmap: Bitmap, fileName: String): File {
    val file = File(context.cacheDir, fileName)
    file.outputStream().use {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
    }
    return file
}


