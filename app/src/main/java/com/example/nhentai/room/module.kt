package com.example.nhentai.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoom(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder( context, AppDatabase::class.java,"your_db_name" ).fallbackToDestructiveMigration().build()




    @Singleton
    @Provides
    fun provideYourDao(db: AppDatabase) = db.galleryDao()

    @Singleton
    @Provides
    fun provideEntityThumbContainerDao(db: AppDatabase) = db.entityThumbContainer()



}