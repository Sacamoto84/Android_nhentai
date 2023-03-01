package com.example.nhentai.room

import javax.inject.Inject

class YourRepository @Inject constructor(
    private val galleryDAO: GalleryDao,
    private val thumbContainerDAO: EntityThumbContainerDao
) {


    /////////////////
    //Snapshot
    //Предоставить галереи по id
    fun galleryById(id: Long) = galleryDAO.getGalleryById(id)

    //Предоставить thumbContainer по id
    fun getThumbContainerById(id: Long) = thumbContainerDAO.getEntityThumbContainerById(id)
    /////////////////


    suspend fun updateOriginal(gallery_id: Long, num: Long, url: String) =
        thumbContainerDAO.updateOriginal(gallery_id, num, url)


    suspend fun updateAllDataComplete(id : Long, complete : Boolean) = galleryDAO.updateAllDataComplete(id, complete)


    suspend fun numberOfItemsInDB() = galleryDAO.getAll()

    //Проверка на то что является ли данная галерея загруженной
    //suspend fun isGalleryDownloaded(id : Long) = galleryDAO.isGalleryDownloaded(id)

    //Проверка на существование записи id
    suspend fun isGalleryExist(id: Long) = galleryDAO.isExist(id)

    fun insertInInDB(gallery: Gallery) = galleryDAO.insertAll(gallery)

    suspend fun galleryDeleteById(id: Long) = galleryDAO.deleteById(id)

    fun insertInThumbContainer(entity: EntityThumbContainer) =
        thumbContainerDAO.insertAll(entity)


}