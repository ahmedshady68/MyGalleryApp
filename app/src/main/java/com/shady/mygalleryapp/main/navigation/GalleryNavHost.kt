package com.shady.mygalleryapp.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.shady.mygalleryapp.core.navigation.buildRoute
import com.shady.mygalleryapp.core.navigation.composable
import com.shady.mygalleryapp.core.navigation.navigate
import com.shady.mygalleryapp.core.navigation.registerIn
import com.shady.mygalleryapp.feature.albums.navigation.AlbumsNavigationDestination
import com.shady.mygalleryapp.feature.albums.ui.AlbumsRoute
import com.shady.mygalleryapp.feature.images.navigation.ImagesNavigationDestination
import com.shady.mygalleryapp.feature.images.ui.ImagesRoute
import com.shady.mygalleryapp.main.ui.GalleryAppState
import kotlinx.coroutines.launch


@Composable
fun GalleryNavHost(
    appState: GalleryAppState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = ImagesNavigationDestination.buildRoute()
    ) {
        composable(ImagesNavigationDestination.registerIn(appState)) {
            ImagesRoute()
        }
        composable(AlbumsNavigationDestination.registerIn(appState)) {
            AlbumsRoute(
                onAlbumClick = { _, bucket ->
                    appState.coroutineScope.launch {
                        appState.navController.navigate(
                            AlbumsNavigationDestination,
                            bucket.id,
                            bucket.name
                        )
                    }
                },
            )
        }
    }
}
