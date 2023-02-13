package com.example.nhentai.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.nhentai.api.readHtmlFromURL
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ScreenInfo(id: Int = 403147, viewModel: vmInfo = hiltViewModel()) {

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
                .background(Color.Green)
        )
        {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
            {

                Spacer(modifier = Modifier.height(8.dp))
                Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
                    AsyncImage(modifier = Modifier
                        .fillMaxWidth(0.7f),
                        model = viewModel.DN.urlCover,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = viewModel.DN.h1.toString())
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = viewModel.DN.h2.toString())
                Spacer(modifier = Modifier.height(8.dp))

                for(i in viewModel.DN.thumbContainers)
                {
                    Spacer(modifier = Modifier.height(2.dp))
                    //Text(text = i.url.toString())


                    Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {

                        AsyncImage(modifier = Modifier
                            //.fillMaxWidth(0.7f)
                            ,
                            model = i.url.toString(),
                            contentDescription = null
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