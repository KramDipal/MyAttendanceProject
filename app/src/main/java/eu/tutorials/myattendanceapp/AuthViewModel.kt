package eu.tutorials.myattendanceapp

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AuthViewModel : ViewModel() {


        //for registration menu
        var fname by mutableStateOf("")
        fun onTextChanged(newText: String) {
                fname = newText
        }

        var lname by mutableStateOf("")
        fun onTextChanged2(newText: String) {
                lname = newText
        }

        var designation by mutableStateOf("")
        fun onTextChanged3(newText: String) {
                designation = newText
        }

        var empid by mutableStateOf("")
        fun onTextChanged4(newText: String) {
                empid = newText
        }

        //For access limitation
        var userName by mutableStateOf("")
        // Get SharedPreferences


        //it create and instance so we can access Firebase authentication
        private val auth: FirebaseAuth = FirebaseAuth.getInstance()

        private val _authstate = MutableLiveData<AuthState>()

        //"authstate" variable that was exposed and can be use in the UI.
        val authstate : LiveData<AuthState> = _authstate


        init {
            checkAuthStatus()
        }
        // checkAuthStatus - is a method for authentication
        fun checkAuthStatus(){
                if(auth.currentUser == null){
                        _authstate.value = AuthState.Unathenticated
                }else{
                        _authstate.value = AuthState.Authenticated
                }

        }

        //Login Method
        //from view model to call FireBase authentication
        //call upon click on LoginPage
        fun Login(email: String, password: String){
                if(email.isEmpty() || password.isEmpty()){
                        _authstate.value = AuthState.Error("Email or password can't be empty")
                        return
                }


                _authstate.value = AuthState.Loading
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener{task->
                                if(task.isSuccessful){
                                        _authstate.value = AuthState.Authenticated
                                        userName = email
                                }
                                else
                                {
                                        _authstate.value = AuthState.Error(
                                                task.exception?.message?:"Something went wrong"
                                        )

                                }
                        }
        }

        //SignIn method
        fun signup(email: String, password: String){
                Log.i("Credentials", "SignUp $email $password")
                if(email.isEmpty() || password.isEmpty()){
                        Log.i("Credentials", "SignUp2")
                        _authstate.value = AuthState.Error("Email or password can't be empty")
                        return
                }
                _authstate.value = AuthState.Loading
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener{task->
                                if(task.isSuccessful){
                                        Log.i("Credentials", "SignUp successful")
                                        _authstate.value = AuthState.Authenticated
                                }
                                else
                                {
                                        Log.i("Credentials", "SignUp4")
                                        _authstate.value = AuthState.Error(
                                                task.exception?.message?:"Something went wrong"
                                        )

                                }
                        }
        }

        //signout method
        fun signout(){
                Log.i("Credentials", "SignOut!")
                auth.signOut()
                _authstate.value = AuthState.Unathenticated
        }




        //date picker function
        @Composable
        fun DateRangePicker() {
                var fromDate by remember { mutableStateOf<Long?>(null) }
                var toDate by remember { mutableStateOf<Long?>(null) }

                Column(modifier = Modifier.padding(16.dp)) {
                        DatePickerDialog(
                                label = "From Date",
                                selectedDate = fromDate,
                                onDateSelected = { fromDate = it }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        DatePickerDialog(
                                label = "To Date",
                                selectedDate = toDate,
                                onDateSelected = { toDate = it }
                        )
                }
        }

        @Composable
        fun DatePickerDialog(
                label: String,
                selectedDate: Long?,
                onDateSelected: (Long) -> Unit
        ) {
                val context = LocalContext.current
                val calendar = Calendar.getInstance()
                selectedDate?.let { calendar.timeInMillis = it }

                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)


                Log.i("DatePickerDialog","$year $month $day")

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
                        value = selectedDate?.let { SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(
                                Date(it)
                        ) } ?: "",
                        onValueChange = {},
                        label = { Text(label) },
                        readOnly = true,
                        trailingIcon = {
                                IconButton(onClick = { datePickerDialog.show() }) {
                                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select date")
                                }
                        }//,
                        //modifier = Modifier.fillMaxWidth(1f)
                )
        }




}

sealed class AuthState{
        object Authenticated : AuthState() // go to home page
        object Unathenticated : AuthState() // go to login page
        object Loading : AuthState()
        data class Error(val message: String) : AuthState() // to show the message
}

