package com.shady.mygalleryapp.feature.images

import com.shady.mygalleryapp.core.data.model.MediaStoreFile

sealed interface ImagesScreenUiState {

    data object Empty : ImagesScreenUiState

    data object Loading : ImagesScreenUiState

    data class Images(val files: List<MediaStoreFile>) : ImagesScreenUiState

    data class Error(val thrown: Exception) : ImagesScreenUiState
}