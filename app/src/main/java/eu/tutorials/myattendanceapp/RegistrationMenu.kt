package eu.tutorials.myattendanceapp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun registrationMenu(navController: NavController, authViewModel: AuthViewModel){

    //> First Name (Text Field)
    //> Last Name (Text Field)
    //> Designation (Text Field)
    //> Employee ID (Text Field)
    //> Generate Button (save to gallery on clicked)

    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent//Color(0xffb5dbc7)
    ) //customized color
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /*Image(
                painter = painterResource(id = R.drawable.mbt), contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(width = 900.dp, height = 300.dp)
            )*/


            Text(text = "Enter Credentials", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))


            //OutlinedTextField(value = fname, onValueChange = {fname = it}, label = {
            OutlinedTextField(value = authViewModel.fname, onValueChange = {
                authViewModel.onTextChanged(it)
            }, label = {
                Text(text = "First Name")
            })

            OutlinedTextField(value = authViewModel.lname, onValueChange = {
                authViewModel.onTextChanged2(it)
            }, label = {
                Text(text = "Last Name")
            })

            OutlinedTextField(value = authViewModel.designation, onValueChange = {
                authViewModel.onTextChanged3(it)
            }, label = {
                Text(text = "Designation")
            })

            OutlinedTextField(value = authViewModel.empid, onValueChange = {
                authViewModel.onTextChanged4(it)
            }, label = {
                Text(text = "Employee ID")
            })


            //Log.i("Kiko/registrationMenu", "${authViewModel.fname} ${authViewModel.lname} ${authViewModel.designation} ${authViewModel.empid}")
            Log.d(
                "Kiko/registrationMenu",
                "${authViewModel.fname} ${authViewModel.lname} ${authViewModel.designation} ${authViewModel.empid}"
            )


            Spacer(modifier = Modifier.height(6.dp))

            Button(
                onClick = {

                    if (authViewModel.fname.isNotBlank() &&
                        authViewModel.lname.isNotBlank() &&
                        authViewModel.designation.isNotBlank() &&
                        authViewModel.empid.isNotBlank()
                    ) {
                        Log.i("Kiko/registrationMenu", "Going to displaygeneratedqrcode form")
                        navController.navigate(Routes.displaygeneratedqrcode)

                    } else {
                        Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_LONG).show()
                    }

                },
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 300.dp, top = 20.dp, end = 300.dp)
            )//.fillMaxWidth().padding(all = 5.dp)
            {
                Text(text = "Generate QRcode")
            }



            Spacer(modifier = Modifier.height(4.dp))

            //Button(onClick = { navController.navigate(Routes.loginmenu) },
            Button(
                onClick = { navController.navigate(Routes.loginpagemenu) },
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 300.dp, top = 20.dp, end = 300.dp)
            )
            {
                Text(text = "Back to Main Menu")
            }

            Button(
                onClick =
                {
                    authViewModel.empid = ""
                    authViewModel.fname = ""
                    authViewModel.lname = ""
                    authViewModel.designation = ""
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 300.dp, top = 20.dp, end = 300.dp)
            )
            {
                Text(text = "Clear All")
            }


        }
    }

}
