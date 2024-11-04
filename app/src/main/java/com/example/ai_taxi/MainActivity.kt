package com.example.ai_taxi

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ai_taxi.ui.theme.AI_TaxiTheme
import com.example.ai_taxi.ui.screens.QTable
import com.example.ai_taxi.ui.screens.Menu
import com.example.ai_taxi.ui.screens.MainMenu
import com.example.ai_taxi.ui.screens.Start
import com.example.ai_taxi.viewmodels.TaxiGameViewModel

class MainActivity : ComponentActivity() {
    private lateinit var mp: MediaPlayer
    private var mediaplayerInitialized = false

    //Initiates the apps viewmodel, from here its gets passed to each component that needs the apps info
    private val taxiGameViewModel:  TaxiGameViewModel by viewModels()

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
                    composable("main") { MainMenu(navController, taxiGameViewModel) }
                    composable("menu") { Menu(navController, taxiGameViewModel) }
                    composable("qtable") { QTable(navController,taxiGameViewModel.qValues) }
                    composable ("Start"){ Start(navController, taxiGameViewModel)  }
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



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AI_TaxiTheme {
        MainMenu(rememberNavController())
    }
}}
