package com.chiasmera.makeablechat_udfordring.Views.conversationOverview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chiasmera.makeablechat_udfordring.Model.Conversation
import com.chiasmera.makeablechat_udfordring.Model.User

@Composable
fun ConversationOverView(
    conversations: List<Conversation>,
    onNewConversation: () -> Unit,
    onNavigateToConversation: (Conversation) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        LazyColumn(
            modifier = Modifier
                .align(Alignment.TopStart)
        ) {
            items(conversations) { conversation ->
                ConversationRow(
                    conversation.participants,
                    onNavigateToConversation = { onNavigateToConversation(conversation) })
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
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

@Preview
@Composable
fun ConversationOverViewPreview() {
    ConversationOverView(
        conversations = listOf(Conversation("1", listOf(User("1", "Bob"), User("2", "Hans")))),
        onNewConversation = { /*TODO*/ },
        onNavigateToConversation = {}
    )
}