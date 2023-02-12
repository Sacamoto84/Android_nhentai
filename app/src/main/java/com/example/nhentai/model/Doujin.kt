package com.example.nhentai.model


//data class Doujin(
//    var id: Int,
//    val media_id: String,
//    val upload_at: datetime,
//    val url: String,
//
//    var title: NHentaiTitle,
//
//    var tags: List[Tag],
//var artists: List[Tag],
//var languages: List[Tag],
//var categories: List[Tag],
//var characters: List[Tag],
//var parodies: List[Tag],
//var groups: List[Tag],
//var cover: Cover,
//var images: List[DoujinPage],
//var total_favorites: Int = 0,
//var total_pages: Int = 0
//
//
//)

data class DynamicNHentai
(

    var id: Int,
    var urlCover: String?,
    var h1: String?,
    var h2: String?,

    var tags: MutableList<TagContainer>?,

    var num_pages: Int,         //Количество страниц
    var uploaded : String,

    var thumbContainers : MutableList<ThumbContainer> //Список Иконок

)


data class TagContainer(
    var type : String,
    var tags: MutableList<NHentaiTag>?,
)

//Тег
data class NHentaiTag(
    var name: String?,
    var url: String?,
    var count: Int?
)

//Тег
data class ThumbContainer(
    var href: String?, //Адрес страницы по нажатию
    var url: String?,  //Адрес самой иконки
)