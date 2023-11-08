package com.chiasmera.makeablechat_udfordring.Views.authentication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.chiasmera.makeablechat_udfordring.Model.Message
import com.chiasmera.makeablechat_udfordring.Model.User
import com.chiasmera.makeablechat_udfordring.Viewmodels.LoggedInState
import com.chiasmera.makeablechat_udfordring.Views.conversation.ConversationView
import java.time.LocalDateTime

@Composable
fun StartScreenView(
    loggedInState: LoggedInState,
    onNavigateToSignUp : () -> Unit,
    onLogin : (email : String, password : String) -> Unit
) {
    when (loggedInState) {
        is LoggedInState.LoggedIn -> ConversationView(messages = listOf(
            Message("Hej", LocalDateTime.now(), loggedInState.user),
            Message("Hej selv", LocalDateTime.now(), User("1", "Dude")),
            Message("nej tak", LocalDateTime.now(),loggedInState.user ),
            Message("farvel", LocalDateTime.now(), loggedInState.user),
            Message("okay", LocalDateTime.now(), User("1", "Dude"))
        ),
            currentUser = loggedInState.user
        )
        is LoggedInState.Loading -> Text(loggedInState.message)
        is LoggedInState.Error -> Text("there was an error")
        is LoggedInState.Anonymous -> LogInView(onNavigateToSignUp = onNavigateToSignUp, onLogIn = onLogin)
    }

}