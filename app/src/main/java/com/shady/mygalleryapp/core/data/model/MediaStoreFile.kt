package com.shady.mygalleryapp.core.data.model

import android.net.Uri

data class MediaStoreFile(
    val id: Long,
    val albumId: Long,
    val dateAdded: Long,
    val mediaType: MediaType,
    val mimeType: String,
    val uri: Uri,
)
