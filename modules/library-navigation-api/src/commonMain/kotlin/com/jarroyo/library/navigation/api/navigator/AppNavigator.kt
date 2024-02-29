package com.jarroyo.library.navigation.api.navigator

import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.navigation.NavOptions

interface AppNavigator {
    val destinations: Flow<NavigatorEvent>
    val homeDestination: String

    /**
     * Attempts to navigate up in the navigation hierarchy. Suitable for when the user presses the
     * "Up" button marked with a left (or start)-facing arrow in the upper left (or starting)
     * corner of the app UI.
     *
     * The intended behavior of Up differs from Back when the user did not reach the current
     * destination from the application's own task. e.g. if the user is viewing a document or link
     * in the current app in an activity hosted on another app's task where the user clicked the
     * link. In this case the current activity (determined by the context used to create this
     * NavController) will be finished and the user will be taken to an appropriate destination
     * in this app on its own task.
     *
     * @return true if the navigation request was successfully delivered to the View, false otherwise
     */
    fun navigateUp(): Boolean

    /**
     * Attempts to pop the navigation controller's back stack. Analogous to when the user presses the system Back button.
     *
     * @return true if the navigation request was successfully delivered to the View, false otherwise
     */
    fun navigateBack(): Boolean
    fun navigateHome(): Boolean
    fun navigateToLogin(builder: NavOptions): Boolean
    fun navigate(route: String, builder: NavOptions? = null): Boolean
}
