package com.shady.mygalleryapp.feature.albums.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shady.mygalleryapp.R
import com.shady.mygalleryapp.core.data.model.MediaStoreAlbum
import com.shady.mygalleryapp.core.ui.component.GalleryAsyncImage
import com.shady.mygalleryapp.core.ui.component.GalleryCenteredMessage
import com.shady.mygalleryapp.core.ui.component.GalleryErrorMessage
import com.shady.mygalleryapp.core.ui.component.GalleryLazyVerticalGrid
import com.shady.mygalleryapp.core.ui.component.GalleryTopAppBar
import com.shady.mygalleryapp.feature.albums.navigation.AlbumsNavigationDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun AlbumsRoute(
    onAlbumClick: (index: Int, album: MediaStoreAlbum) -> Unit,
    viewModel: AlbumsScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(
        viewModel,
        coroutineScope,
    ) {
        coroutineScope.launch {
            viewModel.updateAlbums()
        }
    }
    AlbumsScreen(
        uiState = uiState,
        coroutineScope = coroutineScope,
        onAlbumClick = onAlbumClick,
    )
}

@Composable
fun AlbumsScreen(
    uiState: AlbumsScreenUiState,
    coroutineScope: CoroutineScope,
    onAlbumClick: (index: Int, album: MediaStoreAlbum) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            AlbumsScreenUiState.Empty -> {
                GalleryCenteredMessage(
                    text = stringResource(id = R.string.no_albums_found),
                    modifier = Modifier.matchParentSize(),
                )
            }

            AlbumsScreenUiState.Loading -> {
                // GalleryLoadingIndicator(modifier = Modifier.matchParentSize())
            }

            is AlbumsScreenUiState.Albums -> {
                AlbumsScreenLayout(
                    albums = uiState.Albums,
                    onAlbumClick = onAlbumClick,
                    coroutineScope = coroutineScope,
                    modifier = Modifier.matchParentSize(),
                )
            }

            is AlbumsScreenUiState.Error -> {
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
            text = stringResource(id = AlbumsNavigationDestination.titleRes),
            backgroundColor = MaterialTheme.colorScheme.background.copy(alpha = 0.75f),
        )
    }
}

@Composable
private fun AlbumsScreenLayout(
    albums: List<MediaStoreAlbum>,
    onAlbumClick: (index: Int, album: MediaStoreAlbum) -> Unit,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
) {
    val albumsUpdated by rememberUpdatedState(albums)
    val onAlbumClickUpdated by rememberUpdatedState(onAlbumClick)
    GalleryLazyVerticalGrid(
        modifier = modifier,
        overlayTop = true,
        overlayBottom = true,
    ) {
        items(
            count = albumsUpdated.size,
            key = { index -> albumsUpdated[index].id },
        ) { index ->
            val item = albumsUpdated[index]
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .clickable {
                        coroutineScope.launch {
                            onAlbumClickUpdated(
                                index,
                                item,
                            )
                        }
                    },
            ) {
                GalleryAsyncImage(
                    uri = item.coverUri,
                    description = stringResource(id = R.string.album_cover),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(ratio = 1f)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(8.dp),
                        ),
                    contentScale = ContentScale.Crop,
                    enableLoadingIndicator = true,
                    enableErrorIndicator = true,
                )
                Text(
                    text = item.name,
                    modifier = Modifier.padding(
                        start = 4.dp,
                        top = 4.dp,
                        end = 4.dp,
                        bottom = 0.dp,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    text = item.size.toString(),
                    modifier = Modifier.padding(
                        start = 4.dp,
                        top = 0.dp,
                        end = 4.dp,
                        bottom = 4.dp,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontSize = 14.sp,
                )
            }
        }
    }
}
