package com.example.ai_taxi.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ai_taxi.R
import com.example.ai_taxi.viewmodels.RenderGameViewModel

@Composable
fun RenderGameScreen(
    viewModel: RenderGameViewModel = viewModel()
) {
    val xPosition by viewModel.x.collectAsState()
    val yPosition by viewModel.y.collectAsState()



//    Row { Button(onClick ={viewModel.setValue(
//        xPosition+1,
//        yPosition+0)},
//        modifier = Modifier
//            .size(100.dp)
//            .zIndex(2f))
//        {
//        Text(text = "Move")
//        }
//        Button(onClick ={viewModel.setValue(0,0)},
//            modifier = Modifier
//                .size(100.dp)
//                .zIndex(2f))
//        {
//            Text(text = "Reset")
//        }
//        Text(text = "x=$xPosition, y=$yPosition")
//    }






    Box( modifier= Modifier
        .fillMaxSize()
        .aspectRatio(5/3f)
        .paint(painterResource(id = R.drawable.chart),
            contentScale = ContentScale.FillBounds)
    ){

        Box(

            modifier = Modifier
                .size(50.dp)
                .zIndex(1f)
                .offsetByPercent(
                    (xPosition/5).toFloat(),
                    (yPosition/3).toFloat())
            .paint(painterResource(id=(R.drawable.taxi)))
        )

    }
}

@Composable
fun Modifier.offsetByPercent(xp: Float,yp:Float) = this.then(
    Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(
                x = (constraints.maxWidth * xp).toInt(),
                y = (constraints.maxHeight * yp).toInt()
            )
        }
    }
)
