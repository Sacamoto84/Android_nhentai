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



@Entity( tableName = "gallery") //Имя таблицы
data class Gallery(

    //@PrimaryKey(autoGenerate = true)

     @PrimaryKey
     val id: Long,  //id галлереи

    // val firstName: String?,
    //@ColumnInfo(name = "last_name") val lastName: String?,

    var urlcover: String?, //URL где хранится обложка
    var h1: String?,        //Название
    var num_pages: Int,     //Количество страниц
    var uploaded: String,   //Дата загрузки на сервер

    //var selectedPage : Int  //Номер выбранной картинки 1..num_pages




)

//@Entity( tableName = "gallery") //Имя таблицы


@Entity ( foreignKeys = (arrayOf(ForeignKey(entity = Gallery::class, parentColumns = arrayOf("id"), childColumns = arrayOf("gallery_id"), onDelete = CASCADE))))
data class EntityThumbContainer(

    @PrimaryKey(autoGenerate = true)
    var id : Long =  0L,

    var gallery_id : Long,

    var href: String? = "1", //Адрес страницы по нажатию
    var url: String? = "2",  //Адрес самой иконки

    var urlOriginal: String? = "3", //Адрес оригинальной страницы получаем после парсинга по href






)