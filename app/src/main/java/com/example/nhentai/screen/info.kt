package com.example.nhentai.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nhentai.api.readHtmlFromURL
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ScreenInfo(id : Int = 403147, viewModel: vmInfo = hiltViewModel())
{

    DisposableEffect(key1 = viewModel) {
        //viewModel.startLogging()

        viewModel.launchReadFromId()


        onDispose {
            //viewModel.stopAndLogTime()
        }
    }







}