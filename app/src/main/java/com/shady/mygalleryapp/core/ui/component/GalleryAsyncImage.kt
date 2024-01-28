package com.shady.mygalleryapp.core.ui.component

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.shady.mygalleryapp.R
import com.shady.mygalleryapp.core.ui.icon.GalleryIcons


@Composable
fun GalleryAsyncImage(
    uri: Uri,
    description: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    filterQuality: FilterQuality = FilterQuality.Low,
    enableLoadingIndicator: Boolean = true,
    enableErrorIndicator: Boolean = true,
) {
    SubcomposeAsyncImage(
        model = uri,
        contentDescription = description,
        modifier = modifier,
        contentScale = contentScale,
        filterQuality = filterQuality,
        loading = {
            if (enableLoadingIndicator) {
                StateIcon(
                    icon = GalleryIcons.ImageLoading,
                    description = stringResource(id = R.string.image_loading),
                    modifier = Modifier.fillMaxSize(),
                )
            }
        },
        error = {
            if (enableErrorIndicator) {
                StateIcon(
                    icon = GalleryIcons.ImageError,
                    description = stringResource(id = R.string.image_error),
                    modifier = Modifier.fillMaxSize(),
                )
            }
        },
    )
}

@Composable
private fun StateIcon(
    icon: ImageVector,
    description: String,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}
