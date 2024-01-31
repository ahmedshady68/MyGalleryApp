package com.shady.mygalleryapp.core.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.shady.mygalleryapp.core.navigation.destination.NavigationDestination
import com.shady.mygalleryapp.main.ui.GalleryAppState

fun NavigationDestination.buildRoute(): String =
    if (arguments.isEmpty()) {
        routeBase
    } else {
        buildString {
            append(routeBase)
            arguments.forEach { arg ->
                append("/{")
                append(arg.name)
                append("}")
            }
        }
    }

fun NavigationDestination.registerIn(appState: GalleryAppState): NavigationDestination {
    appState.registerNavigationDestination(this)
    return this
}

fun NavGraphBuilder.composable(
    destination: NavigationDestination,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = destination.buildRoute(),
        arguments = destination.arguments,
        deepLinks = destination.deepLinks,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        content = content
    )
}
fun NavController.navigate(
    destination: NavigationDestination,
    vararg args: Any,
    optionsBuilder: NavOptionsBuilder.() -> Unit = {},
) {
    navigate(
        destination.buildRouteWithArgs(*args),
        navOptions(optionsBuilder)
    )
}
fun NavigationDestination.buildRouteWithArgs(vararg args: Any): String =
    if (args.isEmpty()) {
        routeBase
    } else {
        buildString {
            append(routeBase)
            args.forEach { arg ->
                append("/")
                append(arg.toString())
            }
        }
    }