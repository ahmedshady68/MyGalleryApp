package com.shady.mygalleryapp.feature.images.ui

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
class ImagesScreenViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: MediaStoreRepository,
) : MediaObservingViewModel(context) {

    private val uiStateInternal: MutableStateFlow<ImagesScreenUiState> =
        MutableStateFlow(ImagesScreenUiState.Loading)

    val uiState: StateFlow<ImagesScreenUiState>
        get() = uiStateInternal

    fun updateMedia(silent: Boolean = uiState.value is ImagesScreenUiState.Images) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            if (!silent) {
                uiStateInternal.value = ImagesScreenUiState.Loading
            }
            try {
                val files = withContext(Dispatchers.Default) { repository.getAllImages() }
                if (files.isNotEmpty()) {
                    uiStateInternal.value = ImagesScreenUiState.Images(files)
                } else {
                    uiStateInternal.value = ImagesScreenUiState.Empty
                }
            } catch (e: Exception) {
                if (!silent) {
                    excludeCancellation(e) {
                        uiStateInternal.value = ImagesScreenUiState.Error(e)
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