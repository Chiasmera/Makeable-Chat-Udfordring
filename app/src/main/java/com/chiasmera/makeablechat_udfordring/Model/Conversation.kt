package com.chiasmera.makeablechat_udfordring.Model

import java.util.UUID

class Conversation (
    val id : UUID,
    val participants : List<User>,
    var messages : List<Message>
) {

}