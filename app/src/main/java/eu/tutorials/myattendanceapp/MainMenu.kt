package eu.tutorials.myattendanceapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun MainMenu(modifier: Modifier = Modifier,
             navController: NavController,
             authViewModel: AuthViewModel){


    val authState = authViewModel.authstate.observeAsState()
    val context = LocalContext.current

    // For user access limitation
    // TO save the last logged-in username.  This will fix button enabled limitations if application
    // was closed
    val getSaveUsername = MyAppPreferences(context).getParameterValue()
    val getSaveUsernameX = getSaveUsername?.substringBefore("@")
    val username = authViewModel.userName.substringBefore("@")
    val adminUser = "kiko1234"
    var getUserName: Boolean = false

    Log.i("MainMenu", "${authViewModel.userName}, username: $username, getSaveUsernameX: $getSaveUsernameX")

    if(username.equals(adminUser, ignoreCase = true) ||
        getSaveUsernameX.equals(adminUser, ignoreCase = true))
    {
        Log.i("MainMenu", "It's True!!!")

        getUserName = true
    }
    // For user access limitation



    LaunchedEffect(authState.value){
         when(authState.value) {
             is AuthState.Unathenticated -> navController.navigate(Routes.loginpagemenu)
             else -> Unit
         }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent)
        //color = Color(0xffb5dbc7)) //customized color
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.mbt),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(width = 900.dp, height = 300.dp)
            )


            Button(
                onClick = { navController.navigate(Routes.registration) },
                modifier = Modifier.fillMaxWidth().padding(start = 300.dp, top = 20.dp, end = 300.dp),
                enabled = getUserName//.fillMaxWidth().padding(all = 5.dp)
            ) {
                Text(text = "Register")
            }

            Button(
                onClick = { navController.navigate(Routes.logmemenu) },
                modifier = Modifier.fillMaxWidth().padding(start = 300.dp, top = 20.dp, end = 300.dp)//fillMaxWidth().padding(all = 5.dp)
            ) {
                Text(text = "Log me in")
            }

            Button(
                onClick = { navController.navigate(Routes.reportmenu) },
                modifier = Modifier.fillMaxWidth().padding(start = 300.dp, top = 20.dp, end = 300.dp),
                enabled = getUserName//fillMaxWidth().padding(all = 5.dp)
            ) {
                Text(text = "Report")
            }

            Button(
                onClick = { authViewModel.signout() },
                modifier = Modifier.fillMaxWidth().padding(start = 300.dp, top = 20.dp, end = 300.dp)//fillMaxWidth().padding(all = 5.dp)
            ) {
                Text(text = "Sign Out")
            }

        }
    }


}