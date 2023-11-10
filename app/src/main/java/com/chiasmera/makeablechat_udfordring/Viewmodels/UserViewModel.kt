package com.chiasmera.makeablechat_udfordring.Viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chiasmera.makeablechat_udfordring.Model.Conversation
import com.chiasmera.makeablechat_udfordring.Model.User
import com.chiasmera.makeablechat_udfordring.Service.AuthService
import com.chiasmera.makeablechat_udfordring.Service.DatabaseService
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * Interface to keep track of the login state
 */
sealed interface LoggedInState {
    data class LoggedIn(val user: User) : LoggedInState
    object Anonymous : LoggedInState
    object Error : LoggedInState
    data class Loading(val message: String) : LoggedInState
}

/**
 * Class responsible for the state of the user and the users options in the app
 */
class UserViewModel(
    private val auth: AuthService,
    private val db: DatabaseService
) : ViewModel() {

    var user: LoggedInState by mutableStateOf(LoggedInState.Anonymous)
    var conversations = mutableStateListOf<Conversation>()
    private var stopConversationsListener by mutableStateOf({ })
    var users = mutableStateListOf<User>()
    private var stopUsersListener by mutableStateOf({ })

    /**
     * Logs in the user via the authentication system
     */
    fun login(email: String, password: String) {
        user = LoggedInState.Loading("Signing up and logging in...")

        viewModelScope.launch {

            val id = auth.login(email, password)

            user = if (id == null) {
                LoggedInState.Error
            } else {
                val currentUser = db.getUserById(id)
                if (currentUser != null) {

                    onLogin(currentUser)
                    LoggedInState.LoggedIn(currentUser)


                } else {
                    Log.v("ERROR", "user null : $currentUser")
                    LoggedInState.Error
                }
            }
        }
    }

    /**
     * Signs up a new user, and then logs in the user
     */
    fun signUp(email: String, password: String, userName: String) {
        user = LoggedInState.Loading("Signing up and logging in...")

        viewModelScope.launch {


            val id = auth.signUp(email, password)

            user = if (id != null) {
                val createdUser = User(id, userName)
                db.createUser(createdUser)

                onLogin(createdUser)
                LoggedInState.LoggedIn(createdUser)


            } else {
                LoggedInState.Error
            }
        }
    }

    /**
     * Logs out the user
     * TODO: Implement a button that actually uses this
     */
    fun logout() {
        viewModelScope.launch {
            auth.logout()
        }
        user = LoggedInState.Anonymous
        //Stop listeners
        stopUsersListener()
        stopConversationsListener()
    }

    /**
     * Removes listeners on conversations and users upon clearing
     */
    override fun onCleared() {
        stopUsersListener()
        stopConversationsListener()
    }

    /**
     * Helper function that adds listeners to conversations and users, upon a user loggin in
     */
    private suspend fun onLogin(user: User) {
        viewModelScope.launch {
            db.addAllConversationsForUserListener(
                user = user,
                onUpdatedConversations = { conversationsList ->
                    conversations.clear()
                    conversations.addAll(conversationsList)
                })

            db.addAllUsersListener(onUpdatedUsers = { usersList ->
                users.clear()
                users.addAll(usersList)
            })
        }
    }

    /**
     * Creates a new conversation in the database
     */
    fun createConversation(participants: List<User>): Conversation {
        val conversation = Conversation(
            id = UUID.randomUUID().toString(),
            participants = participants
        )
        viewModelScope.launch {
            db.createConversation(conversation)
        }
        return conversation
    }
}