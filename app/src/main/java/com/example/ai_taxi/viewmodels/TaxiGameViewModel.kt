package com.example.ai_taxi.viewmodels


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ai_taxi.trainTaxiGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.log

class
TaxiGameViewModel : ViewModel() {
    private val _rewardHistory = MutableStateFlow(listOf<Float>())
    val rewardHistory: StateFlow<List<Float>> get() = _rewardHistory
    private val xStart = 1
    private val yStart = 3
    private val _x = MutableStateFlow(xStart)
    val x: StateFlow<Int> =_x
    private val _y = MutableStateFlow(yStart)
    val y: StateFlow<Int> =_y


    private var _pickedUp =MutableStateFlow(false)
    val pickedUp: StateFlow<Boolean> = _pickedUp

    private val _trained= MutableStateFlow(false)
    val trained: StateFlow<Boolean> = _trained

    private val _muted = MutableStateFlow(false)
    val muted: StateFlow<Boolean> = _muted

    fun updateMute(newMute: Boolean) {
        _muted.value = newMute
    }


    private val actions = listOf("south", "north", "east", "west", "pick-up", "drop-off")
    private val actionCoordinates = mapOf(
        "south" to Pair(0, 1),
        "north" to Pair(0, -1),
        "east" to Pair(1, 0),
        "west" to Pair(-1, 0),
        "pick-up" to Pair(0, 0),
        "drop-off" to Pair(0, 0)
    )


    val sp = arrayOf(
        intArrayOf(5, 0, 1, 0, -1, -1), intArrayOf(6, 1, 1, 0, -1, -1), intArrayOf(7, 2, 3, 2, -1, -1), intArrayOf(8, 3, 4, 2, -1, -1), intArrayOf(9, 4, 4, 3, -1, -1),
        intArrayOf(10, 0, 6, 5, -1, -1), intArrayOf(11, 1, 7, 5, -1, -1), intArrayOf(12, 2, 8, 6, -1, -1), intArrayOf(13, 3, 9, 7, -1, -1), intArrayOf(14, 4, 9, 8, -1, -1),
        intArrayOf(15, 5, 11, 10, -1, -1), intArrayOf(16, 6, 12, 10, -1, -1), intArrayOf(17, 7, 13, 11, -1, -1), intArrayOf(18, 8, 14, 12, -1, -1), intArrayOf(19, 9, 14, 13, -1, -1),
        intArrayOf(20, 10, 15, 15, -1, -1), intArrayOf(21, 11, 17, 16, -1, -1), intArrayOf(22, 12, 17, 16, -1, -1), intArrayOf(23, 13, 19, 18, -1, -1), intArrayOf(24, 14, 19, 18, -1, -1),
        intArrayOf(20, 15, 20, 20, -1, -1), intArrayOf(21, 16, 22, 21, -1, -1), intArrayOf(22, 17, 22, 21, -1, -1), intArrayOf(23, 18, 24, 23, 48, -1), intArrayOf(24, 19, 24, 23, -1, -1),
        intArrayOf(30, 25, 26, 25, -1, -1), intArrayOf(31, 26, 26, 25, -1, -1), intArrayOf(32, 27, 28, 27, -1, -1), intArrayOf(33, 28, 29, 27, -1, -1), intArrayOf(34, 29, 29, 28, -1, -1),
        intArrayOf(35, 25, 31, 30, -1, -1), intArrayOf(36, 26, 32, 30, -1, -1), intArrayOf(37, 27, 33, 31, -1, -1), intArrayOf(38, 28, 34, 32, -1, -1), intArrayOf(39, 29, 34, 33, -1, -1),
        intArrayOf(40, 30, 36, 35, -1, -1), intArrayOf(41, 31, 37, 35, -1, -1), intArrayOf(42, 32, 38, 36, -1, -1), intArrayOf(43, 33, 39, 37, -1, -1), intArrayOf(44, 34, 39, 38, -1, -1),
        intArrayOf(45, 35, 40, 40, -1, -1), intArrayOf(46, 36, 42, 41, -1, -1), intArrayOf(47, 37, 42, 41, -1, -1), intArrayOf(48, 38, 44, 43, -1, -1), intArrayOf(49, 39, 44, 43, -1, -1),
        intArrayOf(45, 40, 45, 45, -1, -1), intArrayOf(46, 41, 47, 46, -1, -1), intArrayOf(47, 42, 47, 46, -1, -1), intArrayOf(48, 43, 49, 48, -1, -1), intArrayOf(49, 44, 49, 48, -1, -1)
    )

    val qValues = Array(50) { FloatArray(6) }

    private val _epsilon = MutableStateFlow(0.3f)
    val epsilon: StateFlow<Float> = _epsilon

    private val _alpha = MutableStateFlow(0.2f)
    val alpha: StateFlow<Float> = _alpha

    private val _gamma = MutableStateFlow(0.9f)
    val gamma: StateFlow<Float> = _gamma

    private val _decay = MutableStateFlow(0.01f)
    val decay: StateFlow<Float> = _decay

    private val _visualization = MutableStateFlow(false)
    val visualization: StateFlow<Boolean> = _visualization

    fun updateRewardHistory(newHistory: List<Float>) {
        _rewardHistory.value = newHistory
    }

    fun updateEpsilon(newEpsilon: Float) {
        _epsilon.value = newEpsilon
    }

    fun updateAlpha(newAlpha: Float) {
        _alpha.value = newAlpha
    }

    fun updateGamma(newGamma: Float) {
        _gamma.value = newGamma
    }

    fun updateDecay(newDecay: Float) {
        _decay.value = newDecay
    }

    fun updateTrained(newTrained: Boolean){
        _trained.value = newTrained
    }
    fun resetParameters(){
        updateGamma(0.2f)
        updateAlpha(0.2f)
        updateEpsilon(0.3f)
        updateDecay(0.01f)
    }


    fun getEpsilon():Float{ return _epsilon.value}
    fun getAlpha():Float{return _alpha.value}
    fun getGamma():Float{return _gamma.value}
    fun getDecay():Float{return _decay.value}


    fun  updateVisualization(newVisualization: Boolean) {
        _visualization.value = newVisualization
    }

    fun trainGame (){
        trainTaxiGame(sp, qValues, alpha.value, epsilon.value, gamma.value, decay.value, visualization = false)
        _trained.value=true
    }

    fun startGame() {
        if (!trained.value){
            trainGame()
        }
        _x.value=xStart
        _y.value=yStart
        _pickedUp.value=false

        val coordinatesList = processQValues()
        iterateThroughList(coordinatesList)
    }

    private val _taxiState = MutableLiveData(0) // Initial taxi state
    val taxiState: LiveData<Int> = _taxiState

    private val _passengerState = MutableLiveData(5) // Passenger location
    val passengerState: LiveData<Int> = _passengerState

    private val _destinationState = MutableLiveData(10) // Destination location
    val destinationState: LiveData<Int> = _destinationState


    // Move taxi based on direction (0: Up, 1: Down, 2: Left, 3: Right)
    fun moveTaxi(direction: Int) {
        val currentTaxiState = _taxiState.value ?: 0
        val newState = when (direction) {
            0 -> currentTaxiState - 5 // Move up
            1 -> currentTaxiState + 5 // Move down
            2 -> currentTaxiState - 1 // Move left
            3 -> currentTaxiState + 1 // Move right
            else -> currentTaxiState
        }

        if (newState in 0..24) { // Ensure the new state is within the grid boundaries
            _taxiState.value = newState
        }
    }

    fun updateGameState(taxi: Int, passenger: Int, destination: Int) {
        _taxiState.postValue(taxi)
        _passengerState.postValue(passenger)
        _destinationState.postValue(destination)
    }

    private fun toIndex(x: Int, y: Int) : Int {
        var index = (y * 5) + x
        if(pickedUp.value) {
            index += 25
        }
        return index
    }

    private fun iterateThroughList(coordinatesList: List<Pair<Int, Int>>) {
        var index: Int
        var pair: Pair<Int, Int>

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000L)
            for (i in 0 until 50) {
                index = toIndex(x.value, y.value)
                if(index == 23) {
                    _pickedUp.value = true
                }
                if(index == 45) {
                    break
                }
                pair = coordinatesList[index]
                _x.value += pair.first
                _y.value += pair.second
                Log.d("Coordinates", "X: ${_x.value}, Y: ${_y.value}")
                Log.d("Index", "Index: ${index}")
                delay(1000L)
            }
        }
    }

    private fun findBestActionCoordinates(state: Int): Pair<Int, Int> {
        // Start at index 0
        var bestActionIndex = 0
        // Set index  0 to be the highest value
        var maxValue = qValues[state][0]
        // Loop over all the actions in a single state (row)
        // The highest value will be set to the best action index variable
        for (action in 1 until qValues[state].size) {
            // If the current value at action is highest than current maxvalue,
            // Set the maxValue to the new value
            // Set the new best action index  to the current action index
            if(qValues[state][action] > maxValue) {
                maxValue = qValues[state][action]
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
        for (state in qValues.indices) {
            val coordinates = findBestActionCoordinates(state)
            coordinatesList.add(coordinates)
        }
        return coordinatesList
    }
}