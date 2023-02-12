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

data class NHentai (
    var id: Int,
    var media_id: String,
    var title: NHentaiTitle,
    var images: NHentaiImages,
    var scanlator: String,
    var upload_date: Int,
    var tags: MutableList<NHentaiTag>,
    var num_pages: Int,
    var num_favorites: Int
    )

//data class DynamicNHentai (
//    var id: MaybeI32OrString,
//    var media_id: String,
//    var title: NHentaiTitle,
//    var images: NHentaiImages,
//    var scanlator: String,
//    var upload_date: Int,
//    var tags: NHentaiTags,
//    var num_pages: Int,
//    var num_favoritesval : Int
//)

data class NHentaiTitle(
    var english: String,
    var japanese: String,
    var pretty: String
)

data class NHentaiImages (
    var pages: MutableList<NHentaiPage>,
    var cover: NHentaiPage,
    var thumbnailval : NHentaiPage
)

data class NHentaiPage(
    var t: String,
    var w: Int,
    var h: Int
)

data class NHentaiTag(
    var id: Int,
    var type: String,
    var name: String,
    var url: String,
    var count: Int
)






//data class NHentaiGroup (
//    var result: MutableList<NHentai>,
//    var num_pages: Int,
//    var per_pageval : Int
//)
//
//data class DynamicNHentaiGroup (
//    var result: MutableList<DynamicNHentai>,
//    var num_pages: Int,
//    var per_pageval : Int
//)