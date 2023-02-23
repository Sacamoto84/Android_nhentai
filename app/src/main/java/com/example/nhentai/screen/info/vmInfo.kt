package com.example.nhentai.screen.info

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhentai.DN
import com.example.nhentai.api.readHtmlFromURL
import com.example.nhentai.cache.cacheCheck
import com.example.nhentai.cache.cacheFileWrite
import com.example.nhentai.parser.stringToDynamicHentai
import com.example.nhentai.parser.stringToUrlOriginal
import com.example.nhentai.room.AppDatabase
import com.example.nhentai.room.EntityThumbContainer
import com.example.nhentai.room.Gallery
import com.example.nhentai.room.YourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class vmInfo @Inject constructor(
    //@ApplicationContext
    //private val context: Context,

    private val repository: YourRepository

) : ViewModel() {

    init {
        Timber.i("Создание вьюмодели vmInfo")
    }


    suspend fun databaseSize(): List<Gallery> {
        return repository.numberOfItemsInDB()
    }


    override fun onCleared() {
        super.onCleared()
        Timber.i("................onCleared")
    }

    var ReedDataComplete = mutableStateOf(false) //Признак того что данные прочитаны полностью

    var IndexirovanieComplete = mutableStateOf(false)

    ////////////////////////////////////////////////////////
    fun launchIndexirovanieOriginal() {
        Timber.i("...Запуск Индексирования")

        IndexirovanieComplete.value = false

        DN.thumbContainers.forEachIndexed { index, i ->
            Timber.i("...Индексирование index:$index href:${i.href.toString()}")
            viewModelScope.launch(Dispatchers.IO) {
                launchReadOriginalImageFromHref(i.href.toString(), index)
            }
        }

    }

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
    fun launchReadFromId(id: Int = 403147) {
        Timber.i("...launchReadFromId() $id")
        ReedDataComplete.value = false
        viewModelScope.launch(Dispatchers.IO) {
            val html = readHtmlFromURL("https://nhentai.to/g/$id")
            DN = stringToDynamicHentai(html)
            ReedDataComplete.value = true


            run{
                    Timber.i("Добавление в Room записи DN id:${DN.id}")
                    try {

                        repository.insertInInDB(
                            Gallery(
                                DN.id.toLong(),
                                urlcover = DN.urlCover,
                                h1 = DN.h1,
                                num_pages = DN.num_pages,
                                uploaded = DN.uploaded,
                            )
                        )

                        DN.thumbContainers.forEach { it ->

                            repository.insertInThumbContainer(
                                EntityThumbContainer(
                                    gallery_id = DN.id.toLong(),
                                    href = it.href,
                                    url = it.url,
                                    urlOriginal = it.urlOriginal
                                )
                            )


                        }




                    }
                    catch (e: Exception)
                    {
                        Timber.e("repository.insertInInDB "+e.message)
                    }

            }



        }
    }



    fun deleteGalleryById()
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.galleryDeleteById(DN.id.toLong())
        }
    }





    //Нажание на эскиз запросит OriginalUrl и сохранит его в кеш
    fun launchReadOriginalImageFromHref(href: String, index: Int) {
        Timber.i("...launchReadOriginalImageFromHref()")

        viewModelScope.launch(Dispatchers.IO) {

            //Timber.i("Ok1")
            var s = "https://nhentai.to${href}"

            if (s.last() == '/')
                s = s.dropLast(1)

            val html = readHtmlFromURL(s)

            //Timber.i("Ok2")
            val OriginalURL = stringToUrlOriginal(html)

            DN.thumbContainers[index].urlOriginal = OriginalURL

            Timber.i("OriginalURL = $OriginalURL")

            //Если нет файла то создадим для него кеш
            if (OriginalURL?.let { cacheCheck(it) } == false) {
                cacheFileWrite(OriginalURL)
            }

        }

    }


}