package com.chiasmera.makeablechat_udfordring.Views.conversationOverview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chiasmera.makeablechat_udfordring.Model.User

@Composable
fun ConversationRow(
    participants: List<User>,
    onNavigateToConversation: () -> Unit
) {
    Column {


        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            onClick = onNavigateToConversation,
            shape = MaterialTheme.shapes.extraLarge.copy(),
        ) {
            Text(
                participants.joinToString(separator = ", "),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Divider()
    }
}

@Preview
@Composable
fun ConversationRowPreview() {
    ConversationRow(
        participants = listOf(User("1", "Bob"), User("2", "Hans")),
        onNavigateToConversation = {})
}