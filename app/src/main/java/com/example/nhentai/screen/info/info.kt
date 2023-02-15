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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.MotionDurationScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.nhentai.DN
import com.example.nhentai.cache.URLtoFilePathFile
import com.example.nhentai.cache.cacheFileCheck
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.withContext
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




@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class, ExperimentalLayoutApi::class)
@Composable
fun ScreenInfo(
    navController: NavHostController,
    viewModel: vmInfo = viewModel(),
    id: Int = 403147,
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
                    .verticalScroll(state = scrollState, flingBehavior = flingBehavior())
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
                                    DN.selectedPage = index + 1

                                    navController.navigate("viewer") //По нажатию открываем viewer
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


