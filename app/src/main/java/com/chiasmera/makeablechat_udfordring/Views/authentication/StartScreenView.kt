package com.chiasmera.makeablechat_udfordring.Views.authentication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.chiasmera.makeablechat_udfordring.Model.Conversation
import com.chiasmera.makeablechat_udfordring.Model.Message
import com.chiasmera.makeablechat_udfordring.Model.User
import com.chiasmera.makeablechat_udfordring.Service.FirestoreDatabaseService
import com.chiasmera.makeablechat_udfordring.Viewmodels.ConversationViewModel
import com.chiasmera.makeablechat_udfordring.Viewmodels.LoggedInState
import com.chiasmera.makeablechat_udfordring.Views.conversation.ConversationView
import com.chiasmera.makeablechat_udfordring.Views.conversationOverview.ConversationOverView
import java.time.LocalDateTime

@Composable
fun StartScreenView(
    conversations : List<Conversation>,
    loggedInState: LoggedInState,
    onNavigateToSignUp : () -> Unit,
    onLogin : (email : String, password : String) -> Unit,
    onNavigateToConversation : (Conversation) -> Unit
) {
    when (loggedInState) {
        is LoggedInState.LoggedIn -> ConversationOverView(
            conversations = conversations,
            onNewConversation = { /*TODO*/ },
            onNavigateToConversation = onNavigateToConversation
        )
        is LoggedInState.Loading -> Text(loggedInState.message)
        is LoggedInState.Error -> Text("there was an error")
        is LoggedInState.Anonymous -> LogInView(onNavigateToSignUp = onNavigateToSignUp, onLogIn = onLogin)
    }

}