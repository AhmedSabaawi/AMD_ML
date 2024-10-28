package com.ahmzd96.ADM_AL.viewmodels

import androidx.lifecycle.ViewModel

class RenderGameViewModel(private val taxiGameViewModel: TaxiGameViewModel): ViewModel() {


    private val actions = listOf("south", "north", "east", "west", "pick-up", "drop-off")
    private val actionCoordinates = mapOf(
        "south" to Pair(0, -1),
        "north" to Pair(0, 1),
        "east" to Pair(1, 0),
        "west" to Pair(-1, 0),
        "pick-up" to Pair(0, 0),
        "drop-off" to Pair(0, 0)
    )

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