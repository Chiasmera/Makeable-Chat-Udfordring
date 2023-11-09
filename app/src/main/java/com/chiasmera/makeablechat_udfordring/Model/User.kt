package com.chiasmera.makeablechat_udfordring.Model

import java.util.UUID

class User (
    var id: String = "",
    var name: String = ""
) {

    override fun equals(other: Any?): Boolean {
        if (other is User) {
            return this.id == other.id
        }
        return false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return name
    }
}