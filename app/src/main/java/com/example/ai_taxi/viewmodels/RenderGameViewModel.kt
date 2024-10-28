package com.example.ai_taxi.viewmodels

import androidx.lifecycle.ViewModel
import com.example.ai_taxi.trainTaxiGame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RenderGameViewModel(private val taxiGameViewModel: TaxiGameViewModel): ViewModel() {
    private val _x = MutableStateFlow(0)
    val x: StateFlow<Int> =_x
    private val _y = MutableStateFlow(0)
    val y: StateFlow<Int> =_y


    private val actions = listOf("south", "north", "east", "west", "pick-up", "drop-off")
    private val actionCoordinates = mapOf(
        "south" to Pair(0, -1),
        "north" to Pair(0, 1),
        "east" to Pair(1, 0),
        "west" to Pair(-1, 0),
        "pick-up" to Pair(0, 0),
        "drop-off" to Pair(0, 0)
    )
    init {
//        trainTaxiGame(taxiGameViewModel.sp, taxiGameViewModel.qValues, taxiGameViewModel.gamma)
        val coordinatesList = processQValues()
        iterateThroughList(coordinatesList)
    }

    private fun iterateThroughList(coordinatesList: List<Pair<Int, Int>>) {
        for(i in coordinatesList.indices) {
            val pair = coordinatesList[i]
            _x.value += pair.first
            _y.value += pair.second
//            Thread.sleep(500)
        }
    }

    private fun findBestActionCoordinates(state: Int): Pair<Int, Int> {
        // Start at index 0
        var bestActionIndex = 0
        // Set index  0 to be the highest value
        var maxValue = taxiGameViewModel.qValues[state][0]
        // Loop over all the actions in a single state (row)
        // The highest value will be set to the best action index variable
        for (action in 1 until taxiGameViewModel.qValues[state].size) {
            // If the current value at action is highest than current maxvalue,
            // Set the maxValue to the new value
            // Set the new best action index  to the current action index
            if(taxiGameViewModel.qValues[state][action] > maxValue) {
                maxValue = taxiGameViewModel.qValues[state][action]
                bestActionIndex = action
            }
        }
        // Best action will be retrieved based on the index from the list
        val bestAction = actions[bestActionIndex]
        // The coordinates will be retrieved based on the best action from the map
        val coordinates = actionCoordinates[bestAction] ?: Pair(0, 0)
        // return the coordinates
        return coordinates
    }


    fun processQValues(): List<Pair<Int, Int>> {
        val coordinatesList = mutableListOf<Pair<Int, Int>>()

        for (state in taxiGameViewModel.qValues.indices) {
            val coordinates = findBestActionCoordinates(state)
            coordinatesList.add(coordinates)
        }
        return coordinatesList
    }
}