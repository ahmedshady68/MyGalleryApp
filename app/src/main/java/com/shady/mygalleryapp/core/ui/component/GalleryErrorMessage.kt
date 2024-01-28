package com.shady.mygalleryapp.core.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.shady.mygalleryapp.R

@Composable
fun GalleryErrorMessage(
    modifier: Modifier = Modifier,
) {
    GalleryCenteredMessage(
        text = stringResource(id = R.string.unexpected_error),
        modifier = modifier,
    )
}
