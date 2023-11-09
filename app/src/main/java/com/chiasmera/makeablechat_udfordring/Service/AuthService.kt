package com.chiasmera.makeablechat_udfordring.Service

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

sealed interface AuthService {
    /**
     * Gets the currently logged in user
     */
    fun getCurrentUserID () : String?

    /**
     * Signs in with the provided email and password
     */
    suspend fun login(email : String, password : String) : String?

    /**
     * Logs out the current user
     */
    suspend fun logout ()

    /**
     * Signs up a user
     */
    suspend fun signUp (email : String, password : String) : String?

    /**
     * Deletes and logs out the current user
     */
    suspend fun deleteUser ()
}

class FirebaseAuthService : AuthService {
    private val auth = Firebase.auth

    /**
     * Gets the currently logged in user
     */
    override fun getCurrentUserID () : String? {
        return auth.currentUser?.uid
    }

    /**
     * Signs in with the provided email and password
     */
    override suspend fun login(email : String, password : String) : String? {
         val resultTask = auth.signInWithEmailAndPassword(email, password)
        resultTask.await()
        return resultTask.result.user?.uid
    }

    /**
     * Logs out the current user
     */
    override suspend fun logout () {
        auth.signOut()
    }

    /**
     * Signs up a user
     */
    override suspend fun signUp (email : String, password : String) : String? {
        Log.v("TEST", email)
        Log.v("TEST", password)
        val resultTask = auth.createUserWithEmailAndPassword(email, password)
        resultTask.await()
        return resultTask.result.user?.uid
    }

    /**
     * Deletes and logs out the current user
     */
    override suspend fun deleteUser () {
       auth.currentUser!!.delete()
    }

}