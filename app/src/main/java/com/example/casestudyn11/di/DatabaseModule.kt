package com.example.casestudyn11.di

import android.content.Context
import androidx.room.Room
import com.example.casestudyn11.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDao(db: UserDatabase) = db.userDao()

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = UserDatabase::class.java,
            name = "user_database"
        ).build()
    }
}