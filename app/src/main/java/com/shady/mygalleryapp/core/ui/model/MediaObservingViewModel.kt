package com.shady.mygalleryapp.core.ui.model

import android.annotation.SuppressLint
import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import com.shady.mygalleryapp.core.data.model.MediaType
import com.shady.mygalleryapp.core.util.data.contentUriFor

abstract class MediaObservingViewModel(context: Context) : ViewModel() {

    @MainThread
    abstract fun onMediaChanged()

    @CallSuper
    override fun onCleared() {
        with(appContext.contentResolver) {
            unregisterContentObserver(imagesObserver)
            unregisterContentObserver(videoObserver)
        }
    }

    @SuppressLint("StaticFieldLeak")
    private val appContext: Context = context.applicationContext

    private val imagesObserver: ContentObserver = Observer()
    private val videoObserver: ContentObserver = Observer()

    init {
        with(appContext.contentResolver) {
            registerContentObserver(
                contentUriFor(MediaType.Image),
                true,
                imagesObserver
            )
            registerContentObserver(
                contentUriFor(MediaType.Video),
                true,
                videoObserver
            )
        }
    }

    private inner class Observer : ContentObserver(Handler((Looper.getMainLooper()))) {
        override fun onChange(selfChange: Boolean) {
            onMediaChanged()
        }
    }
}