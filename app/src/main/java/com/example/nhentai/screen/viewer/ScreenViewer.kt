package com.example.nhentai.screen.viewer

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculateCentroidSize
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateRotation
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.util.fastAny
import androidx.compose.ui.util.fastForEach
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.jakewharton.picnic.table
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Math.max
import java.text.DecimalFormat
import kotlin.math.PI
import kotlin.math.abs

var scale = mutableStateOf(1.0f)
var offset = mutableStateOf(Offset(0f, 0f))
var zoom1 by mutableStateOf(1f)


@Composable
fun ScreenViewer(navController: NavHostController, viewModel: vmViewer = hiltViewModel<vmViewer>()) {
//


    Timber.i(
        table {
            cellStyle {border = true }
            //row("...ScreenViewer id $_id page $_page")
        }.toString()
    )

    LaunchedEffect(key1 = true, block =
    {
        Timber.i(
            table {
                cellStyle {border = true }
                row("...ScreenViewer LaunchedEffect")
            }.toString()
        )
        viewModel.calculateAddressCoroutine()
    })


    val scope = rememberCoroutineScope()
    val zoomState = rememberZoomState(4f)

//    //Если нет адреса оригинала
//    if (DN.thumbContainers[DN.selectedPage - 1].urlOriginal.isNullOrEmpty()) {
//        viewModel.launchReadOriginalImageFromHref(
//            DN.thumbContainers[DN.selectedPage - 1].href.toString(),
//            DN.selectedPage - 1
//        )
//
//    }

    //var offset by remember { mutableStateOf(Offset.Zero) }
    //var centroid by remember { mutableStateOf(Offset.Zero) }
    //var angle by remember { mutableStateOf(0f) }

    val decimalFormat = remember { DecimalFormat("0.0") }

    val address = viewModel.selectAddress.collectAsState()
    val page = viewModel.selectPageState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F1F1F))
    ) {

        Timber.i(
            table {
                cellStyle {border = true }
                row("...ScreenViewer Column")
            }.toString()
        )



        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(Color(0xFF1F1F1F)),
            contentAlignment = Alignment.Center
        )
        {




                //Timber.i("scale2 ${scale.value} ${offset.value}")

                SubcomposeAsyncImage(
                    model = address.value,
                    loading = {
                        CircularProgressIndicator(color = Color.White)
                    },
                    contentDescription = "stringResource(R.string.description)",
                    contentScale = ContentScale.Fit,
                    onSuccess = {
                        val p = it.painter.intrinsicSize
                        zoomState.setImageSize(Size(p.width, p.height))

                    },

                    modifier = Modifier
                        .fillMaxSize()
                        .onSizeChanged { size ->
                            zoomState.setLayoutSize(size.toSize())
                        }
                        .pointerInput(Unit) {
                            detectTransformGestures(
                                onGesture = { centroid, pan, zoom, _, timeMillis ->
                                    scope.launch {
                                        zoomState.applyGesture(
                                            pan = pan,
                                            zoom = zoom,
                                            position = centroid,
                                            timeMillis = timeMillis,
                                            centroid
                                        )
                                    }
                                },
                                onGestureEnd = {
                                    scope.launch {
                                        zoomState.endGesture()
                                    }
                                }
                            )


//                        detectTransformGestures(
//
//                            onGesture = { gestureCentroid, gesturePan, gestureZoom, _ ->
//                                val oldScale = zoom
//                                val newScale = zoom * gestureZoom
//                                offset = (offset + gestureCentroid / oldScale) - (gestureCentroid / newScale + gesturePan / oldScale)
//                                zoom = newScale.coerceIn(0.5f..5f)
//                                //centroid = gestureCentroid
//                            }
//                        )
                        }
                        .graphicsLayer {

                            scaleX = zoomState.scale
                            scaleY = zoomState.scale
                            //translationX = zoomState.offsetX
                            //translationY = zoomState.offsetY

                            translationX = zoomState.offsetX //* zoom1
                            translationY = zoomState.offsetY //* zoom1

//                        translationX = -offset.x * zoom
//                        translationY = -offset.y * zoom


//                        scaleX = zoom
//                        scaleY = zoom
                            //TransformOrigin(0f, 0f).also { transformOrigin = it }
                        }

                )




        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color(0x801F1F1F))
        )
        {

            Row(
                modifier = Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(onClick = { viewModel.first() }) {
                }
                Button(onClick = { viewModel.previous() }) {
                }

//                Text(
//                    text = page.value.toString() + " / " + DN.num_pages.toString(),
//
//                    color = Color(0xFFC3C3C3)
//                )

                Button(onClick = { viewModel.next() }) {
                }
                Button(onClick = { viewModel.last() }) {
                }
            }

        }

    }


}


