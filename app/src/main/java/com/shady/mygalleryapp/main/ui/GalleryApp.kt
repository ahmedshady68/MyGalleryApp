package com.shady.mygalleryapp.main.ui


import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.shady.mygalleryapp.R
import com.shady.mygalleryapp.core.navigation.destination.TopLevelNavigationDestination
import com.shady.mygalleryapp.core.ui.component.GalleryMessage
import com.shady.mygalleryapp.core.ui.component.GalleryOutlinedButton
import com.shady.mygalleryapp.core.ui.effect.LifecycleEventEffect
import com.shady.mygalleryapp.core.util.permissions.checkAllPermissionsGranted
import com.shady.mygalleryapp.main.navigation.GalleryNavHost
import kotlinx.coroutines.launch

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
            Box(modifier = Modifier.fillMaxSize()) {
                GalleryNavHost(
                    appState = appState,
                    modifier = Modifier.matchParentSize()
                )
                if (currentDestination is TopLevelNavigationDestination) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.75f),
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .height(64.dp)
                    ) {
                        currentDestinations.forEach { destination ->
                            val selected = destination == currentDestination
                            NavigationBarItem(
                                selected = selected,
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                                    indicatorColor = MaterialTheme.colorScheme.primary
                                ),
                                onClick = {
                                    appState.coroutineScope.launch {
                                        appState.navigateToTopLevelDestination(destination)
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) {
                                            destination.selectedIcon
                                        } else {
                                            destination.unselectedIcon
                                        },
                                        contentDescription = stringResource(id = destination.titleRes)
                                    )
                                },
                            )
                        }
                    }
                }
            }
        } else {
            // Permissions Not Granted
            val message = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                stringResource(id = R.string.no_images_permission)
            } else {
                stringResource(id = R.string.no_storage_permission)
            }
            val mediaPermissionsLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { grantResult ->
                permissionsGranted = checkAllPermissionsGranted(grantResult)
            }
            val settingsLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) {
                permissionsGranted =
                    appContextUpdated.checkAllPermissionsGranted(mediaPermissionsUpdated)
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GalleryMessage(
                    text = message,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                GalleryOutlinedButton(text = stringResource(id = R.string.open_settings)) {
                    appState.coroutineScope.launch {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.fromParts(
                            "package",
                            appContextUpdated.packageName,
                            null
                        )
                        settingsLauncher.launch(intent)
                    }
                }
            }
            LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
                permissionsGranted =
                    appContextUpdated.checkAllPermissionsGranted(mediaPermissionsUpdated)
            }
            LaunchedEffect(Unit) {
                appState.coroutineScope.launch {
                    mediaPermissionsLauncher.launch(mediaPermissionsUpdated)
                }
            }
        }
    }
}
