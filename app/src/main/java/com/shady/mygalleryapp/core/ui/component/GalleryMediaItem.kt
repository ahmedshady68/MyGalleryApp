package com.shady.mygalleryapp.core.ui.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.shady.mygalleryapp.R
import com.shady.mygalleryapp.core.data.model.MediaType
import com.shady.mygalleryapp.core.ui.icon.GalleryIcons


@Composable
fun GalleryMediaItem(
    uri: Uri,
    type: MediaType,
    iconPadding: Dp,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        GalleryAsyncImage(
            uri = uri,
            description = stringResource(id = R.string.image),
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            filterQuality = FilterQuality.Low,
            enableLoadingIndicator = true,
            enableErrorIndicator = true,
        )
        if (type == MediaType.Video) {
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(all = iconPadding)
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    ),
            ) {
                Icon(
                    imageVector = GalleryIcons.Video,
                    contentDescription = stringResource(id = R.string.video),
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}
