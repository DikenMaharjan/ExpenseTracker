package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.database.MoneyTrackDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun providesRoomDatabase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(
        appContext,
        MoneyTrackDatabase::class.java,
        "moneytrack"
    ).build()

    @Provides
    @Singleton
    fun providesExpenseDao(
        roomDatabase: MoneyTrackDatabase
    ) = roomDatabase.getExpenseDao()

    @Provides
    @Singleton
    fun providesCategoryDao(
        roomDatabase: MoneyTrackDatabase
    ) = roomDatabase.getCategoryDao()
}