package com.example.nhentai.room

import javax.inject.Inject

class YourRepository @Inject constructor(
    private val galleryDAO: GalleryDao,
    private val thumbContainerDAO: EntityThumbContainerDao
){







    /////////////////
    //Snapshot
    //Предоставить галереи по id
    suspend fun galleryById(id: Long) =  galleryDAO.getGalleryById(id)

    //Предоставить thumbContainer по id
    suspend fun getThumbContainerById(id: Long) =  thumbContainerDAO.getEntityThumbContainerById(id)
    /////////////////








    suspend fun numberOfItemsInDB() = galleryDAO.getAll()

    //Проверка на то что является ли данная галерея загруженной
    //suspend fun isGalleryDownloaded(id : Long) = galleryDAO.isGalleryDownloaded(id)

    //Проверка на существование записи id
    suspend fun isGalleryExist(id : Long) = galleryDAO.isExist(id)

    suspend fun insertInInDB(gallery: Gallery) = galleryDAO.insertAll(gallery)

    suspend fun galleryDeleteById(id: Long) = galleryDAO.deleteById(id)

    suspend fun insertInThumbContainer(entity: EntityThumbContainer) = thumbContainerDAO.insertAll(entity)





}