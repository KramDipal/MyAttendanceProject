package eu.tutorials.myattendanceapp

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tutorials.myattendanceapp.pages.LoginPage
import eu.tutorials.myattendanceapp.pages.SignupPage

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier,
                    authViewModel: AuthViewModel,
                    todoViewModel: TodoViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.loginpagemenu,
        builder = {

            //user name and password
            composable(Routes.loginpagemenu){
                LoginPage(modifier = Modifier, navController, authViewModel)
            }

            composable(Routes.signuppage){
                SignupPage(modifier = Modifier, navController, authViewModel)
            }

            composable(Routes.mainmenu){
                MainMenu(Modifier, navController, authViewModel)
            }

            composable(Routes.registration){
                registrationMenu(navController, authViewModel)
            }

            composable(Routes.saveregister){
                saveRegistrationMenu(navController, authViewModel)
            }

            composable(Routes.displaygeneratedqrcode){
                DisplayGeneratedQRcode(navController, authViewModel, todoViewModel)
            }
            composable(Routes.logmemenu){
                logmeMenu(navController, todoViewModel)
            }

            composable(Routes.reportmenu){
                reportMenu(navController, todoViewModel, authViewModel)
            }
        })
}