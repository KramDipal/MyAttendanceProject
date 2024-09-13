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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun ScannedAlert(
    code: String,
    onDismiss: () -> Unit,
    todoViewModel: TodoViewModel,
    navController: NavController
){
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }
    
    val parts = code.split(" ")
    val partOne = parts[0]      // fname
    val partTwo = parts[1]      //lname
    val partThree = parts[2]    //designation
    val partFour = parts[3]     //empid

    //Initialize permission
    RequestLocationPermission()
    // to get return string from FetchLocation()
    val location = FetchLocation()
    val latloc = location.split(" ")
    val latlocOne = latloc[0]
    val longLocOne = latloc[1]

    //get current date/time
    val currentDateTime = LocalDateTime.now().toString()
    //val dDate = currentDateTime.substring(0,10)
    //val dTime = currentDateTime.substring(11)
    val year = LocalDateTime.now().year
    val month = LocalDateTime.now().monthValue
    val day = LocalDateTime.now().dayOfMonth
    //val hour = LocalDateTime.now().hour
    //val minute = LocalDateTime.now().minute
    //val second = LocalDateTime.now().second

    val dateX = LocalDate.of(year, month, day)
    val timeX = LocalTime.now()

    val formatTime = DateTimeFormatter.ofPattern("HH:mm:ss")
    val formattedTime = timeX.format(formatTime)
    Log.i("DATES/address-formattedTime", formattedTime)

    // Define the formatter to include the day of the week
    val formatDate = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy", Locale.ENGLISH)
    val formattedDate = dateX.format(formatDate)
    Log.i("DATES/address-formattedDate", formattedDate)

    //09/13/24
    val todoLoggedInUserQuery by todoViewModel.todoLoggedInUserQuery.observeAsState()
    Log.i("ScannedAlert/todoLoggedInUserQuery", "${todoLoggedInUserQuery}")
    //09/13/24


    //get the exact address without the lat and long
    var address: String? = null
    val startidx = 23
    val endidx = 71

    if (startidx >= 0 && endidx <= location.length) {
        address = location.substring(startidx, endidx)
    }
    //Log.i("Address/address-kiko", "$address")
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
    Log.i("ScannedAlert/fEmail", "${fEmail}")
    //For Email


    Log.i("ScannedAlert/location", "${latlocOne} ${longLocOne}")
    Log.i("ScannedAlert:Code", "${partOne} ${partTwo} ${partThree} ${partFour}")


    AlertDialog(
        onDismissRequest = {
            //openDialog.value = false
            //navController.navigate(Routes.mainmenu)
        },
        title = {
            Text(text = "QR Code Scanned")
        },
        text = {
            Column {
                SelectionContainer{
                    //Text(text = code)
                    Text(text = "Select Notification")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {


                    //09/13/24
                    // Check if todoLoggedInUserQuery isNullOrEmpty
                    //then add new record to table
                    if(todoLoggedInUserQuery.isNullOrEmpty()){
                        Log.i("todoLoggedInUserQuery","EMPTY!")
                        todoViewModel.addLoginUser(
                            partOne,
                            partTwo,
                            partThree,
                            partFour,
                            latlocOne,
                            longLocOne,
                            formattedDate,
                            formattedTime
                            //currentDateTime.toString()
                        )

                    }
                    else{
                        //update userID record if not empty
                        Log.i("todoLoggedInUserQuery","NOT EMPTY! >> $partFour $formattedTime $formattedDate")
                        todoViewModel.updateLoggedInUser(partFour, formattedTime, formattedDate)
                    }


                    try
                    {
                        if(fEmail){

                            sendEmail(
                                context,
                                //"francis.lapid@yahoo.com, lapidmaya8@gmail.com, genevievegarcialapid@yahoo.com",
                                "francis.lapid@yahoo.com",
                                "Daily Time logger",
                                "Greetings, \n\n" +
                                        //"MBT Attendance Alert employee ${partOne} has logged-in at ${currentDateTime} to a device located at ${location}." +
                                        "MBT Attendance Alert employee $partOne has logged-in at $formattedDate $formattedTime to a device located at ${location}." +
                                        "\n\nThis is an AI generated email PLEASE DO NOT REPLY to this message." +
                                        "\n\nAdmin")


                        }
                    }
                    catch (e: Exception) {
                        Toast.makeText(context, "Email failed to send. Please try again.", Toast.LENGTH_SHORT).show()
                    }

                    //back to main menu
                    navController.navigate(Routes.mainmenu)

                }) {
                Text("Send as Email")
            }
        },
        dismissButton = {
            Button(onClick = {

                //09/13/24
                // Check if todoLoggedInUserQuery isNullOrEmpty
                if(todoLoggedInUserQuery.isNullOrEmpty()){
                    Log.i("todoLoggedInUserQuery","EMPTY!")
                    todoViewModel.addLoginUser(
                        partOne,
                        partTwo,
                        partThree,
                        partFour,
                        latlocOne,
                        longLocOne,
                        formattedDate,
                        formattedTime
                        //currentDateTime.toString()
                    )

                }
                else{
                    //update userID record if not empty
                    Log.i("todoLoggedInUserQuery","NOT EMPTY! >> $partFour $formattedTime $formattedDate")
                    todoViewModel.updateLoggedInUser(partFour, formattedTime, formattedDate)
                }



                try{
                    sendSMSPermissionLauncher.launch(Manifest.permission.SEND_SMS)
                    val smsManager: SmsManager = SmsManager.getDefault()

                    //smsManager.sendTextMessage("+639175736952", null, "MBT Attendance Alert employee ${partOne} has logged-in at ${currentDateTime}", null, null)
                    smsManager.sendTextMessage("+639175736952", null, "MBT Attendance Alert employee $partOne has logged-in at $formattedDate $formattedTime", null, null)
                    smsManager.sendTextMessage("+639175736952", null, "to a device located at ${location}.  PLEASE DO NOT REPLY to this message.", null, null)
/*
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
*/
                    Toast.makeText(context, "SMS sent successfully!", Toast.LENGTH_SHORT).show()
                }
                catch(e: Exception){
                    Toast.makeText(context, "SMS failed to send. Please try again.", Toast.LENGTH_SHORT).show()
                }

                //back to main menu
                navController.navigate(Routes.mainmenu)
            })
            {
                //Text("Cancel")
                Text("Via SMS")
            }
        }
    )


}