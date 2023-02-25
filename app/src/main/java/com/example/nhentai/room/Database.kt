package com.example.nhentai.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Gallery::class, EntityThumbContainer::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun galleryDao(): GalleryDao
    abstract fun entityThumbContainer(): EntityThumbContainerDao
}

