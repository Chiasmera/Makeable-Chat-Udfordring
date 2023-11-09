package com.chiasmera.makeablechat_udfordring.Views.authentication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.chiasmera.makeablechat_udfordring.Model.Conversation
import com.chiasmera.makeablechat_udfordring.Viewmodels.LoggedInState
import com.chiasmera.makeablechat_udfordring.Views.conversationOverview.ConversationOverView

@Composable
fun MainScreenView(
    conversations : List<Conversation>,
    loggedInState: LoggedInState,
    onNavigateToSignUp : () -> Unit,
    onLogin : (email : String, password : String) -> Unit,
    onNavigateToConversation : (Conversation) -> Unit,
    onNewConversation : () -> Unit
) {
    when (loggedInState) {
        is LoggedInState.LoggedIn -> ConversationOverView(
            conversations = conversations,
            onNewConversation = onNewConversation,
            onNavigateToConversation = onNavigateToConversation
        )
        is LoggedInState.Loading -> Text(loggedInState.message)
        is LoggedInState.Error -> Text("there was an error")
        is LoggedInState.Anonymous -> LogInView(onNavigateToSignUp = onNavigateToSignUp, onLogIn = onLogin)
    }

}