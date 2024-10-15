package com.ahmzd96.ADM_AL

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Composable
fun ParameterSlider(label: String, value: Float, onValueChange: (Float) -> Unit) {
    Column {
        Text(text = "$label: ${"%.2f".format(value)}", fontSize = 18.sp)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..1f,
            steps = 10,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ParameterSliderPreview() {
    ParameterSlider(label = "Epsilon", value = 0.3f, onValueChange = {})
}
