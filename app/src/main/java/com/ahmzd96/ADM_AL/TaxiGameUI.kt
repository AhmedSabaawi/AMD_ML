package com.ahmzd96.ADM_AL

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ahmzd96.ADM_AL.viewmodel.TaxiGameViewModel
import kotlinx.coroutines.launch

@Composable
fun TaxiGameApp(viewModel: TaxiGameViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(viewModel, navController)
        }
        composable("table_screen") {
            QTableView(qValues = viewModel.qValues, navController = navController)
        }
        composable("graph_screen") {
            RewardGraph(viewModel, navController)
        }
        composable("the_Grid") {
            TaxiGridScreen(viewModel, navController)
        }
    }
}

@Composable
fun MainScreen(viewModel: TaxiGameViewModel, navController: NavController) {
    val epsilon by viewModel.epsilon.collectAsState()
    val alpha by viewModel.alpha.collectAsState()
    val gamma by viewModel.gamma.collectAsState()
    val decay by viewModel.decay.collectAsState()
    val rewardHistory by viewModel.rewardHistory.collectAsState()

    var currentTaxiState by remember { mutableStateOf(0) }
    var passengerState by remember { mutableStateOf(5) }
    var destinationState by remember { mutableStateOf(10) }

    var trainingCompleted by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Modify Parameters", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // Sliders for epsilon, alpha, gamma, decay
        ParameterSlider(label = "Epsilon", value = epsilon, onValueChange = { viewModel.updateEpsilon(it) })
        ParameterSlider(label = "Alpha", value = alpha, onValueChange = { viewModel.updateAlpha(it) })
        ParameterSlider(label = "Gamma", value = gamma, onValueChange = { viewModel.updateGamma(it) })
        ParameterSlider(label = "Decay", value = decay, onValueChange = { viewModel.updateDecay(it) })
        Spacer(modifier = Modifier.height(16.dp))

        // Button to start training
        Button(onClick = {
            coroutineScope.launch {
                resetQTable(viewModel.qValues, viewModel.sp)
                val newRewardHistory = trainTaxiGame(
                    viewModel.sp, viewModel.qValues, epsilon, alpha, gamma, decay, visualization = false
                )
                viewModel.updateRewardHistory(newRewardHistory)
                trainingCompleted = true
            }
        }) {
            Text("Start Training")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation buttons to view the Q-Table or Graph
        Row {
            Button(onClick = { navController.navigate("table_screen") }) {
                Text("View Q-Table")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { navController.navigate("graph_screen") }) {
                Text("View Reward Graph")
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {

            Button(onClick = { navController.navigate("the_Grid") }) {
                Text("Play Game")

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display training complete message
        if (trainingCompleted) {
            Text(text = "Training Completed!", color = Color.Green, fontSize = 20.sp)
        }
    }
}
