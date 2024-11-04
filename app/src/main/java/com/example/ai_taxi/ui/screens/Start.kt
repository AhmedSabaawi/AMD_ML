package com.example.ai_taxi.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ai_taxi.viewmodels.TaxiGameViewModel


@Composable
fun Start(navController: NavController, viewModel: TaxiGameViewModel = viewModel()){

    RenderGameScreen(viewModel)
}