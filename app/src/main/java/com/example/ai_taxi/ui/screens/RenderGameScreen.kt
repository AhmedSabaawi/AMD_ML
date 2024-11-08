package com.example.ai_taxi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ai_taxi.R
import com.example.ai_taxi.components.ButtonBox
import com.example.ai_taxi.viewmodels.TaxiGameViewModel




@Composable
fun RenderGameScreen(
    navController: NavController,
    viewModel: TaxiGameViewModel = viewModel()
) {
    val xPosition by viewModel.x.collectAsState()
    val yPosition by viewModel.y.collectAsState()
    val pickedUp by viewModel.pickedUp.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startGame()
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier= Modifier
            .background(Color(0xFFD0D0CF))
            .fillMaxSize()
            .zIndex(0f)
            .aspectRatio(1f)
            .paint(
                painterResource(if (pickedUp) R.drawable.backroundnopass else (R.drawable.backround))


            )){
        Box( modifier = Modifier
            .fillMaxSize(0.86f)

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
    Box(modifier = Modifier
        .padding(100.dp)
    ){
        ButtonBox(
            "Go back "){navController.popBackStack()}
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
