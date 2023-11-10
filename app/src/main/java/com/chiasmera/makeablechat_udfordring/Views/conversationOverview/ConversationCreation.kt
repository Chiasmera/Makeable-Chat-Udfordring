package com.chiasmera.makeablechat_udfordring.Views.conversationOverview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chiasmera.makeablechat_udfordring.Model.User
import com.chiasmera.makeablechat_udfordring.R

@Composable
fun ConversationCreationView(
    currentUser: User,
    users: List<User>,
    onNewConversation: (List<User>) -> Unit
) {
    val selectedUsers: MutableList<User> = remember { mutableStateListOf() }
    val otherUsers = mutableListOf<User>()
    otherUsers.addAll(users)
    otherUsers.remove(currentUser)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Select user(s) to start conversation with",
            modifier = Modifier
                .padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn {
            items(otherUsers) { user ->
                var selected by remember { mutableStateOf(false) }
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    onClick = {
                        if (!selected) {
                            selected = true
                            selectedUsers.add(user)
                        } else {
                            selected = false
                            selectedUsers.remove(user)
                        }
                    }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(user.name)

                        if (selected) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_check_24),
                                contentDescription = "Selected"
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.baseline_radio_button_unchecked_24),
                                contentDescription = "Unselected"
                            )
                        }

                    }
                }

            }
        }

        FilledTonalButton(
            modifier = Modifier
                .padding(16.dp), onClick = {
                selectedUsers.add(currentUser)
                onNewConversation(selectedUsers)
            }
        ) {
            Text("Begin Conversation")
        }
    }

}


@Preview(showBackground = true)
@Composable
fun ConversationCreationPreview() {
    val user1 = User("1", "Testuser")
    val user2 = User("2", "Also Testuser")
    ConversationCreationView(
        user1,
        listOf(user1, user2),
        {}
    )
}