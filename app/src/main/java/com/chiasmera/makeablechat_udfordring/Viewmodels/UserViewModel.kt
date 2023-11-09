package com.chiasmera.makeablechat_udfordring.Viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue

import com.chiasmera.makeablechat_udfordring.Model.User

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chiasmera.makeablechat_udfordring.Model.Conversation
import com.chiasmera.makeablechat_udfordring.Service.AuthService
import com.chiasmera.makeablechat_udfordring.Service.DatabaseService

import kotlinx.coroutines.launch

sealed interface LoggedInState {
    data class LoggedIn(val user: User) : LoggedInState
    object Anonymous : LoggedInState
    object Error : LoggedInState
    data class Loading(val message: String) : LoggedInState
}

class UserViewModel(
    private val auth : AuthService,
    private val db : DatabaseService
) : ViewModel() {

    var user : LoggedInState by mutableStateOf(LoggedInState.Anonymous)
    var conversations = mutableStateListOf<Conversation>()
    private var stopConversationsListener by mutableStateOf( {  } )
    var users = mutableStateListOf<User>()
    private var stopUsersListener by mutableStateOf( {  } )

    fun login(email : String, password : String) {
        user = LoggedInState.Loading("Signing up and logging in...")

        viewModelScope.launch {

            val id = auth.login(email, password)

            user = if (id == null) {
                LoggedInState.Error
            } else {
                val currentUser = db.getUserById(id)
                if (currentUser != null) {

                    onLogin()
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

                onLogin()
                LoggedInState.LoggedIn(createdUser)


            } else {
                LoggedInState.Error
            }
        }
    }

    fun logout () {
        viewModelScope.launch {
            auth.logout()
        }
        user = LoggedInState.Anonymous
        //Stop listeners
        stopUsersListener()
        stopConversationsListener()
    }


    override fun onCleared() {
        stopUsersListener()
        stopConversationsListener()
    }

    private suspend fun onLogin() {
        viewModelScope.launch {
            db.addAllConversationsListener(onUpdatedConversations = {
                    conversationsList ->
                conversations.clear()
                conversations.addAll(conversationsList)
            })

            db.addAllUsersListener(onUpdatedUsers = {
                    usersList ->
                users.clear()
                users.addAll(usersList)
            })
        }
    }
}