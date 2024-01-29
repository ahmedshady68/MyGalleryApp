package com.shady.mygalleryapp.core.data.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.shady.mygalleryapp.core.data.model.MediaStoreAlbum
import com.shady.mygalleryapp.core.data.model.MediaStoreFile
import com.shady.mygalleryapp.core.data.model.MediaType
import com.shady.mygalleryapp.core.util.data.contentUriFor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaStoreRepositoryImpl(private val context: Context) : MediaStoreRepository {

    private suspend fun collectFiles(
        mediaType: MediaType,
        bucketId: Long,
    ): List<MediaStoreFile> {
        val contentUri = contentUriFor(mediaType)
        val cursor = withContext(Dispatchers.IO) {
            context.contentResolver.query(
                contentUri,
                arrayOf(
                    MediaStore.MediaColumns._ID,
                    MediaStore.MediaColumns.DISPLAY_NAME,
                    MediaStore.MediaColumns.BUCKET_ID,
                    MediaStore.MediaColumns.BUCKET_DISPLAY_NAME,
                    MediaStore.MediaColumns.DATE_ADDED,
                    MediaStore.MediaColumns.MIME_TYPE,
                ),
                if (bucketId != Long.MIN_VALUE) {
                    "${MediaStore.MediaColumns.BUCKET_ID}=?"
                } else {
                    null
                },
                if (bucketId != Long.MIN_VALUE) {
                    arrayOf(bucketId.toString())
                } else {
                    null
                },
                null
            )
        } ?: return emptyList()
        cursor.use { c ->
            val files = ArrayList<MediaStoreFile>(c.count)
            val idColumn = c.getColumnIndex(MediaStore.MediaColumns._ID)
            val bucketIdColumn = c.getColumnIndex(MediaStore.MediaColumns.BUCKET_ID)
            val dateAddedColumn = c.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED)
            val mimeTypeColumn = c.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)
            while (c.moveToNext()) {
                val id = c.getLong(idColumn)
                files += MediaStoreFile(
                    id = id,
                    bucketId = c.getLong(bucketIdColumn),
                    dateAdded = c.getLong(dateAddedColumn) * 1000L,
                    mediaType = mediaType,
                    mimeType = c.getString(mimeTypeColumn) ?: when (mediaType) {
                        MediaType.Image -> "image/*"
                        MediaType.Video -> "video/*"
                    },
                    uri = ContentUris.withAppendedId(
                        contentUri,
                        id
                    )
                )
            }
            return files
        }
    }

    override suspend fun getFiles(bucketId: Long): List<MediaStoreFile> {
        val imageFiles = collectFiles(
            MediaType.Image,
            bucketId
        )
        val videoFiles = collectFiles(
            MediaType.Video,
            bucketId
        )
        val files = ArrayList<MediaStoreFile>(imageFiles.size + videoFiles.size)
        files.addAll(imageFiles)
        files.addAll(videoFiles)
        files.sortByDescending { file -> file.dateAdded }
        return files
    }

    private suspend fun collectBucketsInfo(
        mediaType: MediaType,
        destination: MutableMap<Long, BucketInfo>,
    ) {
        val contentUri = contentUriFor(mediaType)
        val cursor = withContext(Dispatchers.IO) {
            context.contentResolver.query(
                contentUri,
                arrayOf(
                    MediaStore.MediaColumns._ID,
                    MediaStore.MediaColumns.BUCKET_ID,
                    MediaStore.MediaColumns.BUCKET_DISPLAY_NAME,
                    MediaStore.MediaColumns.DATE_ADDED,
                ),
                null,
                null,
                null
            )
        } ?: return
        cursor.use { c ->
            val idColumn = c.getColumnIndex(MediaStore.MediaColumns._ID)
            val bucketIdColumn = c.getColumnIndex(MediaStore.MediaColumns.BUCKET_ID)
            val bucketNameColumn = c.getColumnIndex(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME)
            val dateAddedColumn = c.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED)
            while (c.moveToNext()) {
                val id = c.getLong(idColumn)
                val bucketId = c.getLong(bucketIdColumn)
                val dateAdded = c.getLong(dateAddedColumn) * 1000L
                val bucketInfo = destination.getOrPut(bucketId) {
                    BucketInfo(
                        id = bucketId,
                        name = c.getString(bucketNameColumn) ?: id.toString(),
                        coverUri = ContentUris.withAppendedId(
                            contentUri,
                            id
                        ),
                        coverDateAdded = dateAdded,
                        size = 0
                    )
                }
                bucketInfo.size++
                if (bucketInfo.coverDateAdded < dateAdded) {
                    bucketInfo.coverDateAdded = dateAdded
                    bucketInfo.coverUri = ContentUris.withAppendedId(
                        contentUri,
                        id
                    )
                }
            }
        }
    }

    override suspend fun getAlbums(): List<MediaStoreAlbum> {
        val bucketsInfo = LinkedHashMap<Long, BucketInfo>(256)
        collectBucketsInfo(
            MediaType.Image,
            bucketsInfo
        )
        collectBucketsInfo(
            MediaType.Video,
            bucketsInfo
        )
        return bucketsInfo
            .mapTo(ArrayList(bucketsInfo.size)) { (_, info) -> info.toBucket() }
            .also { buckets -> buckets.sortByDescending { bucket -> bucket.coverDateAdded } }
    }

    private fun BucketInfo.toBucket(): MediaStoreAlbum =
        MediaStoreAlbum(
            id,
            name,
            size,
            coverUri,
            coverDateAdded
        )

    private data class BucketInfo(
        val id: Long,
        val name: String,
        var size: Int,
        var coverUri: Uri,
        var coverDateAdded: Long,
    )
}