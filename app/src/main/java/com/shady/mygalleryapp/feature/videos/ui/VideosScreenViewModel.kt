package com.shady.mygalleryapp.feature.videos.ui

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
class VideosScreenViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: MediaStoreRepository,
) : MediaObservingViewModel(context) {

    private val uiStateInternal: MutableStateFlow<VideosScreenUiState> =
        MutableStateFlow(VideosScreenUiState.Loading)

    val uiState: StateFlow<VideosScreenUiState>
        get() = uiStateInternal

    fun updateMedia(silent: Boolean = uiState.value is VideosScreenUiState.Videos) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            if (!silent) {
                uiStateInternal.value = VideosScreenUiState.Loading
            }
            try {
                val files = withContext(Dispatchers.Default) { repository.getAllVideos() }
                if (files.isNotEmpty()) {
                    uiStateInternal.value = VideosScreenUiState.Videos(files)
                } else {
                    uiStateInternal.value = VideosScreenUiState.Empty
                }
            } catch (e: Exception) {
                if (!silent) {
                    excludeCancellation(e) {
                        uiStateInternal.value = VideosScreenUiState.Error(e)
                    }
                }
            }
        }
    }

    override fun onMediaChanged() {
        updateMedia()
    }

    private var currentJob: Job? = null
}