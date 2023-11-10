package com.chiasmera.makeablechat_udfordring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.chiasmera.makeablechat_udfordring.Service.FirebaseAuthService
import com.chiasmera.makeablechat_udfordring.Service.FirestoreDatabaseService
import com.chiasmera.makeablechat_udfordring.Viewmodels.UserViewModel
import com.chiasmera.makeablechat_udfordring.Views.ChatApp
import com.chiasmera.makeablechat_udfordring.ui.theme.MakeableChatUdfordringTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MakeableChatUdfordringTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val db = FirestoreDatabaseService()
                    val auth = FirebaseAuthService()

                    ChatApp(
                        navController = rememberNavController(),
                        db = db,
                        userViewModel = UserViewModel(auth, db)
                    )
                }

            }
        }
    }
}