suspend fun PointerInputScope.detectTransformGestures(
    panZoomLock: Boolean = false,
    onGesture: (centroid: Offset, pan: Offset, zoom: Float, rotation: Float, timeMillis: Long) -> Unit,
    onGestureStart: () -> Unit = {},
    onGestureEnd: () -> Unit = {},
) {
    forEachGesture {

        awaitPointerEventScope {
            var rotation = 0f
            var zoom = 1f
            var pan = Offset.Zero
            var pastTouchSlop = false
            val touchSlop = viewConfiguration.touchSlop
            var lockedToPanZoom = false

            awaitFirstDown(requireUnconsumed = false)
            onGestureStart()
            do {
                val event = awaitPointerEvent()
                val canceled = event.changes.fastAny { it.isConsumed }
                if (!canceled) {
                    val zoomChange = event.calculateZoom()
                    val rotationChange = event.calculateRotation()
                    val panChange = event.calculatePan()

                    if (!pastTouchSlop) {
                        zoom *= zoomChange
                        rotation += rotationChange
                        pan += panChange

                        val centroidSize = event.calculateCentroidSize(useCurrent = false)
                        val zoomMotion = abs(1 - zoom) * centroidSize
                        val rotationMotion = abs(rotation * PI.toFloat() * centroidSize / 180f)
                        val panMotion = pan.getDistance()

                        if (zoomMotion > touchSlop ||
                            rotationMotion > touchSlop ||
                            panMotion > touchSlop
                        ) {
                            pastTouchSlop = true
                            lockedToPanZoom = panZoomLock && rotationMotion < touchSlop
                        }
                    }

                    if (pastTouchSlop) {
                        val centroid = event.calculateCentroid(useCurrent = false)
                        val effectiveRotation = if (lockedToPanZoom) 0f else rotationChange
                        if (effectiveRotation != 0f ||
                            zoomChange != 1f ||
                            panChange != Offset.Zero
                        ) {
                            onGesture(
                                centroid,
                                panChange,
                                zoomChange,
                                effectiveRotation,
                                event.changes[0].uptimeMillis
                            )
                        }
                        event.changes.fastForEach {
                            if (it.positionChanged()) {
                                it.consume()
                            }
                        }
                    }
                }
            } while (!canceled && event.changes.fastAny { it.pressed })
            onGestureEnd()
        }
    }
}


@Stable
class ZoomState(private val maxScale: Float) {
    private var _scale = Animatable(1f).apply {
        updateBounds(0.9f, maxScale)
    }
    val scale: Float
        get() = _scale.value

    private var _offsetX = Animatable(0f)
    val offsetX: Float
        get() = _offsetX.value

    private var _offsetY = Animatable(0f)
    val offsetY: Float
        get() = _offsetY.value

    private var layoutSize = Size.Zero
    fun setLayoutSize(size: Size) {
        layoutSize = size
        updateFitImageSize()
    }

    private var imageSize = Size.Zero
    fun setImageSize(size: Size) {
        imageSize = size
        updateFitImageSize()
    }

    private var fitImageSize = Size.Zero
    private fun updateFitImageSize() {
        if ((imageSize == Size.Zero) || (layoutSize == Size.Zero)) {
            fitImageSize = Size.Zero
            return
        }

        val imageAspectRatio = imageSize.width / imageSize.height
        val layoutAspectRatio = layoutSize.width / layoutSize.height

        fitImageSize = if (imageAspectRatio > layoutAspectRatio) {
            imageSize * (layoutSize.width / imageSize.width) * 2.5f
        } else {
            imageSize * (layoutSize.height / imageSize.height) * 2.5f
        }
    }

    private val velocityTracker = VelocityTracker()
    private var shouldFling = true

    suspend fun applyGesture(
        pan: Offset,
        zoom: Float,
        position: Offset,
        timeMillis: Long,

        gestureCentroid: Offset

    ) = coroutineScope {

        val oldScale = zoom1
        val newScale = zoom1 * zoom

        launch {
            _scale.snapTo(_scale.value * zoom)
        }

        val boundX = max((fitImageSize.width * _scale.value - layoutSize.width), 0f) / 2f
        _offsetX.updateBounds(-boundX, boundX)

        launch {
            _offsetX.snapTo(_offsetX.value + pan.x)


            //val f = _offsetX.value + gestureCentroid.x / oldScale - (gestureCentroid.x / newScale + pan.x / oldScale) + gestureCentroid.x / oldScale
            //_offsetX.snapTo( f )
        }

        //offset = (offset + gestureCentroid / oldScale) - (gestureCentroid / newScale + gesturePan / oldScale)

        val boundY = max((fitImageSize.height * _scale.value - layoutSize.height), 0f) / 2f
        _offsetY.updateBounds(-boundY, boundY)
        launch {
            _offsetY.snapTo(_offsetY.value + pan.y)

            //val f = _offsetY.value - (gestureCentroid.y / newScale + pan.y / oldScale) + gestureCentroid.y / oldScale
            //_offsetX.snapTo( f )
        }

        zoom1 = newScale.coerceIn(0.5f..5f)


        velocityTracker.addPosition(timeMillis, position)

        if (zoom != 1f) {
            shouldFling = false
        }
    }

    private val velocityDecay = exponentialDecay<Float>(frictionMultiplier = 0.5f)

    suspend fun endGesture() = coroutineScope {

        if (shouldFling) {
            val velocity = velocityTracker.calculateVelocity() / 2f
            Timber.i("velocity $velocity")
            launch {
                _offsetX.animateDecay(velocity.x, velocityDecay)
            }
            launch {
                _offsetY.animateDecay(velocity.y, velocityDecay)
            }
        }
        shouldFling = true

        if (_scale.value < 1f) {
            launch {
                _scale.animateTo(1f)
            }
        }

        if (_scale.value > 2f) {
            launch {
                _scale.animateTo(2f)
            }
        }
    }
}


@Composable
fun rememberZoomState(maxScale: Float) = remember { ZoomState(maxScale) }