package com.example.nhentai.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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


}

@Dao
interface EntityThumbContainerDao {

    //В режиме REPLACE старая запись будет заменена новой.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: EntityThumbContainer)

    @Delete
    fun delete(entity: EntityThumbContainer)
}