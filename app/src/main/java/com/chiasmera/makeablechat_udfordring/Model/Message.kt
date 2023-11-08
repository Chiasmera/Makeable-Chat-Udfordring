package com.chiasmera.makeablechat_udfordring.Model

import java.time.LocalDateTime

class Message (
    var content: String = "",
    var timestamp: LocalDateTime = LocalDateTime.now(),
    var user: User
)