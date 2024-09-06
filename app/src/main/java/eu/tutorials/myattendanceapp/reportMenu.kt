package eu.tutorials.myattendanceapp

import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonColors
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun reportMenu(navController: NavController,
               todoViewModel: TodoViewModel,
               authViewModel: AuthViewModel){

    var empID by remember {
        mutableStateOf("")
    }
    val todoList by todoViewModel.todoList.observeAsState()
    val todoListAll by todoViewModel.todoListAll.observeAsState()
    val todoListAny by todoViewModel.todoListAny.observeAsState()
    val context = LocalContext.current

    //date picker
    var fromDate by remember { mutableStateOf<Long?>(null) }
    var toDate by remember { mutableStateOf<Long?>(null) }
    //date picker



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
        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "SELECT DATE",
            color = Color.LightGray,
            fontSize = 30.sp,
            letterSpacing = 5.sp,
            textDecoration = TextDecoration.Underline,
            lineHeight = 5.sp,
            fontWeight = FontWeight.Bold)

        todoListAny?.let {
            Row {

                val getFromDate = DatePickerDialog(
                    label = "From Date",
                    selectedDate = fromDate,
                    onDateSelected = { fromDate = it }
                )
                var formattedDate: String? = null
                if (getFromDate != null) {
                    formattedDate = formatLongDate(getFromDate)
                }
                Log.i("reportMenu/formattedDate","$formattedDate")



                val getToDate = DatePickerDialog2(
                    label = "To Date",
                    selectedDate = toDate,
                    onDateSelected = { toDate = it }
                )
                var formattedDate2: String? = null
                if (getToDate != null) {
                    formattedDate2 = formatLongDate(getToDate)
                }
                Log.i("reportMenu/formattedDate2","$formattedDate2")



                Button(onClick = {

                    //val filePath = "${context.filesDir}/MyAttendanceApp.csv"
                    //val filePath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/MyAttendanceApp${System.currentTimeMillis()}.csv"
                    //writeDataToCsv(todoListAny!!, filePath)
                    //Toast.makeText(context, "CSV file created at $filePath", Toast.LENGTH_LONG).show()
                    //if(authViewModel.fromDate == 0L || authViewModel.toDate == 0L){
                    //    Toast.makeText(context, "Please select (To) and (From) date", Toast.LENGTH_LONG).show()
                    //}
                    //else
                    //{

                    if (formattedDate != null && formattedDate2 != null)
                    {
                        val filePath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/MyAttendanceApp${System.currentTimeMillis()}.pdf"
                        writeDataToPdf(todoListAny!!, filePath, formattedDate, formattedDate2)

                        Toast.makeText(context, "PDF file created at $filePath", Toast.LENGTH_LONG)
                            .show()
                    }
                    else
                    {
                        Toast.makeText(context, "Please select (To) and (From) dates", Toast.LENGTH_LONG)
                            .show()
                    }
                    //}

                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray, // Background color
                        contentColor = Color.White, // Text color
                        disabledContainerColor = Color.Gray, // Background color when disabled
                        disabledContentColor = Color.LightGray // Text color when disabled
                    )
                )
                {
                    Text(text = "GENERATE REPORT",
                        color = Color.Magenta,
                        fontWeight = FontWeight.Bold
                    )
                }
                /*OutlinedTextField(value = empID, onValueChange = { empID = it }, label = {
                    androidx.compose.material3.Text(text = "Employee ID")
                })

                Button(onClick = { todoViewModel.selectTodoAnyLoginUser(empID) }) {
                    Text(text = "SEARCH")

                }*/


            }

            Spacer(modifier = Modifier.height(6.dp))

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

fun formatLongDate(timestamp: Long): String {
    val instant = Instant.ofEpochMilli(timestamp)
    val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
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
            Text(text = "Date / Time Logged:  ${item.createdAt}  ${item.createdTime}",fontSize = 16.sp,color = Color.White)
            //Text(text = "Time Logged:  ${item.createdTime}",fontSize = 16.sp,color = Color.White)
            //Text(text = "Date Created:  ${item.timelog}",fontSize = 16.sp,color = Color.White)

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

@Composable
fun DatePickerDialog(
    label: String,
    selectedDate: Long?,
    onDateSelected: (Long) -> Unit
) : Long?
{
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    selectedDate?.let { calendar.timeInMillis = it }


    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    //Log.i("DatePickerDialog/onDateSelected","$selectedDate")
    //Log.i("DatePickerDialog","$year $month $day")

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
            onDateSelected(selectedCalendar.timeInMillis)
        },
        year, month, day
    )

    OutlinedTextField(
        value = selectedDate?.let { android.icu.text.SimpleDateFormat(
            "MM/dd/yyyy",
            Locale.getDefault()
        ).format(Date(it)) } ?: "",
        onValueChange = {},
        label = { androidx.compose.material3.Text(label) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select date")
            }
        }//,
        //modifier = Modifier.fillMaxWidth(1f)
    )

    return selectedDate
}

@Composable
fun DatePickerDialog2(
    label: String,
    selectedDate: Long?,
    onDateSelected: (Long) -> Unit
) : Long?
{
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    selectedDate?.let { calendar.timeInMillis = it }


    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    //Log.i("DatePickerDialog/onDateSelected","$selectedDate")
    //Log.i("DatePickerDialog","$year $month $day")

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
            onDateSelected(selectedCalendar.timeInMillis)
        },
        year, month, day
    )

    OutlinedTextField(
        value = selectedDate?.let { android.icu.text.SimpleDateFormat(
            "MM/dd/yyyy",
            Locale.getDefault()
        ).format(Date(it)) } ?: "",
        onValueChange = {},
        label = { androidx.compose.material3.Text(label) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select date")
            }
        }//,
        //modifier = Modifier.fillMaxWidth(1f)
    )

    return selectedDate
}

