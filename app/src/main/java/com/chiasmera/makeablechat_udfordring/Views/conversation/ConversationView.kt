package com.chiasmera.makeablechat_udfordring.Views.conversation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chiasmera.makeablechat_udfordring.Model.Message
import com.chiasmera.makeablechat_udfordring.Model.User
import com.chiasmera.makeablechat_udfordring.Viewmodels.ConversationViewModel

import java.time.LocalDateTime
import java.util.UUID

@Composable
fun ConversationView(
    modifier: Modifier = Modifier,
    conversationViewModel: ConversationViewModel
) {
    var messageContent by remember { mutableStateOf("") }

    Column (verticalArrangement = Arrangement.Bottom) {
        MessageListView(messages = conversationViewModel.messages, currentUser = conversationViewModel.currentUser)

        Divider()

        SendMessageView(
            message = messageContent,
            onValueChange = { messageContent = it},
            onclick = {
                conversationViewModel.addMessage(messageContent)
                messageContent = ""
            })
    }
}

//@Preview
//@Composable
//fun ConversationPreview() {
//    val user1 = UUID.randomUUID()
//    val user2 = UUID.randomUUID()
//    val currentUser = User(user1.toString(), "mig selv")
//    val otherUser = User (user2.toString(), "anden dude")
//
//    ConversationView(messages = listOf(
//        Message("Hej", LocalDateTime.now(), currentUser),
//        Message("Hej selv", LocalDateTime.now(), otherUser),
//        Message("nej tak", LocalDateTime.now(),currentUser ),
//        Message("farvel", LocalDateTime.now(), currentUser),
//        Message("okay", LocalDateTime.now(), otherUser)
//    ),
//        currentUser = currentUser
//    )
//}