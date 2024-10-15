package com.ahmzd96.ADM_AL
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ahmzd96.ADM_AL.viewmodel.TaxiGameViewModel

@Composable
fun RewardGraph(viewModel: TaxiGameViewModel, navController: NavController) {
    val rewardHistory by viewModel.rewardHistory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.LightGray.copy(alpha = 0.2f))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth()
        ) {
            Text("Back to Menu")
        }

        if (rewardHistory.isNotEmpty()) {
            val minReward = rewardHistory.minOrNull() ?: 0f
            val maxReward = rewardHistory.maxOrNull() ?: 1f
            val range = if (maxReward - minReward == 0f) 1f else maxReward - minReward

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(2.dp, Color.Blue, shape = RectangleShape)
                    .padding(8.dp)
                    .background(Color.White)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val stepX = size.width / (rewardHistory.size - 1).coerceAtLeast(1)
                    val stepY = size.height / range
                    val offsetY = (size.height - range * stepY) / 2

                    drawLine(Color.Black, Offset(0f, size.height), Offset(size.width, size.height), strokeWidth = 2f)
                    drawLine(Color.Black, Offset(0f, 0f), Offset(0f, size.height), strokeWidth = 2f)

                    for (i in 1 until rewardHistory.size) {
                        val startX = (i - 1) * stepX
                        val startY = offsetY + (maxReward - rewardHistory[i - 1]) * stepY
                        val endX = i * stepX
                        val endY = offsetY + (maxReward - rewardHistory[i]) * stepY
                        drawLine(Color.Blue, Offset(startX, startY), Offset(endX, endY), strokeWidth = 4f)
                    }

                    for (i in 0..10) {
                        val y = i * (size.height / 10)
                        drawContext.canvas.nativeCanvas.drawText(
                            "%.1f".format(maxReward - i * (range / 10)),
                            5f,
                            y,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.BLACK
                                textSize = 30f
                                textAlign = android.graphics.Paint.Align.LEFT
                            }
                        )
                    }

                    for (i in 0 until rewardHistory.size step (rewardHistory.size / 10).coerceAtLeast(1)) {
                        val x = i * stepX
                        drawContext.canvas.nativeCanvas.drawText(
                            "$i",
                            x,
                            size.height + 30f,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.BLACK
                                textSize = 30f
                                textAlign = android.graphics.Paint.Align.CENTER
                            }
                        )
                    }
                }
            }
        } else {
            Text("No reward data to display", fontSize = 18.sp, color = Color.Red)
        }
    }
}
