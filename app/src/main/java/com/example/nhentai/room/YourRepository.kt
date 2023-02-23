package com.example.nhentai.room

import javax.inject.Inject

class YourRepository @Inject constructor(
    private val galleryDAO: GalleryDao,
    private val thumbContainerDAO: EntityThumbContainerDao
){


    suspend fun numberOfItemsInDB() = galleryDAO.getAll()

    suspend fun insertInInDB(gallery: Gallery) = galleryDAO.insertAll(gallery)

    suspend fun galleryDeleteById(id: Long) = galleryDAO.deleteById(id)


    suspend fun insertInThumbContainer(entity: EntityThumbContainer) = thumbContainerDAO.insertAll(entity)
}