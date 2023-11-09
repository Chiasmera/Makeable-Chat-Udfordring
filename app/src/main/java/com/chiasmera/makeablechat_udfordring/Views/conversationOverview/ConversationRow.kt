package com.chiasmera.makeablechat_udfordring.Views.conversationOverview

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.chiasmera.makeablechat_udfordring.Model.Conversation
import com.chiasmera.makeablechat_udfordring.Model.User

@Composable
fun ConversationRow(
    participants: List<User>,
    onNavigateToConversation: () -> Unit
) {
        TextButton(onClick = onNavigateToConversation

        ) {
            Text(participants.joinToString(separator = ", "))
        }
}