package eu.tutorials.myattendanceapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class AuthViewModel : ViewModel() {


        //for registration menu
        //fname
        var fname by mutableStateOf("")

        fun onTextChanged(newText: String) {
                fname = newText
        }

        //lname
        var lname by mutableStateOf("")

        fun onTextChanged2(newText: String) {
                lname = newText
        }

        //designation
        var designation by mutableStateOf("")

        fun onTextChanged3(newText: String) {
                designation = newText
        }

        //empid
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




}

sealed class AuthState{
        object Authenticated : AuthState() // go to home page
        object Unathenticated : AuthState() // go to login page
        object Loading : AuthState()
        data class Error(val message: String) : AuthState() // to show the message
}

