package com.shady.mygalleryapp.core.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.shady.mygalleryapp.core.data.model.MediaStoreFile


@Composable
fun GalleryMediaVerticalGrid(
    files: List<MediaStoreFile>,
    overlayTop: Boolean = false,
    overlayBottom: Boolean = false,
) {
    val filesUpdated by rememberUpdatedState(files)
    GalleryLazyVerticalGrid(
        overlayTop = overlayTop,
        overlayBottom = overlayBottom,
    ) {
        items(
            count = filesUpdated.size,
            key = { index -> filesUpdated[index].id },
        ) { index ->
            val file = filesUpdated[index]
            GalleryMediaItem(
                uri = file.uri,
                type = file.mediaType,
                iconPadding = 4.dp,
                modifier = Modifier
                    .aspectRatio(ratio = 1f)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}