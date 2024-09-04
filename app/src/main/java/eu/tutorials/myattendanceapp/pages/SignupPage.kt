package eu.tutorials.myattendanceapp.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import eu.tutorials.myattendanceapp.AuthState
import eu.tutorials.myattendanceapp.AuthViewModel
import eu.tutorials.myattendanceapp.R
import eu.tutorials.myattendanceapp.Routes

@Composable
fun SignupPage(modifier: Modifier = Modifier,
               navController: NavController,
               authViewModel: AuthViewModel) {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")

    }

    val authState = authViewModel.authstate.observeAsState()

    val context = LocalContext.current

    LaunchedEffect(authState.value){
        when(authState.value) {
            is AuthState.Authenticated -> navController.navigate("mainmenu")
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_LONG).show()
            else -> Unit

        }
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent)//(0xffb5dbc7)) //customized color
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Image(
                painter = painterResource(id = R.drawable.mbt), contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(width = 700.dp, height = 300.dp)
            )



            Text(text = "Sign up Page", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(value = email, onValueChange = { email = it }, label = {
                Text(text = "Email Address")
            })

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = password, onValueChange = { password = it }, label = {
                Text(text = "Password")
            }, visualTransformation = PasswordVisualTransformation())

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { authViewModel.signup(email, password) },
                modifier = Modifier.fillMaxWidth().padding(start = 300.dp, top = 20.dp, end = 300.dp))
            {
                Text(text = "Create an account")
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { navController.navigate(Routes.loginpagemenu) })
            {
                Text(text = "Already have an account, Login")
            }

        }
    }


}