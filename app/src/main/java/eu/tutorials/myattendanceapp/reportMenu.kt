package eu.tutorials.myattendanceapp

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun reportMenu(navController: NavController,
               todoViewModel: TodoViewModel){
    
   // val selectAllTodo = todoViewModel.selectAllTodo()
    val selectAllTodo = todoViewModel.selectAllTodo()

    /*LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
    {
        items(selectAllTodo){

       }
    }

    Log.i("reportMenu","$selectAllTodo")
    */


    Text(text = "This is report menu in progress!!!")
    
}