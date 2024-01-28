package com.shady.mygalleryapp.core.navigation.destination

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

interface TopLevelNavigationDestination : NavigationDestination {

    @get:StringRes
    val titleRes: Int

    val selectedIcon: ImageVector

    val unselectedIcon: ImageVector
}