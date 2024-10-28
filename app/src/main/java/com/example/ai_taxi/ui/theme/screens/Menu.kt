package com.example.ai_taxi.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ai_taxi.components.ButtonBox
import com.example.ai_taxi.components.SliderItem
import com.example.ai_taxi.components.logo

@Composable
fun Menu(navController: NavController){

    val backgroundColor = Color(0xFFD0D0CF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            logo()
        }

        // Slider items with descriptions
        SliderItem(title = "Gamma", description = "Controls the gamma setting.")
        SliderItem(title = "Alpha", description = "Adjusts the alpha parameter.")
        SliderItem(title = "Epsilon", description = "Sets the epsilon value.")
        SliderItem(title = "Decay", description = "Influences the decay rate.")

        // Back button
        ButtonBox(text = "Back") {
            navController.popBackStack() // Navigate back to the previous screen
        }
    }


}