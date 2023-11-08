package com.chiasmera.makeablechat_udfordring.Viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.setValue

import com.chiasmera.makeablechat_udfordring.Model.User

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chiasmera.makeablechat_udfordring.Service.FirebaseAuthService
import com.chiasmera.makeablechat_udfordring.Service.FirestoreDatabaseService
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

sealed interface LoggedInState {
    data class LoggedIn(val user: User) : LoggedInState
    object Anonymous : LoggedInState
    object Error : LoggedInState
    data class Loading(val message: String) : LoggedInState
}

class UserViewModel : ViewModel() {
    val auth = FirebaseAuthService()
    val db = FirestoreDatabaseService()

    var user : LoggedInState by mutableStateOf(LoggedInState.Anonymous)


    fun login(email : String, password : String) {
        user = LoggedInState.Loading("Signing up and logging in...")

        viewModelScope.launch {

            val id = auth.login(email, password)

            Log.v("ID", id.toString());
            user = if (id == null) {
                LoggedInState.Error
            } else {
                val currentUser = db.getUserById(id)
                if (currentUser != null) {

                    LoggedInState.LoggedIn(currentUser)
                } else {
                    Log.v("ERROR", "user null : $currentUser")
                    LoggedInState.Error
                }
            }
        }
    }

    fun signUp (email : String, password : String, userName: String) {
        user = LoggedInState.Loading("Signing up and logging in...")

        viewModelScope.launch{


            val id = auth.signUp(email, password)

            user = if (id != null) {
                val createdUser = User(id, userName)
                db.createUser( createdUser)
                LoggedInState.LoggedIn(createdUser)
            } else {
                LoggedInState.Error
            }
        }
    }
}