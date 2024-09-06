package eu.tutorials.myattendanceapp

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
//fun DisplayGeneratedQRcode(navController: NavController, authViewModel: AuthViewModel, fname: String, lname: String, designation: String, empid: String){
fun DisplayGeneratedQRcode(navController: NavController,
                           authViewModel: AuthViewModel,
                           todoViewModel: TodoViewModel){



    Log.i("Kiko/DisplayGeneratedQRcode", "${authViewModel.fname} ${authViewModel.lname} ${authViewModel.designation} ${authViewModel.empid}")

    //val bitmap = generateQRCode("$fname $lname $designation $empid" , 50)
    val bitmap = generateQRCode("${authViewModel.fname} ${authViewModel.lname} ${authViewModel.designation} ${authViewModel.empid}" , 30)

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Generated QR Code", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        //requestStoragePermission(context, activity)


        //Display generated QRCODE
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "QR Code",
            modifier = Modifier
                .size(400.dp)
                .clickable {
                    saveImageToGallery(bitmap, context)
                    //saveBitmapToGallery(context, bitmap, "QRCODE")
                    //saveImageToGallery(bitmap, context)
                    //requestStoragePermission(context, bitmap)
                }
        )

        //Text(text = "fname: ${authViewModel.fname}")
        //Text(text = "lname: ${authViewModel.lname}")
        //Text(text = "designation: ${authViewModel.designation}")
        //Text(text = "empid: ${authViewModel.empid}")


        //Spacer(modifier = Modifier.height(12.dp))


            //save new user here
            Button(onClick = {
                todoViewModel.addTodo(
                    authViewModel.fname,
                    authViewModel.lname,
                    authViewModel.designation,
                    authViewModel.empid)
                    navController.navigate(Routes.mainmenu)},
                modifier = Modifier.fillMaxWidth().padding(start = 400.dp, top = 20.dp, end = 400.dp))

            {
                Text(text = "Register")

            }


            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                navController.navigate(Routes.mainmenu) },
                modifier = Modifier.fillMaxWidth().padding(start = 400.dp, top = 10.dp, end = 400.dp))

            {
                Text(text = "Back")

            }




    }
}

