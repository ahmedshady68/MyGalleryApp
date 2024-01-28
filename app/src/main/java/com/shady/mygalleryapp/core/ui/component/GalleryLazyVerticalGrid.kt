package com.shady.mygalleryapp.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun GalleryLazyVerticalGrid(
    modifier: Modifier = Modifier,
    overlayTop: Boolean = false,
    overlayBottom: Boolean = false,
    content: LazyGridScope.() -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 96.dp),
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 8.dp,
            top = if (overlayTop) 64.dp else 8.dp,
            end = 8.dp,
            bottom = if (overlayBottom) 64.dp else 8.dp,
        ),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        content = content
    )
}
