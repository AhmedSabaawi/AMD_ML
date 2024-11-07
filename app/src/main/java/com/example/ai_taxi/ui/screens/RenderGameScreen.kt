package com.example.ai_taxi.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ai_taxi.R
import com.example.ai_taxi.viewmodels.TaxiGameViewModel




@Composable
fun RenderGameScreen(
    viewModel: TaxiGameViewModel = viewModel()
) {
    val xPosition by viewModel.x.collectAsState()
    val yPosition by viewModel.y.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startGame()
    }


    Box( modifier= Modifier
        .background(Color(0xFFD0D0CF))
        .fillMaxSize()
        .aspectRatio(1f)
        .zIndex(0f)
        .paint(painterResource(R.drawable.backround)),
        contentAlignment = Alignment.Center

    ){
        Box( modifier = Modifier
            .fillMaxSize(0.858f)


            .offsetByPercent(xPosition, yPosition)
        ){
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .zIndex(1f)

                    .paint(painterResource(id = (R.drawable.taxi)))
            )
        }
    }

}

@Composable
fun Modifier.offsetByPercent(x:Int,y:Int) = this.then(
    Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {
            val offsetX= (constraints.maxWidth)/5
            val offsetY= (constraints.maxHeight)/5
            placeable.placeRelative(
                x = offsetX*x,
                y = offsetY*y
            )
        }
    }
)
