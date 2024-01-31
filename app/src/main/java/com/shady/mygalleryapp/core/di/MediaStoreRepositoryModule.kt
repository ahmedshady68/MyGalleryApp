package com.shady.mygalleryapp.core.di

import android.content.Context
import com.shady.mygalleryapp.core.data.repository.MediaStoreRepository
import com.shady.mygalleryapp.core.data.repository.MediaStoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaStoreRepositoryModule {
    @Provides
    @Singleton
    fun provideMediaStoreRepository(@ApplicationContext context: Context): MediaStoreRepository =
        MediaStoreRepositoryImpl(context)
}