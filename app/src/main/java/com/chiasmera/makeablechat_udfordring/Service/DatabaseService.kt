package com.chiasmera.makeablechat_udfordring.Service

import android.util.Log
import com.chiasmera.makeablechat_udfordring.Model.Conversation
import com.chiasmera.makeablechat_udfordring.Model.Message
import com.chiasmera.makeablechat_udfordring.Model.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await

sealed interface DatabaseService {
    /**
     * Finds a speciffic user by the ID given
     */
    suspend fun getUserById(id: String): User?

    /**
     * Begins listening to messages for a specific conversation.
     * Returns a function that will remove the listener when called
     */
    suspend fun addConversationListener(
        conversation: Conversation, onUpdatedMessages: (List<Message>) -> Unit
    ): () -> Unit

    /**
     * Creates a new conversation between the list of users and returns the id
     */
    suspend fun createConversation(conversation: Conversation)

    /**
     * Creates a new user in the database
     */
    suspend fun createUser(user: User)

    /**
     * Adds a new message to the conversation
     */
    suspend fun addMessage(conversation: Conversation, message: Message)

    /**
     * Adds a listener that will perform the given callback when the list of conversations is updated
     */
    suspend fun addAllConversationsForUserListener(
        user: User, onUpdatedConversations: (List<Conversation>) -> Unit
    ): () -> Unit

    /**
     * Adds a listener to the list of users, performing the given callback on update
     */
    suspend fun addAllUsersListener(onUpdatedUsers: (List<User>) -> Unit): () -> Unit
}

const val TAG = "DATABASESERVICE"
const val USERS = "users"
const val CONVERSATIONS = "conversations"
const val MESSAGES = "messages"


class FirestoreDatabaseService : DatabaseService {
    private val db = Firebase.firestore
    val userRef = Firebase.firestore.collection(USERS)

    override suspend fun getUserById(id: String): User? {
        var user: User? = null
        val docRef = userRef.document(id)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            user = documentSnapshot.toObject<User>()
        }.await()
        return user
    }

    override suspend fun addConversationListener(
        conversation: Conversation, onUpdatedMessages: (List<Message>) -> Unit
    ): () -> Unit {
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

        return { registration.remove() }
    }


    override suspend fun createConversation(conversation: Conversation) {
        val docRef = db.collection(CONVERSATIONS).document(conversation.id)
        docRef.set(conversation)
        docRef.collection(MESSAGES)
    }

    override suspend fun createUser(user: User) {
        db.collection(USERS).document(user.id).set(user)
    }

    override suspend fun addMessage(conversation: Conversation, message: Message) {
        val convRef = db.collection(CONVERSATIONS).document(conversation.id)
        convRef.collection(MESSAGES).add(message)

    }

    override suspend fun addAllConversationsForUserListener(
        user: User, onUpdatedConversations: (List<Conversation>) -> Unit
    ): () -> Unit {
        val colRef = db.collection(CONVERSATIONS).whereArrayContains("participants", user)
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

        return { registration.remove() }
    }

    override suspend fun addAllUsersListener(onUpdatedUsers: (List<User>) -> Unit): () -> Unit {
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

        return { registration.remove() }
    }


}