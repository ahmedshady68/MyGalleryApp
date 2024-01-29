package com.shady.mygalleryapp.feature.album.ui

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.shady.mygalleryapp.core.data.repository.MediaStoreRepository
import com.shady.mygalleryapp.core.ui.model.MediaObservingViewModel
import com.shady.mygalleryapp.core.util.coroutines.excludeCancellation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AlbumScreenViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: MediaStoreRepository,
) : MediaObservingViewModel(context) {

    private val uiStateInternal: MutableStateFlow<AlbumScreenUiState> =
        MutableStateFlow(AlbumScreenUiState.Loading)

    val uiState: StateFlow<AlbumScreenUiState>
        get() = uiStateInternal

    fun updateMedia(
        bucketId: Long = currentBucketId,
        silent: Boolean = uiState.value is AlbumScreenUiState.Album,
    ) {
        currentBucketId = bucketId
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            if (!silent) {
                uiStateInternal.value = AlbumScreenUiState.Loading
            }
            try {
                val files = withContext(Dispatchers.Default) { repository.getFiles(bucketId) }
                if (files.isNotEmpty()) {
                    uiStateInternal.value = AlbumScreenUiState.Album(
                        bucketId,
                        files
                    )
                } else {
                    uiStateInternal.value = AlbumScreenUiState.Empty
                }
            } catch (e: Exception) {
                if (!silent) {
                    excludeCancellation(e) {
                        uiStateInternal.value = AlbumScreenUiState.Error(e)
                    }
                }
            }
        }
    }

    override fun onMediaChanged() {
        updateMedia()
    }

    private var currentJob: Job? = null
    private var currentBucketId: Long = Long.MIN_VALUE
}
