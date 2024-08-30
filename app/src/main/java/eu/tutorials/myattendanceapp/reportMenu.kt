package eu.tutorials.myattendanceapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun reportMenu(navController: NavController,
               todoViewModel: TodoViewModel){

    val todoList by todoViewModel.todoList.observeAsState()
    val todoListAll by todoViewModel.todoListAll.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        todoListAll?.let {
            LazyColumn(
                content = {
                    itemsIndexed(it){index: Int, item: LoginUser ->
                        TodoItem(item = item, onDelete = {
                            todoViewModel.deleteTodoLog(item.id)
                        })
                    }
                }
            )

        }
    }

}

@Composable
fun TodoItem(item : LoginUser,onDelete : ()-> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(text = "First Name:    ${item.fname}",fontSize = 20.sp,color = Color.White)
            Text(text = "Last Name:     ${item.lname}",fontSize = 20.sp,color = Color.White)
            Text(text = "Employee ID:   ${item.empid}",fontSize = 20.sp,color = Color.White)
            Text(text = "Designation:   ${item.designation}",fontSize = 20.sp,color = Color.White)
            Text(text = "Latitude:      ${item.latitude}",fontSize = 20.sp,color = Color.White)
            Text(text = "Longitute:     ${item.longitude}",fontSize = 20.sp,color = Color.White)
            Text(text = "Date Created:  ${item.createdAt}",fontSize = 20.sp,color = Color.White)

        }
        IconButton(onClick = onDelete)
                    //modifier = Modifier.size(width = 10.dp, height = 10.dp),
                    //colors = IconButtonDefaults.filledIconButtonColors())
        {
            //Icon(
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
                //painter = painterResource(id = R.drawable.delete4),
                //contentDescription = null
                //tint = Color.Black
            //)
        }
    }
}
