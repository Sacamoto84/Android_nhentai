package com.example.nhentai.model

data class DynamicNHentai
    (
    var id: Int = 0,
    var urlCover: String? = "",
    var h1: String = "",
    var tags: MutableList<TagContainer>?,
    var num_pages: Int = 0,         //Количество страниц
    var uploaded: String = "",
    var thumbContainers: MutableList<ThumbContainer>?, //Список Иконок
    var selectedPage: Int = 1//Номер выбранной картинки 1..num_pages
)

data class TagContainer(
    var type: String,
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

    var urlOriginal: String? = null //Адрес оригинальной страницы получаем после парсинга по href
)