package com.chiasmera.makeablechat_udfordring.Model

import java.util.UUID

class Conversation (
    var id : String = "",
    var participants : List<User> = listOf()
)