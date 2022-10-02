package com.example.currencyconvertor.di

import android.content.Context
import androidx.room.Room
import com.example.currencyconvertor.database.AppDao
import com.example.currencyconvertor.database.AppDatabase
import com.example.currencyconvertor.util.Constants
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
    fun provideAppDatabase(@ApplicationContext context: Context):AppDatabase{
        return Room.databaseBuilder(
            context,AppDatabase::class.java,
            Constants.LOCAL_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    }

    @Singleton
    @Provides
    fun provideAppDao(appDatabase: AppDatabase):AppDao{
        return appDatabase.appDao()
    }

}