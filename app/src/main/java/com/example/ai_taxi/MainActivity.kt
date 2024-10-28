package com.example.ai_taxi

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ai_taxi.ui.theme.AI_TaxiTheme
import com.example.ai_taxi.ui.screens.QTable
import com.example.ai_taxi.ui.screens.Menu
import com.example.ai_taxi.ui.screens.MainMenu
import com.example.ai_taxi.ui.screens.Start

class MainActivity : ComponentActivity() {
    private lateinit var mp: MediaPlayer
    private var mediaplayerInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mp = MediaPlayer.create(this, R.raw.background_music)
            mp.isLooping = true
            mp.start()
            mediaplayerInitialized = true
            AI_TaxiTheme {
                // Initialize Navigation
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainMenu(navController) }
                    composable("menu") { Menu(navController) }
                    composable("qtable") { QTable(navController)  }
                    composable ("Start"){ Start(navController)  }
                }

            }
        }
    }
    //What the app does when the user ends its process (quits the app)
    override fun onDestroy() {
        super.onDestroy()

        if (this::mp.isInitialized){
            mp.release()
        }
    }

    //what it does when you tab out from the app
    override fun onPause() {
        super.onPause()


//        if (mp.isPlaying){
//            try {
//                mp.pause()
//            }catch (e: IllegalStateException){
//                e.printStackTrace()
//            }
//
//        }



    }


    //what it does when you tab back in to the app
    override fun onResume() {
        super.onResume()

//        try {
//            mp.start()
//        }catch (e: IllegalStateException){
//            e.printStackTrace()
//        }
//
//    }


}

    //TODO: (Optional) Make a fun for mediaplayer so its accessible in one place.


//@Composable
//fun MainScreen(navController: NavController) {
//    val backgroundColor = Color(0xFFD0D0CF)
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(backgroundColor)
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceEvenly
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(150.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.taxi_logo),
//                contentDescription = "Taxi Logo",
//                contentScale = ContentScale.Fit,
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//
//        ButtonBox(text = "START")
//        ButtonBox(text = "MENU") {
//            navController.navigate("menu") // Navigate to the Menu screen
//        }
//        ButtonBox(text = "RESET")
//        ButtonBox(text = "QTABLE"){
//            navController.navigate("qtable")
//        }
//    }
//}
//
//@Composable
//fun MenuScreen(navController: NavController) {
//    val backgroundColor = Color(0xFFD0D0CF)
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(backgroundColor)
//            .padding(16.dp)
//            .verticalScroll(rememberScrollState()),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(150.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.taxi_logo),
//                contentDescription = "Taxi Logo",
//                contentScale = ContentScale.Fit,
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//
//        // Slider items with descriptions
//        SliderItem(title = "Gamma", description = "Controls the gamma setting.")
//        SliderItem(title = "Alpha", description = "Adjusts the alpha parameter.")
//        SliderItem(title = "Epsilon", description = "Sets the epsilon value.")
//        SliderItem(title = "Decay", description = "Influences the decay rate.")
//
//        // Back button
//        ButtonBox(text = "Back") {
//            navController.popBackStack() // Navigate back to the previous screen
//        }
//    }
//}
//
//@Composable
//fun SliderItem(title: String, description: String) {
//    var sliderValue by remember { mutableStateOf(0f) }
//    var showDescription by remember { mutableStateOf(false) } // For controlling the description dialog
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        horizontalAlignment = Alignment.Start
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = title,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.weight(1f)
//            )
//            IconButton(onClick = { showDescription = true }) {
//                Text(text = "?", fontSize = 18.sp, color = Color.Black) // Question mark icon
//            }
//        }
//
//        Slider(
//            value = sliderValue,
//            onValueChange = { sliderValue = it },
//            valueRange = 0f..1f,
//            steps = 100,
//            colors = SliderDefaults.colors(
//                thumbColor = Color.Black,
//                activeTrackColor = Color.Black,
//                inactiveTrackColor = Color.LightGray
//            ),
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        // Display the current slider value
//        Text(
//            text = String.format("%.2f", sliderValue), // Display as integer
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(vertical = 4.dp)
//        )
//
//        // Display the scale
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(text = "0", fontSize = 12.sp)
//            Text(text = "100", fontSize = 12.sp)
//        }
//
//        // Description Dialog
//        if (showDescription) {
//            AlertDialog(
//                onDismissRequest = { showDescription = false },
//                title = { Text(text = title) },
//                text = { Text(text = description) },
//                confirmButton = {
//                    Button(onClick = { showDescription = false }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
//                        Text("OK", color = Color.White) // Set the text color to white
//                    }
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun ButtonBox(text: String, onClick: () -> Unit = {}) {
//    Button(
//        onClick = onClick,
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(50.dp)
//            .padding(horizontal = 32.dp),
//        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
//        shape = RoundedCornerShape(16.dp)
//    ) {
//        Text(
//            text = text,
//            color = Color.White,
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AI_TaxiTheme {
        MainMenu(rememberNavController())
    }
}}
