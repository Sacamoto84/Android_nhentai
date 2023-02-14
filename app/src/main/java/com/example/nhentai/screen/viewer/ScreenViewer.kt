package com.example.nhentai.screen.viewer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ScreenViewer(navController: NavHostController, id : Int,  currertPage : Int, countPage : Int)
{
    val viewModel = vmViewer()
    
    Box(modifier = Modifier.size(100.dp).background(Color.Green))





}