package com.example.casestudyn11.di

import com.example.casestudyn11.helper.FavoriteUpdatesDispatcher
import com.example.casestudyn11.helper.FavoriteUpdatesDispatcherImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteModule {

    @Binds
    @Singleton
    abstract fun bindFavoriteUpdatesDispatcher(
        favoriteUpdatesDispatcherImpl: FavoriteUpdatesDispatcherImpl
    ): FavoriteUpdatesDispatcher
}