package com.shady.mygalleryapp.feature.images

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.shady.mygalleryapp.R
import com.shady.mygalleryapp.core.ui.component.GalleryCenteredMessage
import com.shady.mygalleryapp.core.ui.component.GalleryErrorMessage
import com.shady.mygalleryapp.core.ui.component.GalleryLoadingIndicator
import com.shady.mygalleryapp.core.ui.component.GalleryMediaVerticalGrid
import com.shady.mygalleryapp.core.ui.component.GalleryTopAppBar

@Composable
fun ImagesScreen(
    uiState: ImagesScreenUiState,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            ImagesScreenUiState.Empty -> {
                GalleryCenteredMessage(
                    text = stringResource(id = R.string.no_images_found),
                    modifier = Modifier.matchParentSize(),
                )
            }

            ImagesScreenUiState.Loading -> {
                GalleryLoadingIndicator(modifier = Modifier.matchParentSize())
            }

            is ImagesScreenUiState.Images -> {
                GalleryMediaVerticalGrid(
                    files = uiState.files,
                    modifier = Modifier.matchParentSize(),
                    overlayTop = true,
                    overlayBottom = true,
                )
            }

            is ImagesScreenUiState.Error -> {
                GalleryErrorMessage(
                    modifier = Modifier.matchParentSize()
                )
            }
        }
        GalleryTopAppBar(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(),
            text = "Gallery",
            backgroundColor = MaterialTheme.colorScheme.background.copy(alpha = 0.75f),
        )
    }
}
