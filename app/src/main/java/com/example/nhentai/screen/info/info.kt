package com.example.nhentai.screen.info

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.nhentai.DN
import com.example.nhentai.cache.URLtoFilePathFile
import com.example.nhentai.cache.cacheFileCheck
import kotlinx.coroutines.DelicateCoroutinesApi

fun flowLayoutMeasurePolicy() = MeasurePolicy { measurables, constraints ->
    layout(constraints.maxWidth, constraints.maxHeight) {
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        var yPos = 0
        var xPos = 0
        var maxY = 0
        placeables.forEach { placeable ->
            if (xPos + placeable.width >
                constraints.maxWidth
            ) {
                xPos = 0
                yPos += maxY
                maxY = 0
            }
            placeable.placeRelative(
                x = xPos,
                y = yPos
            )
            xPos += placeable.width
            if (maxY < placeable.height) {
                maxY = placeable.height
            }
        }
    }
}

@Composable
fun FlowLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val measurePolicy = flowLayoutMeasurePolicy()
    Layout(
        measurePolicy = measurePolicy,
        content = content,
        modifier = modifier
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class, ExperimentalLayoutApi::class)
@Composable
fun ScreenInfo(
    navController: NavHostController,
    id: Int = 403147,
    viewModel: vmInfo = hiltViewModel()
) {

    DisposableEffect(key1 = viewModel) {
        //viewModel.startLogging()

        viewModel.launchReadFromId()


        onDispose {
            //viewModel.stopAndLogTime()
        }
    }

    if (viewModel.ReedDataComplete.value) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1F1F1F))
        )
        {

            val scrollState = rememberScrollState(0)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            )
            {

                Spacer(modifier = Modifier.height(8.dp))
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth(0.7f),
                        model = DN.urlCover,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = DN.h1.toString(), color = Color(0xFFD9D9D9))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = DN.h2.toString(), color = Color(0xFFD9D9D9))
                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    maxItemsInEachRow = 3//viewModel.DN.num_pages/1
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically

                )
                {
                    DN.thumbContainers.forEachIndexed { index, i ->

                        //Поместить в кеш эскиз
                        viewModel.cacheThumbalis(i.url.toString())

                        val address = if (!cacheFileCheck(i.url.toString())) {
                            i.url.toString()
                        } else {
                            URLtoFilePathFile(i.url.toString())
                        }

                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .padding(top = 8.dp)
                                .clickable {
                                    //Нажатие на иконку
                                    viewModel.launchReadOriginalImageFromHref(i.href.toString(), index)

                                    //navController.navigate(Screen.Viewer.route+"?id={100}&currentPage={200}&countPage={300}")
                                },

                            model = address//i.url.toString()
                            ,
                            contentDescription = null, contentScale = ContentScale.Crop
                        )

                    }

                }


            }

        }

    } else {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Gray)
        )
        {

        }
    }


}

fun onScroll(function: () -> Unit) {

}
