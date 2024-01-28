package com.shady.mygalleryapp.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shady.mygalleryapp.core.navigation.destination.NavigationDestination
import com.shady.mygalleryapp.core.navigation.destination.TopLevelNavigationDestination
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberSketchesAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): SketchesAppState =
    remember(
        navController,
        coroutineScope
    ) {
        SketchesAppState(
            navController,
            coroutineScope
        )
    }

@Stable
class SketchesAppState(
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


    private val navigationDestinationsInternal: MutableMap<String, NavigationDestination> =
        HashMap()

    private val topLevelNavigationDestinationsInternal: MutableList<TopLevelNavigationDestination> =
        ArrayList()
}
