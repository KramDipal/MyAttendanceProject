package eu.tutorials.myattendanceapp

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/*
fun requestStoragePermission(context: Context, activity: Activity)
{
    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
    }

}
*/

fun saveImageToGallery(bitmap: Bitmap, context: Context) {
    val filename = "QRCODE${System.currentTimeMillis()}.jpg"
    //val filename = "Saved_QRCODE_Image.jpg"

    Log.i("saveImageToGallery","$filename")

    val fos: OutputStream?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        Log.i("saveImageToGallery","$imageUri")

        fos = imageUri?.let { resolver.openOutputStream(it) }
    } else {
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        Log.i("saveImageToGallery","$imagesDir")

        val image = File(imagesDir, filename)

        Log.i("saveImageToGallery","$image")


        fos = FileOutputStream(image)
    }
    fos?.use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()
    }
}

/*
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.OutputStream
import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.io.FileOutputStream


/*
fun saveImageToGallery(bitmap: Bitmap, context: Context) {
    val filename = "image_${System.currentTimeMillis()}.png"
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename)
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }
}
*/


/*
@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
//fun requestStoragePermission(context: Context, bitmap: Bitmap) {
fun requestStoragePermission(context: Context) {
    val writePermissionState = rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)

    //val readPermissionState = rememberPermissionState(permission = Manifest.permission.READ_MEDIA_IMAGES)

    if (writePermissionState.status.isGranted) {
    //if (readPermissionState.status.isGranted) {
        //saveImageToGallery(bitmap, context)
        Toast.makeText(context, "Permission Granted!!!", Toast.LENGTH_SHORT).show()
    } else {
        writePermissionState.launchPermissionRequest()
        //readPermissionState.launchPermissionRequest()
    }

}



fun saveBitmapToGallery(context: Context, bitmap: Bitmap, title: String) {


    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "$title.jpg")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

    }

    Log.i("saveBitmapToGallery:contentValues", "$contentValues")

    val url = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    Log.i("saveBitmapToGallery:uri", "$url")

    url?.let {
        Log.i("saveBitmapToGallery1.0", "Try!!!")
        val outputStream: OutputStream? = context.contentResolver.openOutputStream(it)
        //outputStream.use { stream ->
         //   bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream!!)}
        try {
            Log.i("saveBitmapToGallery1", "Try!!!")
            val outputStream: OutputStream? = context.contentResolver.openOutputStream(it)
            outputStream?.use { stream ->
                //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream!!)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Log.i("saveBitmapToGallery2", "Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q")
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(url, contentValues, null, null)
            }
        } catch (e: Exception) {
            Log.i("saveBitmapToGallery1", "$e")
            e.printStackTrace()
        }
    }


    Toast.makeText(context, "Image Saved in Phone Gallery", Toast.LENGTH_SHORT).show()
}
*/