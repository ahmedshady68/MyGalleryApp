package com.shady.mygalleryapp.core.navigation.destination

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

interface NavigationDestination {
    val routeBase: String

    val arguments: List<NamedNavArgument>

    val deepLinks: List<NavDeepLink>
}