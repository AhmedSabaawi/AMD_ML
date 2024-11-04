package com.example.ai_taxi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ai_taxi.components.ButtonBox
import com.example.ai_taxi.components.SliderItem
import com.example.ai_taxi.components.logo
import com.example.ai_taxi.viewmodels.TaxiGameViewModel

@Composable
fun Menu(navController: NavController, viewModel: TaxiGameViewModel = viewModel()) {
//    var gamma by remember { mutableStateOf(Float) }
    val backgroundColor = Color(0xFFD0D0CF)
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

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
                .heightIn(min = 0.dp, max = 0.3f * screenHeight) // Dynamic height for better adaptability
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            logo()
        }

        // Flexible slider items
        SliderItem(title = "Gamma", description = "Controls the gamma setting.", viewModel)
        SliderItem(title = "Alpha", description = "Adjusts the alpha parameter.", viewModel)
        SliderItem(title = "Epsilon", description = "Sets the epsilon value.", viewModel)
        SliderItem(title = "Decay", description = "Influences the decay rate.", viewModel)


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 32.dp), // Padding on both sides of the Row
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // First Column with START button at the center
            Column(
                modifier = Modifier
                    .weight(1f) // Distribute space equally between columns
                    .fillMaxHeight(), // Make column take up full height of the Row
                horizontalAlignment = Alignment.CenterHorizontally, // Center content horizontally in the column
                verticalArrangement = Arrangement.Center // Center content vertically in the column
            ) {
                Button(
                    onClick = {
                         navController.popBackStack()
                    }, // Navigates back to the previous screen
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 15.dp, start = 15 .dp)
                ) {
                    Text("Save", color = Color.White)
                }
            }

            // Second Column with MENU button at the center
            Column(
                modifier = Modifier
                    .weight(1f) // Distribute space equally between columns
                    .fillMaxHeight(), // Make column take up full height of the Row
                horizontalAlignment = Alignment.CenterHorizontally, // Center content horizontally in the column
                verticalArrangement = Arrangement.Center // Center content vertically in the column
            ) {
                Button(
                    onClick = {
                      viewModel.resetParameters()
                        navController.navigate("MENU")
                    }, // Navigates back to the previous screen
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp , end = 15.dp)
                ) {
                    Text("Default", color = Color.White)
                }
            }
        }


    }


}

@Preview
@Composable
fun MenuPreview() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 32.dp), // Padding on both sides of the Row
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // First Column with START button at the center
        Column(
            modifier = Modifier
                .weight(1f) // Distribute space equally between columns
                .background(Color.Red) // Set background color of the column
                .fillMaxHeight(), // Make column take up full height of the Row
            horizontalAlignment = Alignment.CenterHorizontally, // Center content horizontally in the column
            verticalArrangement = Arrangement.Center // Center content vertically in the column
        ) {
            Button(
                onClick = {
                    // navController.popBackStack()
                }, // Navigates back to the previous screen
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp, start = 15 .dp)
            ) {
                Text("Save", color = Color.White)
            }
        }

        // Second Column with MENU button at the center
        Column(
            modifier = Modifier
                .weight(1f) // Distribute space equally between columns
                .background(Color.Blue) // Set background color of the column
                .fillMaxHeight(), // Make column take up full height of the Row
            horizontalAlignment = Alignment.CenterHorizontally, // Center content horizontally in the column
            verticalArrangement = Arrangement.Center // Center content vertically in the column
        ) {
            Button(
                onClick = {
                    // navController.popBackStack()
                }, // Navigates back to the previous screen
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp , end = 15.dp)
            ) {
                Text("Save", color = Color.White)
            }
        }
    }

}