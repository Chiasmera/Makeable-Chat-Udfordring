package com.chiasmera.makeablechat_udfordring.Viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.chiasmera.makeablechat_udfordring.Model.Message
import com.chiasmera.makeablechat_udfordring.Model.User

class ConversationViewModel () : ViewModel() {
    var currentUser : User? by  mutableStateOf(null)
//    var messages : List<Message>

    init {
        //Begin the snapshot listener here
    }


    fun getMessages() {

    }
}