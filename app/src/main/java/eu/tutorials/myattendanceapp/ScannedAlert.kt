package eu.tutorials.myattendanceapp


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.telephony.SmsManager
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import java.time.LocalDateTime


@Composable
fun ScannedAlert(
    code: String,
    onDismiss: () -> Unit,
    todoViewModel: TodoViewModel,
    navController: NavController
){
    val context = LocalContext.current

    val parts = code.split(" ")
    val partOne = parts[0]
    val partTwo = parts[1]
    val partThree = parts[2]
    val partFour = parts[3]


    //Initialize permission
    RequestLocationPermission()
    // to get return string from FetchLocation()
    val location = FetchLocation()
    val latloc = location.split(" ")
    val latlocOne = latloc[0]
    val longLocOne = latloc[1]

    //get current date/time
    val currentDateTime = LocalDateTime.now()

    //get the exact address without the lat and long
    var address: String? = null
    val startidx = 23
    val endidx = 71

    if (startidx >= 0 && endidx <= location.length) {
        address = location.substring(startidx, endidx)
    }
    Log.i("Address/address-kiko", "${address}")
    Log.i("Address/currentDateTime-kiko", "${currentDateTime}")
    //get the exact address without the lat and long


    //for SMS
    val sendSMSPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ){ isGranted: Boolean ->
        if (isGranted) {
            //Toast.makeText(context, "SMS Permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "SMS Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    //For Email
    val fEmail = isEmailClientInstalled(context)
    /*
    val bitmap = getBitmapFromDrawable(context, R.drawable.goodday2)
    val file = saveBitmapToFile(context, bitmap, "image.png")

    if(!fEmail){
        Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
    }*/
    Log.i("ScannedAlert/fEmail", "${fEmail}")
    //For Email




    Log.i("ScannedAlert/location", "${latlocOne} ${longLocOne}")
    Log.i("ScannedAlert:Code", "${partOne} ${partTwo} ${partThree} ${partFour}")




    AlertDialog(
        onDismissRequest = {
            //openDialog.value = false
        },
        title = {
            Text(text = "QR Code Scanned")
        },
        text = {
            Column {
                SelectionContainer {
                    Text(text = code)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    //Log.i("ScannedAlert:onClick", "${partOne} ${partTwo} ${partThree} ${partFour}")

                    todoViewModel.addLoginUser(
                        partOne,
                        partTwo,
                        partThree,
                        partFour,
                        latlocOne,
                        longLocOne
                    )

                    try
                    {
                        if(fEmail){

                            sendEmail(
                                context,
                                "francis.lapid@yahoo.com, lapidmaya8@gmail.com, genevievegarcialapid@yahoo.com",
                                "Daily Time logger",
                                "Greetings, \n\n" +
                                        "MBT Attendance Alert employee ${partOne} has logged-in at ${currentDateTime} to a device located at ${location}." +
                                        "\n\nThis is an AI generated email PLEASE DO NOT REPLY to this message." +
                                        "\n\nAdmin")


                        }
                    }
                    catch (e: Exception) {
                        Toast.makeText(context, "Email failed to send. Please try again.", Toast.LENGTH_SHORT).show()
                    }

                    // Send advise through SMS
                    /*
                    try {

                        sendSMSPermissionLauncher.launch(Manifest.permission.SEND_SMS)
                        val smsManager: SmsManager = SmsManager.getDefault()

                        smsManager.sendTextMessage("+639175736952", null, "MBT Attendance Alert employee ${partOne} has logged-in at ${currentDateTime}", null, null)
                        smsManager.sendTextMessage("+639175736952", null, "to a device located at ${location}.  PLEASE DO NOT REPLY to this message.", null, null)


                        //Honey phone number
                        smsManager.sendTextMessage("+639175940314", null, "MBT Attendance Alert employee ${partOne} has logged-in at ${currentDateTime}", null, null)
                        smsManager.sendTextMessage("+639175940314", null, "to a device located at ${location}", null, null)


                        //Mike phone number
                        smsManager.sendTextMessage("+639125988789", null, "MBT Attendance Alert employee ${partOne} has logged-in at ${currentDateTime}", null, null)
                        smsManager.sendTextMessage("+639125988789", null, "to a device located at ${location}", null, null)


                        //Isang phone number
                        smsManager.sendTextMessage("+639611202551", null, "MBT Attendance Alert employee ${partOne} has logged-in at ${currentDateTime}", null, null)
                        smsManager.sendTextMessage("+639611202551", null, "to a device located at ${location}", null, null)


                        //MBT tablet phone no.
                        smsManager.sendTextMessage("+639369303009", null, "MBT Attendance Alert employee ${partOne} has logged-in at ${currentDateTime}", null, null)
                        smsManager.sendTextMessage("+639369303009", null, "to a device located at ${location}", null, null)



                        Toast.makeText(context, "SMS sent successfully!", Toast.LENGTH_SHORT).show()

                    } catch (e: Exception) {
                        Toast.makeText(context, "SMS failed to send. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                    */

                }) {
                Text("Send as Email")
            }
        },
        dismissButton = {
            Button(
                //onClick = onDismiss,
                //openDialog.value = false
                  onClick = {navController.navigate(Routes.mainmenu)})
            {
                Text("Cancel")
            }
        }
    )


}