package com.ahmzd96.ADM_AL


 import android.os.Bundle
 import androidx.activity.ComponentActivity
 import androidx.activity.compose.setContent
 import androidx.lifecycle.viewmodel.compose.viewModel
 import com.ahmzd96.ADM_AL.viewmodels.TaxiGameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Obtain the ViewModel
            val viewModel: TaxiGameViewModel = viewModel()
            TaxiGameApp(viewModel)
        }
    }
}
