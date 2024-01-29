package com.shady.mygalleryapp.feature.album.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.shady.mygalleryapp.core.navigation.destination.NavigationDestination

object AlbumNavigationDestination: NavigationDestination {

    override val routeBase: String = "bucket"

    override val arguments: List<NamedNavArgument> =
        listOf(navArgument(name = Arguments.BUCKET_ID) { type = NavType.LongType },
            navArgument(name = Arguments.BUCKET_NAME) { type = NavType.StringType })

    override val deepLinks: List<NavDeepLink> = emptyList()

    object Arguments {

        const val BUCKET_ID = "id"
        const val BUCKET_NAME = "name"
    }
}
