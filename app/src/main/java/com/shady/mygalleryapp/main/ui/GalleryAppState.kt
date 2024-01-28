package com.shady.mygalleryapp.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shady.mygalleryapp.core.navigation.buildRoute
import com.shady.mygalleryapp.core.navigation.destination.NavigationDestination
import com.shady.mygalleryapp.core.navigation.destination.TopLevelNavigationDestination
import com.shady.mygalleryapp.core.navigation.navigate
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberGalleryAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): GalleryAppState =
    remember(
        navController,
        coroutineScope
    ) {
        GalleryAppState(
            navController,
            coroutineScope
        )
    }

@Stable
class GalleryAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
) {

    val currentNavigationDestination: NavigationDestination?
        @Composable get() {
            val route = navController.currentBackStackEntryAsState().value?.destination?.route
                ?: return null
            return navigationDestinationsInternal[route]
        }

    val topLevelNavigationDestinations: List<TopLevelNavigationDestination>
        get() = topLevelNavigationDestinationsInternal

    fun navigateToTopLevelDestination(destination: TopLevelNavigationDestination) {
        navController.navigate(destination) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun registerNavigationDestination(destination: NavigationDestination) {
        navigationDestinationsInternal[destination.buildRoute()] = destination
        if (destination is TopLevelNavigationDestination) {
            topLevelNavigationDestinationsInternal += destination
        }
    }

    private val navigationDestinationsInternal: MutableMap<String, NavigationDestination> =
        HashMap()

    private val topLevelNavigationDestinationsInternal: MutableList<TopLevelNavigationDestination> =
        ArrayList()
}
