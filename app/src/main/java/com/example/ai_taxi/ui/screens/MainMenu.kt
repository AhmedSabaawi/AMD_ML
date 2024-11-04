package com.example.ai_taxi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ai_taxi.components.ButtonBox
import com.example.ai_taxi.components.logo
import com.example.ai_taxi.resetQTable
import com.example.ai_taxi.viewmodels.TaxiGameViewModel

@Composable
fun MainMenu(navController: NavController, viewModel: TaxiGameViewModel = viewModel()){

    val backgroundColor = Color(0xFFD0D0CF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            logo()
        }

        ButtonBox(text = "START"){
            navController.navigate("Start")
        }
        ButtonBox(text = "MENU") {
            navController.navigate("menu") // Navigate to the Menu screen
        }
        ButtonBox(text = "RESET")
        ButtonBox(text = "QTABLE"){
            navController.navigate( "Qtable" )
        }
    }


}
