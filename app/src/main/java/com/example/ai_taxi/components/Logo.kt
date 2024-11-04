package com.example.ai_taxi.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.ai_taxi.R

@Composable
fun logo() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Image(
        painter = painterResource(id = R.drawable.taxi_logo),
        contentDescription = "Taxi Logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .heightIn(max = 0.2f * screenHeight) // Make the logo height proportionate to the screen height (e.g., 20% of the screen height)
            .widthIn(max = 0.4f * screenWidth)  // Make the logo width proportionate to the screen width (e.g., 40% of the screen width)
            .padding(16.dp)                     // Optional padding to create space around the logo
    )
}
