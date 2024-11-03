package com.example.ai_taxi.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SliderItem(title: String, description: String) {
    var sliderValue by remember { mutableStateOf(0f) }
    var showDescription by remember { mutableStateOf(false) } // For controlling the description dialog

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { showDescription = true }) {
                Text(text = "?", fontSize = 18.sp, color = Color.Black) // Question mark icon
            }
        }

        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = 0f..1f,
            steps = 100,
            colors = SliderDefaults.colors(
                thumbColor = Color.Black,
                activeTrackColor = Color.Black,
                inactiveTrackColor = Color.LightGray
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Display the current slider value
        Text(
            text = String.format("%.2f", sliderValue), // Display as integer
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Display the scale
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "0", fontSize = 12.sp)
            Text(text = "1.00", fontSize = 12.sp)
        }

        // Description Dialog
        if (showDescription) {
            AlertDialog(
                onDismissRequest = { showDescription = false },
                title = { Text(text = title) },
                text = { Text(text = description) },
                confirmButton = {
                    Button(onClick = { showDescription = false }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                        Text("OK", color = Color.White) // Set the text color to white
                    }
                }
            )
        }
    }
}