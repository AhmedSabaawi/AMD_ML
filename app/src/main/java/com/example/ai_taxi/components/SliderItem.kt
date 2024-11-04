package com.example.ai_taxi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ai_taxi.viewmodels.TaxiGameViewModel
import com.example.ai_taxi.MenuValues

@Composable
fun SliderItem(title: String, description: String, viewModel: TaxiGameViewModel = viewModel()) {

    fun saveData(newValue: Float) {
        when (MenuValues.valueOf(title.uppercase())) {
            MenuValues.GAMMA -> viewModel.updateGamma(newValue)
            MenuValues.EPSILON -> viewModel.updateEpsilon(newValue)
            MenuValues.DECAY -> viewModel.updateDecay(newValue)
            MenuValues.ALPHA -> viewModel.updateAlpha(newValue)
        }
    }

    fun loadData(): Float {
        return when (MenuValues.valueOf(title.uppercase())) {
            MenuValues.GAMMA -> viewModel.getGamma()
            MenuValues.EPSILON -> viewModel.getEpsilon()
            MenuValues.DECAY -> viewModel.getDecay()
            MenuValues.ALPHA -> viewModel.getAlpha()
        }
    }

    var sliderValue by remember { mutableStateOf(loadData()) }
    var showDescription by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 4.dp), // Reduced horizontal padding for compactness
        horizontalAlignment = Alignment.Start
    ) {
        // Title row with title at start, value in the middle, and help icon at the end
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 2.dp) // Minimal padding between title and slider
        ) {
            // Title with dynamic width that adapts to content length
            Text(
                text = title,
                fontSize = 18.sp, // Bold font size for title
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp) // Padding after title for spacing
            )

            Spacer(modifier = Modifier.weight(1f)) // Spacer to push value and icon to their respective positions

            // Real slider value in the center
            Text(
                text = String.format("%.2f", sliderValue), // Display current value
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 100.dp) // Padding for spacing from the icon
            )

            // Help icon at the end
            IconButton(onClick = { showDescription = true }) {
                Text(
                    text = "?",
                    fontSize = 18.sp, // Bold font size for question mark
                    color = Color.Black
                )
            }
        }

        // Row with range values and slider in between
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp), // Reduced padding for compactness
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Minimum value before the slider
            Text(
                text = "0",
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 4.dp)
            )

            // Slider
            Slider(
                value = sliderValue,
                onValueChange = {
                    sliderValue = it
                    saveData(it) // Saves the value
                },
                valueRange = 0f..1f,
                steps = 100,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Black,
                    activeTrackColor = Color.Black,
                    inactiveTrackColor = Color.LightGray
                ),
                modifier = Modifier
                    .weight(1f) // Allow slider to take up available space
                    .padding(horizontal = 2.dp) // Reduced padding for a compact layout
            )

            // Maximum value after the slider
            Text(
                text = "1.00",
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        // Description dialog
        if (showDescription) {
            AlertDialog(
                onDismissRequest = { showDescription = false },
                title = { Text(text = title, fontSize = 18.sp) }, // Bold font size for title
                text = { Text(text = description, fontSize = 16.sp) }, // Bold font size for dialog text
                confirmButton = {
                    Button(
                        onClick = { showDescription = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("OK", color = Color.White, fontSize = 16.sp) // Set text color to white and bold font size
                    }
                },
                modifier = Modifier
                    .padding(8.dp) // Reduced padding for dialog
                    .fillMaxWidth(0.9f) // Dialog width takes up 90% of screen width
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SliderItemPreview() {
    SliderItem(title = "Gamma", description = "Controls the gamma setting.")


}
