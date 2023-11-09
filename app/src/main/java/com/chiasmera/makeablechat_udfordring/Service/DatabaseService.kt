package com.chiasmera.makeablechat_udfordring.Service

import android.util.Log
import com.chiasmera.makeablechat_udfordring.Model.Conversation
import com.chiasmera.makeablechat_udfordring.Model.Message
import com.chiasmera.makeablechat_udfordring.Model.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.UUID

sealed interface DatabaseService {
//    /**
//     * Returns the list of users
//     */
//     suspend fun getUsers() : Collection<User>

    suspend fun getUserById(id : String) : User?

//    /**
//     * Returns all conversations in which the provided user is a participant
//     */
//    suspend fun getConversationsForUser(user : User) : List<Conversation>

    /**
     * Begins listening to messages for a specific conversation.
     * Returns a function that will remove the listener when called
     */
    suspend fun addConversationListener(conversation : Conversation, onUpdatedMessages : (List<Message>) -> Unit)  : () -> Unit

    /**
     * Creates a new conversation between the list of users and returns the id
     */
    suspend fun createConversationWithUsers(users : Collection<User>) : Conversation

    /**
     * Creates a new user in the database
     */
    suspend fun createUser(user : User)

    /**
     * Adds a new message to the conversation
     */
    suspend fun addMessage(conversation : Conversation, message: Message)

    suspend fun addAllConversationsListener(onUpdatedConversations :(List<Conversation>) -> Unit ) : () -> Unit

    suspend fun addAllUsersListener(onUpdatedUsers :(List<User>) -> Unit ) : () -> Unit
}

const val TAG = "DATABASESERVICE"
const val USERS = "users"
const val CONVERSATIONS = "conversations"
const val MESSAGES = "messages"


class FirestoreDatabaseService : DatabaseService {
    private val db = Firebase.firestore
    val userRef = Firebase.firestore.collection(USERS)

//    /**
//     * Returns the list of users
//     */
//    override suspend fun getUsers() : Collection<User> {
//        val resultTask = userRef.get()
//        resultTask.await()
//        return resultTask.result.toObjects<User>()
//    }

    override suspend fun getUserById(id: String): User? {
        var user : User? = null
        val docRef = userRef.document(id)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            user = documentSnapshot.toObject<User>()
        }.await()
        return user
    }

//    /**
//     * Returns all conversations in which the provided user is a participant
//     */
//    override suspend fun getConversationsForUser(user : User) : List<Conversation> {
//        val conversations = db.collection("conversations").whereArrayContains("participants", user).get()
//        conversations.await()
//
//        return conversations.result.toObjects(Conversation::class.java)
//    }


    /**
     * Returns all messages for a specific conversation
     */
    override suspend fun addConversationListener(conversation : Conversation, onUpdatedMessages : (List<Message>) -> Unit) : () -> Unit {
        val colRef = db.collection(CONVERSATIONS).document(conversation.id).collection(MESSAGES)
        val registration = colRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val list = snapshot.toObjects<Message>().sortedBy { m -> m.timestamp }
                onUpdatedMessages(list)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }

        return {  registration.remove() }
    }


    override suspend fun createConversationWithUsers(users : Collection<User>) : Conversation {
        val conversation = Conversation(
            id = UUID.randomUUID().toString(),
            participants = users.toList()
            )
        val docRef = db.collection(CONVERSATIONS).document(conversation.id)
        docRef.set(conversation)
        docRef.collection(MESSAGES)

        return conversation
    }

    override suspend fun createUser(user : User) {
        db.collection(USERS).document(user.id).set(user)
    }

    override suspend fun addMessage(conversation : Conversation, message: Message) {
        val convRef = db.collection(CONVERSATIONS).document(conversation.id)
        convRef.collection(MESSAGES).add(message)

    }

    override suspend fun addAllConversationsListener(onUpdatedConversations: (List<Conversation>) -> Unit) : () -> Unit {
        val colRef = db.collection(CONVERSATIONS)
        val registration = colRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val list = snapshot.toObjects<Conversation>()
                onUpdatedConversations(list)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }

        return {  registration.remove() }
    }

    override suspend fun addAllUsersListener(onUpdatedUsers: (List<User>) -> Unit) : () -> Unit {
        val colRef = db.collection(USERS)
        val registration = colRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val list = snapshot.toObjects<User>()
                onUpdatedUsers(list)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }

        return {  registration.remove() }
    }


}