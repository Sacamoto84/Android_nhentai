package com.example.nhentai.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
)


@Entity(tableName = "gallery") //Имя таблицы
data class Gallery(

    @PrimaryKey
    val id: Long = 999L,                         //id галлереи
    var urlcover: String? = "",                 //URL где хранится обложка
    var h1: String = "empty",                       //Название
    var num_pages: Int = 0,                    //Количество страниц
    var uploaded: String = "",                  //Дата загрузки на сервер

    var AllDataComplete: Boolean = false,  //Содержит полностью все данные //Это значит что OriginalUrl записан везде
    var downloaded: Boolean = false,       // Признак того что галерея загружена на диск
)

//@Entity( tableName = "gallery") //Имя таблицы


@Entity(
    foreignKeys = (arrayOf(
        ForeignKey(
            entity = Gallery::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("gallery_id"),
            onDelete = CASCADE
        )
    ))
)
data class EntityThumbContainer(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    var gallery_id: Long,

    var href: String? = null,        //Адрес страницы по нажатию       /g/403149/6/
    var urlthumb: String? = null,    //Адрес самой иконки              https://img.dogehls.xyz/galleries/2220566/6t.jpg

    var urloriginal: String? = null, //Адрес оригинальной страницы получаем после парсинга по href

)