package com.example.nhentai.screen.info

import android.annotation.SuppressLint
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.MotionDurationScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.nhentai.AbortGlobalId
import com.example.nhentai.GlobalId
import com.example.nhentai.R
import com.example.nhentai.cache.URLtoFilePath
import com.example.nhentai.cache.cacheCheck
import com.example.nhentai.cache.cacheFileWrite
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.math.abs

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


@Composable
fun flingBehavior(): FlingBehavior {
    val flingSpec = rememberSplineBasedDecay<Float>()
    return remember(flingSpec) {
        DefaultFlingBehavior(flingSpec)
    }
}

internal class DefaultFlingBehavior(
    private val flingDecay: DecayAnimationSpec<Float>,
    private val motionDurationScale: MotionDurationScale = DefaultScrollMotionDurationScale
) : FlingBehavior {

    // For Testing
    var lastAnimationCycleCount = 0

    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        lastAnimationCycleCount = 0
        // come up with the better threshold, but we need it since spline curve gives us NaNs
        return withContext(motionDurationScale) {
            if (abs(initialVelocity) > 1f) {
                var velocityLeft = initialVelocity
                var lastValue = 0f
                AnimationState(
                    initialValue = 0f,
                    initialVelocity = initialVelocity,
                ).animateDecay(flingDecay) {
                    val delta = value - lastValue
                    val consumed = scrollBy(delta)
                    lastValue = value
                    velocityLeft = this.velocity
                    // avoid rounding errors and stop if anything is unconsumed
                    if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
                    lastAnimationCycleCount++
                }
                velocityLeft
            } else {
                initialVelocity
            }
        }
    }
}

internal val DefaultScrollMotionDurationScale = object : MotionDurationScale {
    override val scaleFactor: Float
        get() = DefaultScrollMotionDurationScaleFactor
}

private const val DefaultScrollMotionDurationScaleFactor = 0.5f

var iid by mutableStateOf(403148)

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Info(
    navController: NavHostController,
    viewModel: vmInfo,
    id: Long,
) {


    //navController.popBackStack()

    //val viewModel: vmInfo = viewModel()

    Column() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
        {
            ScreenInfo(navController, viewModel = viewModel, id = id)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.Red)
        )
        {

            Row() {

                Button(onClick = {
                    GlobalId.value -= 1
                    AbortGlobalId = GlobalId.value
                }) {
                    Text(text = "-")
                }

                Text(text = "${GlobalId.value}")

                Button(onClick = {
                    GlobalId.value += 1
                    AbortGlobalId = GlobalId.value
                }) {
                    Text(text = "+")
                }

                Button(onClick = { viewModel.deleteGalleryById() }) {
                    Text(text = "Удалить")
                }

            }

        }
    }

}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class, ExperimentalLayoutApi::class)
@Composable
fun ScreenInfo(
    navController: NavHostController,
    viewModel: vmInfo,
    id: Long,
) {

    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    val state = rememberLazyListState()

    Timber.i("ScreenInfo id $id")

    LaunchedEffect(key1 = true, key2 = id) {
        //viewModel.startLogging()
        Timber.i("...ScreenInfo LaunchedEffect")
        viewModel.launchReadFromId(id)
    }

    //val s = viewModel.thumb

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F1F1F))
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
            //.verticalScroll(state = scrollState, flingBehavior = flingBehavior())
        )
        {



            val v = viewModel.addressThumb.toList()

            LazyColumn(modifier = Modifier.fillMaxSize(),
                state = viewModel.state, flingBehavior = flingBehavior()
            ) {

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth(0.7f),
                            model = viewModel.gallery.urlcover,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = viewModel.gallery.h1.toString(), color = Color(0xFFD9D9D9))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = viewModel.gallery.id.toString(), color = Color(0xFFD9D9D9))

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = GlobalId.value.toString(), color = Color.Red)

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = { navController.navigate("viewer") }) {
                    }

                }

                items(count = v.size) {i ->

                    if (i % 2 == 0)
                    Row(
                        Modifier
                            .fillMaxWidth()
                            //.background(Color.Magenta)
                        , horizontalArrangement = Arrangement.Center) {

                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(4.dp)
                                //.background(Color.Green)
                                .clickable {
                                    scope.launch {
                                        navController.navigate(
                                            "viewer",
                                            //"viewer/${viewModel.thumb[0].gallery_id}/$i",
                                        ) //По нажатию открываем viewer
                                    }
                                },
                            model = v[i],
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            //placeholder = painterResource(androidx.appcompat.R.drawable.notification_bg_normal_pressed )
                        )

                        if ((i + 1) <= v.lastIndex)
                        {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(4.dp)
                                    //.background(Color.Red)
                                    .clickable {
                                        scope.launch {
                                            navController.navigate(
                                                "viewer",
                                                //"viewer/${viewModel.thumb[0].gallery_id}/$i",
                                            ) //По нажатию открываем viewer
                                        }
                                    },
                                model = v[i + 1],
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                //placeholder = painterResource(androidx.appcompat.R.drawable.notification_bg_normal_pressed )
                            )
                    }
                        else
                        {
                         Box(modifier = Modifier.height(1.dp)
                             .fillMaxWidth()
                             .weight(1f).background(Color.Gray))   
                        }


                    }

                }





            }


//            LazyVerticalGrid(
//                columns = GridCells.Adaptive(minSize = 128.dp)
//            )
//            {
//
//                items(v.size) {i ->
//
//                    AsyncImage(
//                        modifier = Modifier
//                            .fillMaxWidth(0.3f)
//                            .padding(top = 8.dp)
//                            .clickable {
//                                scope.launch {
//                                    navController.navigate(
//                                        "viewer",
//                                        //"viewer/${viewModel.thumb[0].gallery_id}/$i",
//                                    ) //По нажатию открываем viewer
//                                }
//                            },
//                        model = v[i], contentDescription = null, contentScale = ContentScale.Crop,
//                        //placeholder = painterResource(androidx.appcompat.R.drawable.notification_bg_normal_pressed )
//                    )
//
//
//                }
//
//            }


//            FlowRow(
//                modifier = Modifier.fillMaxWidth(),
//                maxItemsInEachRow = 2,
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                verticalAlignment = Alignment.CenterVertically
//            )
//            {
//                for (i in v.indices) {
//
//                    Timber.i("index $i")
//                    AsyncImage(
//                        modifier = Modifier
//                            .fillMaxWidth(0.3f)
//                            .padding(top = 8.dp)
//                            .clickable {
//                                scope.launch {
//                                    navController.navigate(
//                                        "viewer",
//                                        //"viewer/${viewModel.thumb[0].gallery_id}/$i",
//                                    ) //По нажатию открываем viewer
//                                }
//                            },
//                        model = v[i], contentDescription = null, contentScale = ContentScale.Crop,
//                        //placeholder = painterResource(androidx.appcompat.R.drawable.notification_bg_normal_pressed )
//                    )
//                }
//            }


        }


        //for (i in v.indices) {

        //}


        //}


    }
}

