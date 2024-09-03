package eu.tutorials.myattendanceapp

import android.os.Environment
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun reportMenu(navController: NavController,
               todoViewModel: TodoViewModel){

    var empID by remember {
        mutableStateOf("")
    }
    val todoList by todoViewModel.todoList.observeAsState()
    val todoListAll by todoViewModel.todoListAll.observeAsState()
    val todoListAny by todoViewModel.todoListAny.observeAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(4.dp)
    ) {
        Row {

            /*
            OutlinedTextField(value = empID, onValueChange = { empID = it }, label = {
                androidx.compose.material3.Text(text = "Employee ID")
            },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))

            Button(onClick = { /*TODO*/ })
            {
                Text(text = "Search by ID")
            }
            */
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(4.dp)
    ) {
        todoListAny?.let {
            Row {
                Button(onClick = {
                    //val filePath = "${context.filesDir}/MyAttendanceApp.csv"
                    //val filePath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/MyAttendanceApp${System.currentTimeMillis()}.csv"
                    //writeDataToCsv(todoListAny!!, filePath)
                    //Toast.makeText(context, "CSV file created at $filePath", Toast.LENGTH_LONG).show()

                    val filePath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/MyAttendanceApp${System.currentTimeMillis()}.pdf"
                    writeDataToPdf(todoListAny!!, filePath)
                    Toast.makeText(context, "PDF file created at $filePath", Toast.LENGTH_LONG).show()
                })
                {
                    Text(text = "GENERATE REPORT")
                }

                /*OutlinedTextField(value = empID, onValueChange = { empID = it }, label = {
                    androidx.compose.material3.Text(text = "Employee ID")
                })

                Button(onClick = { todoViewModel.selectTodoAnyLoginUser(empID) }) {
                    Text(text = "SEARCH")

                }*/

            }

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

            Text(text = "First Name:    ${item.fname}",fontSize = 16.sp,color = Color.White)
            Text(text = "Last Name:     ${item.lname}",fontSize = 16.sp,color = Color.White)
            Text(text = "Employee ID:   ${item.empid}",fontSize = 16.sp,color = Color.White)
            Text(text = "Designation:   ${item.designation}",fontSize = 16.sp,color = Color.White)
            Text(text = "Latitude:      ${item.latitude}",fontSize = 16.sp,color = Color.White)
            Text(text = "Longitute:     ${item.longitude}",fontSize = 16.sp,color = Color.White)
            Text(text = "Date Created:  ${item.createdAt}",fontSize = 16.sp,color = Color.White)

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
        /*
        IconButton(onClick = onCreate)
        //modifier = Modifier.size(width = 10.dp, height = 10.dp),
        //colors = IconButtonDefaults.filledIconButtonColors())
        {
            //Icon(
            Icon(Icons.Filled.Create, contentDescription = "Create CSV")
            //painter = painterResource(id = R.drawable.delete4),
            //contentDescription = null
            //tint = Color.Black
            //)
        }
        */
    }

}
