package com.shady.mygalleryapp.core.data.repository

import com.shady.mygalleryapp.core.data.model.MediaStoreAlbum
import com.shady.mygalleryapp.core.data.model.MediaStoreFile

interface MediaStoreRepository {
    suspend fun getFiles(bucketId: Long = Long.MIN_VALUE): List<MediaStoreFile>

    suspend fun getAlbums(): List<MediaStoreAlbum>
}