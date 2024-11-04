package com.example.ai_taxi.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ai_taxi.viewmodels.TaxiGameViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//@Composable
//fun QTable(navController: NavController, viewModel: TaxiGameViewModel = viewModel()){
//
//    Text("this is qTable 2")
//
//
//}

@Composable
fun QTable( navController: NavController , qValues: Array<FloatArray>) { // Add NavController as a parameter
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp) // Added padding for better spacing
    ) {
        // Back Button to return to the main menu
        Button(
            onClick = { navController.popBackStack() }, // Navigates back to the previous screen
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Back to Menu")
        }
        // Table headers
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                .padding(vertical = 8.dp)
        ) {
            listOf("Nr", "South", "North", "East", "West", "Pick-up", "Drop-off").forEach { header ->
                Text(
                    text = header,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Table rows
        qValues.forEachIndexed { state, actions ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                // State column
                Text(
                    text = "$state",
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onBackground
                )
                // Action values columns
                actions.forEach { qValue ->
                    Text(
                        text = "${"%.2f".format(qValue)}",
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            // Divider between rows
            Divider(color = Color.Gray.copy(alpha = 0.3f), thickness = 1.dp)
        }
    }
}