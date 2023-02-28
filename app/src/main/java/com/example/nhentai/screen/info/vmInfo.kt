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
import com.example.nhentai.api.Downloader
import com.example.nhentai.api.readHtmlFromURL
import com.example.nhentai.cache.URLtoFilePath
import com.example.nhentai.cache.cacheCheck
import com.example.nhentai.cache.cacheFileWrite
import com.example.nhentai.parser.stringToDynamicHentai
import com.example.nhentai.parser.stringToUrlOriginal
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
    //var thumb = mutableStateListOf<EntityThumbContainer>()

    var addressThumb =
        mutableStateListOf<String>()  //Список адресов которые используются для вывода списка

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

        if (!((id>0) && (id < 10000000)))
        {
            Timber.e("Ошибка id")
            return
        }

        //addressThumb.clear()
        //gallery = Gallery()

        viewModelScope.launch(Dispatchers.Default) {

            addressThumb.clear()
            gallery = Gallery()

            val thumb = mutableListOf<EntityThumbContainer>()


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
                    tempDN.thumbContainers?.forEachIndexed { index, it ->
                        repository.insertInThumbContainer(
                            EntityThumbContainer(
                                gallery_id = tempDN.id.toLong(),
                                href = it.href,
                                urlthumb = it.url,
                                urloriginal = it.urlOriginal,
                                num = index + 1
                            )
                        )
                    }

                    gallery = repository.galleryById(id.toLong()) //Читаем текущую галерею
                    thumb.addAll(repository.getThumbContainerById(id.toLong()))

                } catch (e: Exception) {
                    Timber.e("repository.insertInInDB " + e.message)
                }

            } else {

                gallery = repository.galleryById(id.toLong()) //Читаем текущую галерею
                thumb.addAll(repository.getThumbContainerById(id.toLong()))

            }


//            launch(Dispatchers.Default) {
//                addressThumb.clear()
//                thumb.forEach {
//
//                    launch(Dispatchers.IO) {
//                        val address =
//                            if (!cacheCheck(it.urlthumb.toString())) {
//                                Downloader.cache.FileChannel.send(it.urlthumb.toString())
//                                it.urlthumb.toString()
//                            } else {
//                                URLtoFilePath(it.urlthumb.toString())
//                            }
//                        addressThumb.add(address)
//                    }
//
//                }
//            }

//
//            launch(Dispatchers.Default) {
//
//                if ((thumb.size != 0) && (!gallery.AllDataComplete)) {
//                    //Если есть записи по эскизам, получим список оригиналов
//                    Timber.i("...Получение Оригинал по href()")
//                    val shot = thumb.toList()
//
//
//                        for (i in shot.indices) {
//                            var s = "https://nhentai.to${shot[i].href}"
//                            if (s.last() == '/') s = s.dropLast(1)
//                            val originalURL = stringToUrlOriginal(readHtmlFromURL(s))
//                            shot[i].urloriginal = originalURL
//                            Timber.i("originalURL = $originalURL")
//                        }
//
//                        //Получили список оригинал адресов и записали в базу
//                        for (i in shot.indices) {
//                            shot[i].urloriginal?.let {
//                                repository.updateOriginal(
//                                    shot[i].gallery_id, (i + 1).toLong(),
//                                    it
//                                )
//                            }
//                        }
//
//                        //Установили признак того что все оригинал записаны в базу
//                        repository.updateAllDataComplete(shot[0].gallery_id, true)
//
//
//
//                }
//
//            }
//


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