package com.shady.mygalleryapp.core.data.model

import android.net.Uri

data class MediaStoreAlbum(
    val id: Long,
    val name: String,
    val size: Int,
    val coverUri: Uri,
    val coverDateAdded: Long,
)
