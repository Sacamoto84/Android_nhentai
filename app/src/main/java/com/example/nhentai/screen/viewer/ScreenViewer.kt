package com.example.nhentai.screen.viewer

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.nhentai.DN
import java.lang.Math.max
import java.text.DecimalFormat


var scale = mutableStateOf(1.0f)
var offset = mutableStateOf(Offset(0f, 0f))

@Composable
fun ScreenViewer(navController: NavHostController) {
    val viewModel = vmViewer()

    val scope = rememberCoroutineScope()
    val zoomState = rememberZoomState()

    zoomState.setImageSize(Size(2f, 2f))


    //Если нет адреса оригинала
    if (DN.thumbContainers[DN.selectedPage - 1].urlOriginal.isNullOrEmpty()) {
        viewModel.launchReadOriginalImageFromHref(
            DN.thumbContainers[DN.selectedPage - 1].href.toString(),
            DN.selectedPage - 1
        )

    }


    var zoom by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var centroid by remember { mutableStateOf(Offset.Zero) }
    var angle by remember { mutableStateOf(0f) }

    val decimalFormat = remember { DecimalFormat("0.0") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F1F1F)),
        contentAlignment = Alignment.Center
    )
    {

        if (DN.thumbContainers[DN.selectedPage - 1].urlOriginal != null) {
            val url = DN.thumbContainers[DN.selectedPage - 1].urlOriginal.toString()


//            val address = if (!cacheFileCheck(url)) {
//                url
//            } else {
//                url //URLtoFilePathFile(url)
//            }


            //Timber.i("scale2 ${scale.value} ${offset.value}")


            SubcomposeAsyncImage(
                model = url, //address,
                loading = {
                    CircularProgressIndicator(color = Color.White)
                },
                contentDescription = "stringResource(R.string.description)",
                contentScale = ContentScale.Fit,
                onSuccess = {
                    val p = it.painter.intrinsicSize
                    p
                },

                modifier = Modifier
                    .fillMaxSize()
                    .onSizeChanged { size ->
                        zoomState.setLayoutSize(size.toSize())
                    }
                    .pointerInput(Unit) {

                        detectTransformGestures(

                            onGesture = { gestureCentroid, gesturePan, gestureZoom, _ ->
                                val oldScale = zoom
                                val newScale = zoom * gestureZoom
                                offset =
                                    (offset + gestureCentroid / oldScale) -
                                            (gestureCentroid / newScale + gesturePan / oldScale)
                                zoom = newScale.coerceIn(0.5f..5f)
                                centroid = gestureCentroid
                            }
                        )
                    }
                    .graphicsLayer {
                        translationX = -offset.x * zoom
                        translationY = -offset.y * zoom
                        scaleX = zoom
                        scaleY = zoom
                        rotationZ = angle
                        TransformOrigin(0f, 0f).also { transformOrigin = it }
                    }

            )


        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Magenta)
            )
        }

    }


}


@Stable
class ZoomState(private val maxScale: Float) {
    private var _scale = mutableStateOf(1f)
    val scale: Float
        get() = _scale.value

    private var _offsetX = mutableStateOf(0f)
    val offsetX: Float
        get() = _offsetX.value

    private var _offsetY = mutableStateOf(0f)
    val offsetY: Float
        get() = _offsetY.value

//    fun applyGesture(pan: Offset, zoom: Float) {
//        _scale.value = (_scale.value * zoom).coerceIn(1f, maxScale)
//        _offsetX.value += pan.x
//        _offsetY.value += pan.y
//    }


    private var imageSize = Size.Zero
    fun setImageSize(size: Size) {
        imageSize = size
        updateFitImageSize()
    }

    private var layoutSize = Size.Zero
    fun setLayoutSize(size: Size) {
        layoutSize = size
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
            imageSize * (layoutSize.width / imageSize.width)
        } else {
            imageSize * (layoutSize.height / imageSize.height)
        }
    }


}

@Composable
fun rememberZoomState() = remember { ZoomState(5f) }

