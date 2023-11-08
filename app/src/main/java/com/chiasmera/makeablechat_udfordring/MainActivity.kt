package com.chiasmera.makeablechat_udfordring

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chiasmera.makeablechat_udfordring.Model.Message
import com.chiasmera.makeablechat_udfordring.Model.User
import com.chiasmera.makeablechat_udfordring.Service.Destinations
import com.chiasmera.makeablechat_udfordring.Service.FirebaseAuthService
import com.chiasmera.makeablechat_udfordring.Service.FirestoreDatabaseService
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
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val userViewModel = UserViewModel()

                    NavHost(
                        navController = navController,
                        startDestination = Destinations.StartScreenDestination.route,
                    ) {
                        composable(route= Destinations.StartScreenDestination.route) {
                            StartScreenView(
                                loggedInState = userViewModel.user,
                                onNavigateToSignUp = { navController.navigate(Destinations.SignUpDestination.route) },
                                onLogin = { email : String, password : String -> userViewModel.login(email, password) }
                            )
                        }

//                        composable(route = Destinations.LogInDestination.route) {
//                            LogInView(
//                                onNavigateToSignUp = { navController.navigate(Destinations.SignUpDestination.route) },
//                                onLogIn = { navController.navigate(Destinations.ConversationDestination.route) }
//                            )
//                        }

                        composable(route = Destinations.SignUpDestination.route) {
                            SignUpView(
                                onNavigateToLogIn = { navController.navigate(Destinations.LogInDestination.route) },
                                onSignUp = { email : String, password : String, userName : String ->
                                    userViewModel.signUp(email, password, userName)
                                    //Fetch user conversations, and navigate to overview of conversations
                                    navController.navigate(Destinations.ConversationDestination.route)
                                }
                            )
                        }

                        composable(
                            route = Destinations.ConversationDestination.route
                        ) {
                            val currentUser = User("1", "mig")
                            val otherUser = User ("2", "dudebro")
                            ConversationView(messages = listOf(
                                Message("Hej", LocalDateTime.now(), currentUser),
                                Message("Hej selv", LocalDateTime.now(), otherUser),
                                Message("nej tak", LocalDateTime.now(),currentUser ),
                                Message("farvel", LocalDateTime.now(), currentUser),
                                Message("okay", LocalDateTime.now(), otherUser)
                            ),
                                currentUser = currentUser
                            )

                        }
                    }

                }
            }
        }
    }
}