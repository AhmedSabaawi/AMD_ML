package com.example.ai_taxi.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow


class RenderGameViewModel : ViewModel() {
    private val _x = MutableStateFlow(0)
    val x: StateFlow<Int> =_x
    private val _y = MutableStateFlow(0)

    val y: StateFlow<Int> =_y

    init {
        _x.value=0
        _y.value=0

    }

    fun setValue(newX:Int, newY:Int){

        _x.value=newX
        _y.value=newY
    }



}