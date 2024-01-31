package com.shady.mygalleryapp.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.shady.mygalleryapp.core.navigation.buildRoute
import com.shady.mygalleryapp.core.navigation.composable
import com.shady.mygalleryapp.core.navigation.navigate
import com.shady.mygalleryapp.core.navigation.registerIn
import com.shady.mygalleryapp.feature.album.navigation.AlbumNavigationDestination
import com.shady.mygalleryapp.feature.album.ui.AlbumRoute
import com.shady.mygalleryapp.feature.albums.navigation.AlbumsNavigationDestination
import com.shady.mygalleryapp.feature.albums.ui.AlbumsRoute
import com.shady.mygalleryapp.feature.images.navigation.ImagesNavigationDestination
import com.shady.mygalleryapp.feature.images.ui.ImagesRoute
import com.shady.mygalleryapp.feature.videos.navigation.VideosNavigationDestination
import com.shady.mygalleryapp.feature.videos.ui.VideosRoute
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
        composable(VideosNavigationDestination.registerIn(appState)) {
            VideosRoute()
        }
        composable(AlbumsNavigationDestination.registerIn(appState)) {
            AlbumsRoute(
                onAlbumClick = { _, bucket ->
                    appState.coroutineScope.launch {
                        appState.navController.navigate(
                            AlbumNavigationDestination,
                            bucket.id,
                            bucket.name
                        )
                    }
                },
            )
        }
        composable(AlbumNavigationDestination.registerIn(appState)) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getLong(
                AlbumNavigationDestination.Arguments.BUCKET_ID,
                Long.MIN_VALUE
            ) ?: Long.MIN_VALUE
            val albumName =
                backStackEntry.arguments?.getString(AlbumNavigationDestination.Arguments.BUCKET_NAME)
            AlbumRoute(
                id = albumId,
                name = albumName,
            )
        }
    }
}
