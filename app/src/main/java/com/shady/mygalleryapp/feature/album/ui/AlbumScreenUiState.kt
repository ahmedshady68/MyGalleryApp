package com.shady.mygalleryapp.feature.album.ui

import com.shady.mygalleryapp.core.data.model.MediaStoreFile

sealed interface AlbumScreenUiState {
    data object Empty : AlbumScreenUiState

    data object Loading : AlbumScreenUiState

    data class Album(
        val id: Long,
        val files: List<MediaStoreFile>,
    ) : AlbumScreenUiState

    data class Error(val thrown: Exception) : AlbumScreenUiState
}