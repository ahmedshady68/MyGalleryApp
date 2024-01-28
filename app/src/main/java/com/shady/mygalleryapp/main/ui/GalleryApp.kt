package com.shady.mygalleryapp.main.ui


import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.shady.mygalleryapp.core.util.permissions.checkAllPermissionsGranted
import com.shady.mygalleryapp.feature.images.ui.ImagesScreen
import com.shady.mygalleryapp.feature.images.ui.ImagesScreenUiState

@Composable
fun GalleryApp(appState: GalleryAppState = rememberGalleryAppState()) {
    val appContextUpdated by rememberUpdatedState(LocalContext.current.applicationContext)
    val mediaPermissionsUpdated by rememberUpdatedState(
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
                )
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            else -> {
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
        }
    )
    var permissionsGranted by remember {
        mutableStateOf(appContextUpdated.checkAllPermissionsGranted(mediaPermissionsUpdated))
    }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        if (permissionsGranted) {
            // if Permissions Granted
            val currentDestinations = appState.topLevelNavigationDestinations
            val currentDestination = appState.currentNavigationDestination
            ImagesScreen(uiState = ImagesScreenUiState.Images(emptyList()))
        } else {
            // Permissions Not Granted
        }
    }
}
