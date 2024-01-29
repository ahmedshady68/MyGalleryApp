package com.shady.mygalleryapp.feature.videos.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import com.shady.mygalleryapp.R
import com.shady.mygalleryapp.core.navigation.destination.TopLevelNavigationDestination
import com.shady.mygalleryapp.core.ui.icon.GalleryIcons

object VideosNavigationDestination : TopLevelNavigationDestination {

    override val routeBase: String = "videos"

    override val arguments: List<NamedNavArgument> = emptyList()

    override val deepLinks: List<NavDeepLink> = emptyList()

    @get:StringRes
    override val titleRes: Int = R.string.videos

    override val selectedIcon: ImageVector = GalleryIcons.VideosSelected

    override val unselectedIcon: ImageVector = GalleryIcons.VideosUnselected
}
