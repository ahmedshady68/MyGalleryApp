package com.shady.mygalleryapp.feature.albums.ui

import com.shady.mygalleryapp.core.data.model.MediaStoreAlbum

sealed interface AlbumsScreenUiState {
    data object Empty: AlbumsScreenUiState

    data object Loading: AlbumsScreenUiState

    data class Albums(val Albums: List<MediaStoreAlbum>): AlbumsScreenUiState

    data class Error(val thrown: Exception): AlbumsScreenUiState
}