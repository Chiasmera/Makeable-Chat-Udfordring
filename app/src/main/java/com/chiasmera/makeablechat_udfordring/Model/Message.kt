package com.chiasmera.makeablechat_udfordring.Model

import com.google.firebase.Timestamp
import java.time.LocalDateTime

class Message (
    var content: String = "",
    var timestamp: Timestamp = Timestamp.now(),
    var user: User = User("", "")
)