package com.chiasmera.makeablechat_udfordring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chiasmera.makeablechat_udfordring.Service.Destinations
import com.chiasmera.makeablechat_udfordring.Service.FirebaseAuthService
import com.chiasmera.makeablechat_udfordring.Service.FirestoreDatabaseService
import com.chiasmera.makeablechat_udfordring.Viewmodels.ConversationViewModel
import com.chiasmera.makeablechat_udfordring.Viewmodels.LoggedInState
import com.chiasmera.makeablechat_udfordring.Viewmodels.UserViewModel
import com.chiasmera.makeablechat_udfordring.Views.ChatApp
import com.chiasmera.makeablechat_udfordring.Views.authentication.SignUpView
import com.chiasmera.makeablechat_udfordring.Views.authentication.MainScreenView
import com.chiasmera.makeablechat_udfordring.Views.conversation.ConversationView
import com.chiasmera.makeablechat_udfordring.ui.theme.MakeableChatUdfordringTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
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
                        userViewModel = UserViewModel(auth, db),
                        windowSize = calculateWindowSizeClass(activity = this)
                    )
                }

            }
        }
    }
}