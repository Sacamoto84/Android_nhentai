package com.example.nhentai.screen.info

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhentai.api.readHtmlFromURL
import com.example.nhentai.cache.cacheCheck
import com.example.nhentai.cache.cacheFileWrite
import com.example.nhentai.parser.stringToDynamicHentai
import com.example.nhentai.room.EntityThumbContainer
import com.example.nhentai.room.Gallery
import com.example.nhentai.room.YourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@SuppressLint("StaticFieldLeak")
@HiltViewModel
class vmInfo @Inject constructor(
    //@ApplicationContext
    //private val context: Context,

    private val repository: YourRepository

) : ViewModel() {

    var gallery by mutableStateOf(Gallery())
    var thumb = mutableStateListOf<EntityThumbContainer>()

    init {
        Timber.i("Создание вьюмодели vmInfo")
    }


    suspend fun databaseSize(): List<Gallery> {
        return repository.numberOfItemsInDB()
    }


    override fun onCleared() {
        super.onCleared()
        Timber.i("................onCleared")

        //DN = DynamicNHentai(0,"","null","null", null, 0, "", null, 0)
    }

//    ////////////////////////////////////////////////////////
//    fun launchIndexirovanieOriginal() {
//        Timber.i("...Запуск Индексирования")
//
//        IndexirovanieComplete.value = false
//
//        DN.thumbContainers?.forEachIndexed { index, i ->
//            Timber.i("...Индексирование index:$index href:${i.href.toString()}")
//            viewModelScope.launch(Dispatchers.IO) {
//                launchReadOriginalImageFromHref(i.href.toString(), index)
//            }
//        }
//
//    }

    fun cacheThumbalis(url: String) {
        Timber.i("...cacheThumbalis")
        viewModelScope.launch(Dispatchers.IO) {
            //Если нет файла то создадим для него кеш
            if (!cacheCheck(url)) {
                cacheFileWrite(url)
            }
        }
    }

    ////////////////////////////////////////////////////////
    //  Запуск при открытии страницы
    ////////////////////////////////////////////////////////
    fun launchReadFromId(id: Int) {
        Timber.i("...launchReadFromId() $id")

        viewModelScope.launch(Dispatchers.IO) {

            gallery = Gallery()
            thumb.clear()

            val isExistInRoom = repository.isGalleryExist(id.toLong())
            Timber.i("Запись id $id существует в Room = $isExistInRoom")

            //Записи нет читаем из сети
            if (!isExistInRoom) {
                Timber.i("Запись id $id нет в Room читаем из сети")
                val html = readHtmlFromURL("https://nhentai.to/g/$id")
                if (html.isBlank()) {
                    Timber.e("html пустой")
                }

                val tempDN = stringToDynamicHentai(html)


                run {
                    Timber.i("Добавление в Room записи DN id:${tempDN.id}")
                    try {

                        repository.insertInInDB(
                            Gallery(
                                tempDN.id.toLong(),
                                urlcover = tempDN.urlCover,
                                h1 = tempDN.h1,
                                num_pages = tempDN.num_pages,
                                uploaded = tempDN.uploaded
                            )
                        )

                        //Помещаем в Room ThumbContainer
                        tempDN.thumbContainers?.forEach { it ->
                            repository.insertInThumbContainer(
                                EntityThumbContainer(
                                    gallery_id = tempDN.id.toLong(),
                                    href = it.href,
                                    urlthumb = it.url,
                                    urloriginal = it.urlOriginal
                                )
                            )
                        }

                        gallery = repository.galleryById(id.toLong()) //Читаем текущую галерею
                        thumb.addAll(repository.getThumbContainerById(id.toLong()))

                    } catch (e: Exception) {
                        Timber.e("repository.insertInInDB " + e.message)
                    }
                }
            } else {

                gallery = repository.galleryById(id.toLong()) //Читаем текущую галерею
                thumb .addAll(repository.getThumbContainerById(id.toLong()))

            }

            Timber.i("Закончили ...launchReadFromId() 0")

        }




    }

    //Удалить запись из Room, висит на кнопке удалить
    fun deleteGalleryById() {
        viewModelScope.launch(Dispatchers.IO) {
            //DNGallery.value?.id?.let { repository.galleryDeleteById(it.toLong()) }
        }
    }

//    //Нажание на эскиз запросит OriginalUrl и сохранит его в кеш
//    fun launchReadOriginalImageFromHref(href: String, index: Int) {
//        Timber.i("...launchReadOriginalImageFromHref()")
//
//        viewModelScope.launch(Dispatchers.IO) {
//
//            //Timber.i("Ok1")
//            var s = "https://nhentai.to${href}"
//
//            if (s.last() == '/')
//                s = s.dropLast(1)
//
//            val OriginalURL = stringToUrlOriginal(readHtmlFromURL(s))
//            DN.thumbContainers?.get(index)?.urlOriginal = OriginalURL
//
//            Timber.i("OriginalURL = $OriginalURL")
//
//            //Если нет файла то создадим для него кеш
//            if (OriginalURL?.let { cacheCheck(it) } == false) {
//                cacheFileWrite(OriginalURL)
//            }
//
//        }
//
//    }


}