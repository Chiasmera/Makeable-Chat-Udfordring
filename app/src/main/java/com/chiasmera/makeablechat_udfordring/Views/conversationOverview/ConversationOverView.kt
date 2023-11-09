package com.chiasmera.makeablechat_udfordring.Views.conversationOverview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chiasmera.makeablechat_udfordring.Model.Conversation

@Composable
fun ConversationOverView(
    conversations: List<Conversation>,
    onNewConversation : () -> Unit,
    onNavigateToConversation : (Conversation)-> Unit
) {
    Column {


        LazyColumn {
            items(conversations) { conversation ->
                ConversationRow(conversation.participants, onNavigateToConversation = { onNavigateToConversation(conversation) })
            }
        }

        Divider()

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            FilledTonalButton(
                onClick = onNewConversation
            ) {
                Text("New Conversation")
            }
        }
    }
}