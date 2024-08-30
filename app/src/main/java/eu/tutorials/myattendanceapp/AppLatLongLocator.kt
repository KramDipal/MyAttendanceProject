package eu.tutorials.myattendanceapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission() {
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    if(permissionState.status.isGranted) {
        // Permission granted, proceed with location fetching
        Log.i("RequestLocationPermission", "Permission granted!!")
        //FetchLocation()
    }
    else{
        Log.i("RequestLocationPermission", "Permission denied!!")
    }

    /*if (Manifest.permission.ACCESS_FINE_LOCATION == PackageManager.PERMISSION_GRANTED) {

    } else {
        // Handle permission denied case
    }
    */
}

@Composable
fun FetchLocation(): String {

    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


    var location by remember { mutableStateOf<Location?>(null) }

    Log.i("FetchLocation", "INSIDE already!!!")

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            Log.i("FetchLocation1", "True")
            fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                location = loc

                //Log.i("FetchLocation", "${loc.latitude} ${loc.longitude}")
            }
        }
        else{
            Log.i("FetchLocation2", "False")
        }
    }



    location?.let {
        Log.i("FetchLocation3", "${it.latitude} ${it.longitude}")



        Text(text = "Latitude: ${it.latitude}, Longitude: ${it.longitude}")
    }
    val address = location?.let { GetAddressScreen(location!!.latitude, location!!.longitude) }

    Log.i("FetchLocation4", "${location?.latitude} ${location?.longitude} ${address}")
    return "${location?.latitude} ${location?.longitude} $address"
}


@Composable
fun GetAddressScreen(latitude: Double, longitude: Double): String
{
    val context = LocalContext.current
    var address by remember { mutableStateOf("") }
    val geocoder = Geocoder(context, Locale.getDefault())

    Log.i("Address","${latitude} ${longitude}")

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1) { addresses ->
            if (addresses.isNotEmpty()) {
                address = addresses[0].getAddressLine(0)
            }
        }
    } else {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

        Log.i("Address","$addresses")
        //if (addresses != null) {
            //if (addresses.isNotEmpty()) {
                address = addresses?.get(0)?.getAddressLine(0) ?: "NO Address found"
            //}
        //}
    }

    return "${address}"
}