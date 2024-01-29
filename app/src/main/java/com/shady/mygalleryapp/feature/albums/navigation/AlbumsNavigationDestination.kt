package com.shady.mygalleryapp.feature.albums.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import com.shady.mygalleryapp.R
import com.shady.mygalleryapp.core.navigation.destination.TopLevelNavigationDestination
import com.shady.mygalleryapp.core.ui.icon.GalleryIcons

object AlbumsNavigationDestination: TopLevelNavigationDestination {

    override val routeBase: String = "albums"

    override val arguments: List<NamedNavArgument> = emptyList()

    override val deepLinks: List<NavDeepLink> = emptyList()

    @get:StringRes
    override val titleRes: Int = R.string.albums

    override val selectedIcon: ImageVector = GalleryIcons.AlbumsSelected

    override val unselectedIcon: ImageVector = GalleryIcons.AlbumsUnselected
}
