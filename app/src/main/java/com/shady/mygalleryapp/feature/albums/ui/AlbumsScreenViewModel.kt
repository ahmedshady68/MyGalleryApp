package com.shady.mygalleryapp.feature.albums.ui

import android.content.Context
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope
import com.shady.mygalleryapp.core.data.repository.MediaStoreRepository
import com.shady.mygalleryapp.core.ui.model.MediaObservingViewModel
import com.shady.mygalleryapp.core.util.coroutines.excludeCancellation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltViewModel
class AlbumsScreenViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: MediaStoreRepository,
): MediaObservingViewModel(context) {

    private val uiStateInternal: MutableStateFlow<AlbumsScreenUiState> =
        MutableStateFlow(AlbumsScreenUiState.Loading)

    val uiState: StateFlow<AlbumsScreenUiState>
        get() = uiStateInternal

    fun updateAlbums(silent: Boolean = uiState.value is AlbumsScreenUiState.Albums) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            if (!silent) {
                uiStateInternal.value = AlbumsScreenUiState.Loading
            }
            try {
                val albums = withContext(Dispatchers.Default) {
                    repository.getAlbums()
                }
                if (albums.isNotEmpty()) {
                    uiStateInternal.value = AlbumsScreenUiState.Albums(albums)
                } else {
                    uiStateInternal.value = AlbumsScreenUiState.Empty
                }
            } catch (e: Exception) {
                if (!silent) {
                    excludeCancellation(e) {
                        uiStateInternal.value = AlbumsScreenUiState.Error(e)
                    }
                }
            }
        }
    }

    override fun onMediaChanged() {
        updateAlbums()
    }

    private var currentJob: Job? = null
}
