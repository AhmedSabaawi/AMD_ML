package com.example.ai_taxi.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ai_taxi.MainActivity
import com.example.ai_taxi.R
import com.example.ai_taxi.components.ButtonBox
import com.example.ai_taxi.components.ViewImage
import com.example.ai_taxi.components.logo
import com.example.ai_taxi.resetQTable
import com.example.ai_taxi.viewmodels.TaxiGameViewModel



@Composable
fun MainMenu(navController: NavController, viewModel: TaxiGameViewModel = viewModel()) {

    val backgroundColor = Color(0xFFD0D0CF)
    var showImage by remember { mutableStateOf(false) }
    val imageResId = R.drawable.instructions


    val context = LocalContext.current
    val main = context as MainActivity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            logo()
        }

        ButtonBox(text = "START") {
            navController.navigate("Start")
        }
        ButtonBox(text = "MENU") {
            navController.navigate("menu") // Navigate to the Menu screen
        }
        ButtonBox(text = "RESET") {
            viewModel.resetParameters()
            resetQTable(viewModel.qValues, viewModel.sp)



        }
        ButtonBox(text = "QTABLE") {
            navController.navigate("Qtable")
        }

        Row (horizontalArrangement = Arrangement.spacedBy(56.dp)
            ) {
            IconButton(
                onClick = { showImage = true },
                modifier = Modifier.size(30.dp)
            ) {
                Text(text = "?", fontSize = 24.sp, color = Color.Black)
            }

            // Show Image when showImage is true
            if (showImage) {
                ViewImage(imageResId = imageResId) {
                    showImage = false // Dismiss the image view when needed
                }
            }

            //Changes the picture of the button depending on the state (if it is muted or not)
            val muteButtonPicture = if (viewModel.muted.collectAsState().value == false){
                painterResource(id = R.drawable.speaker)}
            else{
                painterResource(id = R.drawable.muted)
            }
            IconButton(
                onClick = { main.muteMusic()  },
                modifier = Modifier.size(30.dp)
            ) {
                Image(
                    painter = muteButtonPicture,
                    contentDescription = "mute button",
                    modifier = Modifier.size(30.dp),

                )
            }
        }


    }
}
