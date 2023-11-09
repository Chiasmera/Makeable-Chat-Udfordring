package com.chiasmera.makeablechat_udfordring

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chiasmera.makeablechat_udfordring.Model.Message
import com.chiasmera.makeablechat_udfordring.Model.User
import com.chiasmera.makeablechat_udfordring.Service.Destinations
import com.chiasmera.makeablechat_udfordring.Service.FirebaseAuthService
import com.chiasmera.makeablechat_udfordring.Service.FirestoreDatabaseService
import com.chiasmera.makeablechat_udfordring.Viewmodels.ConversationViewModel
import com.chiasmera.makeablechat_udfordring.Viewmodels.LoggedInState
import com.chiasmera.makeablechat_udfordring.Viewmodels.UserViewModel
import com.chiasmera.makeablechat_udfordring.Views.authentication.LogInView
import com.chiasmera.makeablechat_udfordring.Views.authentication.SignUpView
import com.chiasmera.makeablechat_udfordring.Views.authentication.StartScreenView
import com.chiasmera.makeablechat_udfordring.Views.conversation.ConversationView
import com.chiasmera.makeablechat_udfordring.ui.theme.MakeableChatUdfordringTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MakeableChatUdfordringTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val db = FirestoreDatabaseService()
                    val auth = FirebaseAuthService()
                    val userViewModel = UserViewModel(auth, db)

                    NavHost(
                        navController = navController,
                        startDestination = Destinations.StartScreenDestination.route,
                    ) {
                        composable(route = Destinations.StartScreenDestination.route) {
                            StartScreenView(conversations = userViewModel.conversations,
                                loggedInState = userViewModel.user,
                                onNavigateToSignUp = { navController.navigate(Destinations.SignUpDestination.route) },
                                onLogin = { email: String, password: String ->
                                    userViewModel.login(
                                        email, password
                                    )
                                },
                                onNavigateToConversation = { conversation ->
                                        navController.navigate("${Destinations.ConversationDestination.route}/${conversation.id}")
                                })
                        }

                        composable(route = Destinations.SignUpDestination.route) {
                            SignUpView(onNavigateToLogIn = { navController.navigate(Destinations.LogInDestination.route) },
                                onSignUp = { email: String, password: String, userName: String ->
                                    userViewModel.signUp(email, password, userName)
                                    navController.navigate(Destinations.StartScreenDestination.route)
                                })
                        }

                        composable(
                            route = Destinations.ConversationDestination.routeWithArgs,
                            arguments = Destinations.ConversationDestination.arguments
                        ) { NavBackStackEntry ->
                            if (userViewModel.user is LoggedInState.LoggedIn) {
                                val argumentID = NavBackStackEntry.arguments?.getString(Destinations.ConversationDestination.argName)
                                val user = userViewModel.user as LoggedInState.LoggedIn
                                val conversation = userViewModel.conversations.find { c -> c.id == argumentID }
                                if(conversation != null) {
                                    ConversationView(
                                        conversationViewModel = ConversationViewModel(user.user, conversation, db)
                                    )
                                } else {
                                    Text("Error")
                                }
                            } else {
                                Text("Error")
                            }
                        }
                    }
                }

            }
        }
    }
}