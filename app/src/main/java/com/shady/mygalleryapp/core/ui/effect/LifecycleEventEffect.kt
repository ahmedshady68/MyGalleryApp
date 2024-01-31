package com.shady.mygalleryapp.core.ui.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
inline fun LifecycleEventEffect(
    event: Lifecycle.Event,
    crossinline block: () -> Unit,
) {
    LifecycleEventEffect { currentEvent ->
        if (currentEvent == event) {
            block()
        }
    }
}

@Composable
inline fun LifecycleEventEffect(crossinline block: (event: Lifecycle.Event) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
            block(event)
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
