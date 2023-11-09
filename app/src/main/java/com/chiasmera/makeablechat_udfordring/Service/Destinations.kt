package com.chiasmera.makeablechat_udfordring.Service

import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Contains the navigation information for the app.
 */
class Destinations {

    /**
     * Contract that all destinations in the app must implement
     */
    interface Destination {
        val route: String

    }

    /**
     * Destination for the Sign Up view
     */
    object SignUpDestination : Destination {
        override val route = "signUp"

    }

    /**
     * Destination for the logIn view
     */
    object LogInDestination : Destination {
        override val route = "logIn"
    }

    /**
     * Destination for the startScreen view
     */
    object StartScreenDestination : Destination {
        override val route = "startScreen"
    }

    /**
     * Destination for the conversationCreation view
     */
    object ConversationCreationDestination : Destination {
        override val route = "conversationCreation"
    }

    /**
     * Destination for the conversation view
     */
    object ConversationDestination : Destination {
        override val route = "conversation"
        val argName = "convID"
        val routeWithArgs = "${route}/{${argName}}"
        val arguments = listOf(navArgument(argName) { type = NavType.StringType })

    }

}