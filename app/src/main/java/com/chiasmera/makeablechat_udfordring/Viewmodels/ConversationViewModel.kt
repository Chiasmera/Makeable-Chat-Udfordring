package com.chiasmera.makeablechat_udfordring.Viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chiasmera.makeablechat_udfordring.Model.Conversation
import com.chiasmera.makeablechat_udfordring.Model.Message
import com.chiasmera.makeablechat_udfordring.Model.User
import com.chiasmera.makeablechat_udfordring.Service.DatabaseService
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch


/**
 * Viewmodel for a single conversation. Adds a lsitener to the given conversation on initialization
 * note: Architecturally dubious
 */
class ConversationViewModel(
    currentUser: User,
    conversation: Conversation,
    val db: DatabaseService
) : ViewModel() {
    var currentUser: User by mutableStateOf(currentUser)
    var messages = mutableStateListOf<Message>()
    private var stopListener by mutableStateOf({ })
    var currentConversation: Conversation by mutableStateOf(conversation)

    init {
        viewModelScope.launch {
            startListeningToConversation(currentConversation)
        }
    }

    /**
     * Clears the listener
     */
    override fun onCleared() {
        stopListeningToConversation()
    }

    /**
     * Adds a listener to the given conversation, updating messages on updates
     */
    fun startListeningToConversation(conversation: Conversation) {
        viewModelScope.launch {
            db.addConversationListener(conversation, onUpdatedMessages = { messageList ->
                messages.clear()
                messages.addAll(messageList)
            })
        }
    }

    /**
     * Removes a listener from the active conversation
     */
    fun stopListeningToConversation() {
        stopListener()
    }

    /**
     * Adds a message to the current conversation
     */
    fun addMessage(messageContent: String) {
        val message = Message(messageContent, Timestamp.now(), currentUser)
        viewModelScope.launch {
            currentConversation.let { db.addMessage(it, message) }
        }
    }
}