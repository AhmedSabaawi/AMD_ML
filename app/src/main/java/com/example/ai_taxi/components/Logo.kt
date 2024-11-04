package com.example.ai_taxi.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.ai_taxi.R

@Composable
fun logo(){
    Image(
        painter = painterResource(id = R.drawable.taxi_logo),
        contentDescription = "Taxi Logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier.fillMaxSize()
    )
}