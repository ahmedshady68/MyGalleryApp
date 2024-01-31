package com.shady.mygalleryapp.feature.videos.ui

import com.shady.mygalleryapp.core.data.model.MediaStoreFile

sealed interface VideosScreenUiState {

    data object Empty : VideosScreenUiState

    data object Loading : VideosScreenUiState

    data class Videos(val files: List<MediaStoreFile>) : VideosScreenUiState

    data class Error(val thrown: Exception) : VideosScreenUiState
}