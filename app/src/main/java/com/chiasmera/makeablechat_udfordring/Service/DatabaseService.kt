package com.chiasmera.makeablechat_udfordring.Service

import android.util.Log
import com.chiasmera.makeablechat_udfordring.Model.Conversation
import com.chiasmera.makeablechat_udfordring.Model.Message
import com.chiasmera.makeablechat_udfordring.Model.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

sealed interface DatabaseService {
    /**
     * Returns the list of users
     */
     fun getUsers() : List<User>

    suspend fun getUserById(id : String) : User?

    /**
     * Returns all conversations in which the provided user is a participant
     */
    fun getConversationsForUser(user : User) : List<Conversation>

    /**
     * Returns all messages for a specific conversation
     */
    fun getMessagesForConversation(conversation : Conversation) : List<Message>

    /**
     * Creates a new conversation between the list of users and returns the id
     */
    fun createConversationwithUsers(users : List<User>) : String

    /**
     * Creates a new user in the database
     */
    fun createUser(user : User)
}


class FirestoreDatabaseService : DatabaseService {
    private val db = Firebase.firestore
    val userRef = Firebase.firestore.collection("users")

    /**
     * Returns the list of users
     */
    override fun getUsers() : List<User> {
    return listOf()
    }

    override suspend fun getUserById(id: String): User? {
        var user : User? = null
        Log.v("ERROR", "id : ${id}")
        val docRef = db.collection("users").document(id)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            user = documentSnapshot.toObject<User>()
        }.await()

        Log.v("ERROR", "user id : ${user?.id}")
        Log.v("ERROR", "user name : ${user?.name}")
        return user
    }

    /**
     * Returns all conversations in which the provided user is a participant
     */
    override fun getConversationsForUser(user : User) : List<Conversation> {
        return listOf()
    }

    /**
     * Returns all messages for a specific conversation
     */
    override fun getMessagesForConversation(conversation : Conversation) : List<Message> {
        return listOf()
    }

    override fun createConversationwithUsers(users : List<User>) : String {
        return ""
    }

    override fun createUser(user : User) {
        db.collection("users").document(user.id).set(user)
    }


}