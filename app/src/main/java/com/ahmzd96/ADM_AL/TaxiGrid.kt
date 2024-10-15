package com.ahmzd96.ADM_AL

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahmzd96.ADM_AL.viewmodel.TaxiGameViewModel
@Composable
fun TaxiGridScreen(viewModel: TaxiGameViewModel, navController: NavController) {
    val taxiState = viewModel.taxiState.observeAsState(0).value
    val passengerState = viewModel.passengerState.observeAsState(5).value
    val destinationState = viewModel.destinationState.observeAsState(10).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaxiGrid(
            taxiState = taxiState,
            passengerState = passengerState,
            destinationState = destinationState
        )
        // Back Button to return to the main menu
        Button(
            onClick = { navController.popBackStack() }, // Navigates back to the previous screen
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Back to Menu")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.moveTaxi(0) }) { Text("Up") }
            Button(onClick = { viewModel.moveTaxi(2) }) { Text("Left") }
            Button(onClick = { viewModel.moveTaxi(3) }) { Text("Right") }
            Button(onClick = { viewModel.moveTaxi(1) }) { Text("Down") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Menu")
        }
    }
}

@Composable
fun TaxiGrid(
    taxiState: Int,
    passengerState: Int,
    destinationState: Int,
    gridSize: Int = 5
) {
    // The 5x5 grid where each index represents a cell
    val cells = List(gridSize * gridSize) { it }

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridSize),
        modifier = Modifier.padding(16.dp)
    ) {
        itemsIndexed(cells) { index, _ ->
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .border(2.dp, Color.Black)
                    .background(
                        when (index) {
                            taxiState -> Color.Blue
                            passengerState -> Color.Green
                            destinationState -> Color.Red
                            else -> Color.LightGray
                        }
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaxiGridPreview() {
    TaxiGrid(taxiState = 0, passengerState = 5, destinationState = 10)
}