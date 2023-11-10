package com.chiasmera.makeablechat_udfordring.Views.conversation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chiasmera.makeablechat_udfordring.Model.Message
import com.chiasmera.makeablechat_udfordring.Model.User
import com.google.firebase.Timestamp
import java.util.UUID

@Composable
fun MessageListView(
    messages: List<Message>,
    currentUser: User
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        reverseLayout = false,
        verticalArrangement = Arrangement.Bottom,
    ) {
        items(messages) { message ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = if (message.user == currentUser) {
                    Alignment.End
                } else {
                    Alignment.Start
                }
            ) {
                Text(
                    "${message.user.name} ${message.timestamp.toDate()}",
                    style = MaterialTheme.typography.labelMedium
                )
                Surface(
                    modifier = Modifier.padding(8.dp),
                    shape = MaterialTheme.shapes.large.copy(
                        bottomEnd = if (currentUser == message.user) {
                            CornerSize(0.dp)
                        } else {
                            CornerSize(12.dp)
                        },
                        bottomStart = if (currentUser != message.user) {
                            CornerSize(0.dp)
                        } else {
                            CornerSize(12.dp)
                        }
                    ),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = message.content,
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 24.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun MessageListPreview() {
    val user1 = UUID.randomUUID()
    val user2 = UUID.randomUUID()
    val currentUser = User(user1.toString(), "mig selv")
    val otherUser = User(user2.toString(), "anden dude")

    MessageListView(
        messages = listOf(
            Message("Hej", Timestamp.now(), currentUser),
            Message("Hej selv", Timestamp.now(), otherUser),
            Message("nej tak", Timestamp.now(), currentUser),
            Message("farvel", Timestamp.now(), currentUser),
            Message("okay", Timestamp.now(), otherUser)
        ),
        currentUser = currentUser
    )
}