package com.shady.mygalleryapp.feature.images.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import com.shady.mygalleryapp.R
import com.shady.mygalleryapp.core.navigation.destination.TopLevelNavigationDestination
import com.shady.mygalleryapp.core.ui.icon.GalleryIcons

object ImagesNavigationDestination : TopLevelNavigationDestination {

    override val routeBase: String = "images"

    override val arguments: List<NamedNavArgument> = emptyList()

    override val deepLinks: List<NavDeepLink> = emptyList()

    @get:StringRes
    override val titleRes: Int = R.string.images

    override val selectedIcon: ImageVector = GalleryIcons.ImagesSelected

    override val unselectedIcon: ImageVector = GalleryIcons.ImagesUnselected
}
