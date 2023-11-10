package com.chiasmera.makeablechat_udfordring.Views.conversation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.chiasmera.makeablechat_udfordring.Viewmodels.ConversationViewModel

@Composable
fun ConversationView(
    conversationViewModel: ConversationViewModel
) {
    var messageContent by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.Bottom) {
        MessageListView(
            messages = conversationViewModel.messages,
            currentUser = conversationViewModel.currentUser
        )

        Divider()

        SendMessageView(
            message = messageContent,
            onValueChange = { messageContent = it },
            onclick = {
                conversationViewModel.addMessage(messageContent)
                messageContent = ""
            }
        )
    }

}