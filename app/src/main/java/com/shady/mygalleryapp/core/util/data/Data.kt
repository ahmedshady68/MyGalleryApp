package com.shady.mygalleryapp.core.util.data

import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.shady.mygalleryapp.core.data.model.MediaType

fun contentUriFor(mediaType: MediaType): Uri =
    when (mediaType) {
        MediaType.Image -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }
        }

        MediaType.Video -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            }
        }
    }
