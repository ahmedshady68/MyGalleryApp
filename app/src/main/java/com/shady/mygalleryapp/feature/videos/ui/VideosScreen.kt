package com.shady.mygalleryapp.feature.videos.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shady.mygalleryapp.R
import com.shady.mygalleryapp.core.ui.component.GalleryCenteredMessage
import com.shady.mygalleryapp.core.ui.component.GalleryErrorMessage
import com.shady.mygalleryapp.core.ui.component.GalleryLoadingIndicator
import com.shady.mygalleryapp.core.ui.component.GalleryMediaVerticalGrid
import com.shady.mygalleryapp.core.ui.component.GalleryTopAppBar
import com.shady.mygalleryapp.feature.videos.navigation.VideosNavigationDestination

@Composable
fun VideosRoute(
    viewModel: VideosScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(
        viewModel,
        coroutineScope,
    ) {
        viewModel.updateMedia()
    }
    ImagesScreen(
        uiState = uiState,
    )
}

@Composable
fun ImagesScreen(
    uiState: VideosScreenUiState,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            VideosScreenUiState.Empty -> {
                GalleryCenteredMessage(
                    text = stringResource(id = R.string.no_images_found),
                    modifier = Modifier.matchParentSize(),
                )
            }

            VideosScreenUiState.Loading -> {
                GalleryLoadingIndicator(modifier = Modifier.matchParentSize())
            }

            is VideosScreenUiState.Videos -> {
                GalleryMediaVerticalGrid(
                    files = uiState.files,
                    overlayTop = true,
                    overlayBottom = true,
                )
            }

            is VideosScreenUiState.Error -> {
                GalleryErrorMessage(
                    thrown = uiState.thrown,
                    modifier = Modifier.matchParentSize()
                )
            }
        }
        GalleryTopAppBar(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(),
            text = stringResource(id = VideosNavigationDestination.titleRes),
            backgroundColor = MaterialTheme.colorScheme.background.copy(alpha = 0.75f),
        )
    }
}
