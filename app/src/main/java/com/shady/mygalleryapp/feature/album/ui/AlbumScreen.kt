package com.shady.mygalleryapp.feature.album.ui

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
import kotlinx.coroutines.launch


@Composable
fun AlbumRoute(
    id: Long,
    name: String?,
    viewModel: AlbumScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(
        id,
        viewModel,
        coroutineScope,
    ) {
        coroutineScope.launch {
            viewModel.updateMedia(id)
        }
    }
    AlbumScreen(
        name = name,
        uiState = uiState,
    )
}

@Composable
fun AlbumScreen(
    name: String?,
    uiState: AlbumScreenUiState,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            AlbumScreenUiState.Empty -> {
                GalleryCenteredMessage(
                    text = stringResource(id = R.string.no_images_found),
                    modifier = Modifier.matchParentSize(),
                )
            }

            AlbumScreenUiState.Loading -> {
                // GalleryLoadingIndicator(modifier = Modifier.matchParentSize())
            }

            is AlbumScreenUiState.Album -> {
                GalleryMediaVerticalGrid(
                    files = uiState.files,
                    modifier = Modifier.matchParentSize(),
                    overlayTop = true,
                    overlayBottom = false,
                )
            }

            is AlbumScreenUiState.Error -> {
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
            text = name,
            backgroundColor = MaterialTheme.colorScheme.background.copy(alpha = 0.75f),
        )
    }
}
