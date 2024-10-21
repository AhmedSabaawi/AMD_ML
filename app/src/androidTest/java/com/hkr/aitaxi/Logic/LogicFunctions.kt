package com.hkr.aitaxi.Logic

import android.util.Log
import kotlin.random.Random


fun trainTaxiGame(
    sp: Array<IntArray>,
    qValues: Array<FloatArray>,
    alpha0: Float = 0.2f,
    epsilon: Float = 0.3f,
    gamma: Float = 0.9f,
    decay: Float = 0.01f,
    visualization: Boolean,
//    onStateUpdate: (Int, Int, Int) -> Unit
): List<Float> {
    resetQTable(qValues, sp)
    val episodes = 1500 // Number of training episodes
    val maxSteps = 200  // Maximum steps per episode
    val rewardHistory = mutableListOf<Float>() // List to store total rewards for each episode

    for (episode in 0 until episodes) {
        var state = Random.nextInt(0, sp.size)


        var passengerState = Random.nextInt(0, sp.size)
        var destinationState = Random.nextInt(0, sp.size)

        var totalReward = 0f // Track total rewards for this episode

        for (step in 0 until maxSteps) {
            val alpha = alpha0 / (1 + episode * decay) // Dynamic alpha based on episode

            val toExplore = Random.nextFloat() < epsilon



            val action = if (toExplore) {
                explorationPolicy() // Exploration action
            } else {
                exploitingPolicy(state, qValues) // Exploitation action
            }




            val (nextState, reward) = step(state, action, sp)

            if (nextState == -1) {
                qValues[state][action] = reward.toFloat() // Terminal state
                break // End early if no valid next state
            } else {
                val nextMaxQ = qValues[nextState].maxOrNull() ?: 0f
                qValues[state][action] = qValues[state][action] * (1 - alpha) + alpha * (reward + gamma * nextMaxQ)
                state = nextState

//                if (visualize) {
//                    onStateUpdate(state, passengerState, destinationState)
//                    delay(100) // Adjust delay for visualizing training
//                }
            }

            totalReward += reward // Update total reward for this episode
            Log.d("Training", "Episode $episode: totalReward = $totalReward")

        }
        Log.d("Training", "Taxi game training completed")

        rewardHistory.add(totalReward) // Add total reward to reward history
    }

    return rewardHistory // Return the populated reward history
}

fun step(s: Int, action: Int, sp: Array<IntArray>): Pair<Int, Int> {
    val nextState = sp[s][action]
    var reward = -1

    if (action == 5) {
        reward = -10
    }
    if (s == 45 && action == 5) {
        reward = 20
    }
    if (action == 4) {
        reward = -10
    }
    if (s == 23 && action == 4) {
        reward = 10
    }

    if (s <= 24) {
        if ((s / 5 == 0 && action == 1) || (s / 5 == 4 && action == 0) || (s % 5 == 0 && action == 3) || (s % 5 == 4 && action == 2)) {
            reward = -10
        }
    }

    if (s > 24) {
        if (((s - 25) / 5 == 0 && action == 1) || ((s - 25) / 5 == 4 && action == 0) || ((s - 25) % 5 == 0 && action == 3) || ((s - 25) % 5 == 4 && action == 2)) {
            reward = -10
        }
    }

    if ((s == 1 && action == 2) || (s == 2 && action == 3)) {
        reward = -10
    }

    if ((s == 15 && action == 2) || (s == 20 && action == 2) || (s == 16 && action == 3) || (s == 21 && action == 3)) {
        reward = -10
    }

    if ((s == 17 && action == 2) || (s == 22 && action == 2) || (s == 18 && action == 3) || (s == 23 && action == 3)) {
        reward = -10
    }

    if ((s == 26 && action == 2) || (s == 27 && action == 3)) {
        reward = -10
    }

    if ((s == 40 && action == 2) || (s == 45 && action == 2) || (s == 41 && action == 3) || (s == 46 && action == 3)) {
        reward = -10
    }

    if ((s == 42 && action == 2) || (s == 47 && action == 2) || (s == 43 && action == 3) || (s == 48 && action == 3)) {
        reward = -10
    }

    return Pair(nextState, reward)
}

fun explorationPolicy(): Int {
    return Random.nextInt(0, 6) // Random action (explore)
}

fun exploitingPolicy(state: Int, qValues: Array<FloatArray>): Int {
    return qValues[state].indexOfMax() // Choose action with highest Q-value (exploit)
}

fun resetQTable(qValues: Array<FloatArray>, sp: Array<IntArray>) {
    qValues.forEachIndexed { index, _ ->
        qValues[index] = FloatArray(6) { 0f } // Reset all Q-values to 0
    }
}

fun FloatArray.indexOfMax(): Int {
    return this.indices.maxByOrNull { this[it] } ?: -1 // Find index of max value
}


