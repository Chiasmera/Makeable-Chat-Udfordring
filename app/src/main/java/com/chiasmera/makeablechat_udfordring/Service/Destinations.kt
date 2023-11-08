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
     * Destination for the SignIn view
     */
    object LogInDestination : Destination {
        override val route = "signIn"
    }

    /**
     * Destination for the SignIn view
     */
    object StartScreenDestination : Destination {
        override val route = "startScreen"
    }

    /**
     * Destination for the conversation view
     */
    object ConversationDestination : Destination {
        override val route = "conversation"
    }

}