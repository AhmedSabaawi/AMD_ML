package com.example.ai_taxi.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.ai_taxi.components.SliderItem
import com.example.ai_taxi.components.SliderValues

class SliderViewModel : ViewModel() {


    var sliders = mutableStateListOf(
        SliderValues("Gamma", 0.0f),
        SliderValues("Alpha", 0.0f),
        SliderValues("Epsilon", 0.0f),
        SliderValues("Decay", 0.0f)
    )

    fun saveValue(valueName: String){
        sliders.find { it.valueName == }
    }

    fun loadValue(){}
}