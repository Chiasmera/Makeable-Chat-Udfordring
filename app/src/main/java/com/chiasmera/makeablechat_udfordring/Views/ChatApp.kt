package com.chiasmera.makeablechat_udfordring.Views

import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chiasmera.makeablechat_udfordring.Service.DatabaseService
import com.chiasmera.makeablechat_udfordring.Service.Destinations
import com.chiasmera.makeablechat_udfordring.Viewmodels.ConversationViewModel
import com.chiasmera.makeablechat_udfordring.Viewmodels.LoggedInState
import com.chiasmera.makeablechat_udfordring.Viewmodels.UserViewModel
import com.chiasmera.makeablechat_udfordring.Views.authentication.MainScreenView
import com.chiasmera.makeablechat_udfordring.Views.authentication.SignUpView
import com.chiasmera.makeablechat_udfordring.Views.conversation.ConversationView
import com.chiasmera.makeablechat_udfordring.Views.conversationOverview.ConversationCreationView

@Composable
fun ChatApp(
    navController: NavHostController,
    db: DatabaseService,
    userViewModel: UserViewModel,
    windowSize: WindowSizeClass
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.StartScreenDestination.route,
    ) {
        composable(route = Destinations.StartScreenDestination.route) {
            MainScreenView(
                conversations = userViewModel.conversations,
                loggedInState = userViewModel.user,
                onNavigateToSignUp = { navController.navigate(Destinations.SignUpDestination.route) },
                onLogin = { email: String, password: String ->
                    userViewModel.login(
                        email, password
                    )
                },
                onNavigateToConversation = { conversation ->
                    navController.navigate("${Destinations.ConversationDestination.route}/${conversation.id}")
                },
                onNewConversation = { navController.navigate(Destinations.ConversationCreationDestination.route) }
            )
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
                val argumentID =
                    NavBackStackEntry.arguments?.getString(Destinations.ConversationDestination.argName)
                val user = userViewModel.user as LoggedInState.LoggedIn
                val conversation = userViewModel.conversations.find { c -> c.id == argumentID }
                if (conversation != null) {
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

        composable(route = Destinations.ConversationCreationDestination.route) {
            if (userViewModel.user is LoggedInState.LoggedIn) {
                val user = userViewModel.user as LoggedInState.LoggedIn

                ConversationCreationView(
                    currentUser = user.user,
                    users = userViewModel.users,
                    onNewConversation = { selectedUsers ->
                        //Create new conversation in database with given participants
                        val conversation = userViewModel.createConversation(selectedUsers)
                        //navigate to conversation
                        navController.navigate("${Destinations.ConversationDestination.route}/${conversation.id}")
                    }
                )
            }
        }
    }
}