package com.jarroyo.library.navigation.navigator

import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.navigation.api.navigator.NavigatorEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo

internal class AppNavigatorImpl : AppNavigator {
    // A capacity > 0 is required to not lose an event sent before the nav host starts collecting (e.g. Add account from System settings)
    private val navigationEvents = Channel<NavigatorEvent>(capacity = Channel.CONFLATED)

    override val destinations = navigationEvents.receiveAsFlow()
    override val homeDestination = "NewsFeedDestination.get()"

    /**
     * Checks the given Intent for a Navigation deep link and navigates to the deep link if present.
     * This is called automatically for you the first time you set the graph if you've passed in an
     * [Activity] as the context when constructing this NavController, but should be manually
     * called if your Activity receives new Intents in [Activity.onNewIntent].
     *
     * The types of Intents that are supported include:
     *
     * Intents created by [NavDeepLinkBuilder] or
     * [createDeepLink]. This assumes that the current graph shares
     * the same hierarchy to get to the deep linked destination as when the deep link was
     * constructed.
     * Intents that include a [data Uri][Intent.getData]. This Uri will be checked
     * against the Uri patterns in the [NavDeepLinks][NavDeepLink] added via
     * [NavDestination.addDeepLink].
     *
     * The [navigation graph][graph] should be set before calling this method.
     *
     * @param intent The Intent that may contain a valid deep link
     * @return true if the navigation request was successfully delivered to the View, false otherwise.
     */
    // override fun handleDeepLink(intent: Intent): Boolean = navigationEvents.trySend(NavigatorEvent.HandleDeepLink(intent)).isSuccess

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
    override fun navigateUp(): Boolean =
        navigationEvents.trySend(NavigatorEvent.NavigateUp).isSuccess

    /**
     * Attempts to pop the navigation controller's back stack. Analogous to when the user presses the system Back button.
     *
     * @return true if the navigation request was successfully delivered to the View, false otherwise
     */
    override fun navigateBack(): Boolean =
        navigationEvents.trySend(NavigatorEvent.NavigateBack).isSuccess

    override fun navigateHome(): Boolean = navigate(
        homeDestination,
        NavOptions(
            // Launch the scene as single top
            launchSingleTop = true,
            popUpTo = PopUpTo.First(),
        ),
    )

    override fun navigateToLogin(builder: NavOptions): Boolean =
        navigate("", builder)

    override fun navigate(route: String, builder: NavOptions?): Boolean =
        navigationEvents.trySend(NavigatorEvent.Directions(route, builder)).isSuccess
}
