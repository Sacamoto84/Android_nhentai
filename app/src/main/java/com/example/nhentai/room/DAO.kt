package com.example.nhentai.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query(
        "SELECT * FROM user WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1"
    )
    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}

@Dao
interface GalleryDao {

    @Query("SELECT * FROM gallery")
    fun getAll(): List<Gallery>

    //Получить Галерею по требуемому id
    @Query("SELECT * from gallery WHERE id = :id")
    fun getGalleryById(id: Long): Gallery
    
    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
//

    //В режиме REPLACE старая запись будет заменена новой.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg gallery: Gallery)

    @Delete
    fun delete(gallery: Gallery)

    @Query("DELETE FROM gallery WHERE id = :id")
    fun deleteById(id: Long)

    @Query("DELETE FROM gallery")
    fun deleteAll()




    //Проверка на существование записи id
    @Query("SELECT EXISTS(SELECT id FROM gallery WHERE id = :id)")
    fun isExist(id: Long): Boolean


//    //Проверка на то что является ли данная галерея загруженной
//    @Query("SELECT downloaded FROM gallery WHERE id = :id")
//    fun isGalleryDownloaded(id: Long): Boolean


}

@Dao
interface EntityThumbContainerDao {

    //Получить по требуемому id
    @Query("SELECT * from EntityThumbContainer WHERE gallery_id = :id")
    fun getEntityThumbContainerById(id: Long): List<EntityThumbContainer>

    //В режиме REPLACE старая запись будет заменена новой.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: EntityThumbContainer)

    @Delete
    fun delete(entity: EntityThumbContainer)
}