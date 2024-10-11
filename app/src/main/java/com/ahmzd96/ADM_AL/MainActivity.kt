
    package com.ahmzd96.ADM_AL

    import android.os.Bundle
    import android.util.Log
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.compose.foundation.Canvas
    import androidx.compose.foundation.background
    import androidx.compose.foundation.border
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.grid.GridCells
    import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
    import androidx.compose.foundation.lazy.grid.items
    import androidx.compose.foundation.lazy.grid.itemsIndexed
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material.*
    import androidx.compose.material3.Button
    import androidx.compose.material3.RadioButton
    import androidx.compose.material3.Slider
    import androidx.compose.material3.Text
    import androidx.compose.runtime.*
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.geometry.Offset
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.StrokeCap
    import androidx.compose.ui.graphics.drawscope.Stroke
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import kotlin.random.Random
    import com.chaquo.python.Python



    class MainActivity : ComponentActivity() {

        private val sp = arrayOf(
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
        private var qValues = Array(50) { FloatArray(6) }
        private var epsilon by mutableStateOf(0.3f)
        private var alpha by mutableStateOf(0.2f)
        private var gamma by mutableStateOf(0.9f)
        private var trainingComplete by mutableStateOf(false)
        private var qTableHeaders = listOf("StepNR", "South", "North", "East", "West", "Pick-up", "Drop-off")

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                TaxiGameApp()
            }
        }

        @Composable
        fun TaxiGameApp() {
            var rewardHistory by remember { mutableStateOf(listOf<Float>()) }
            var currentTaxiState by remember { mutableStateOf(0) }  // Taxi's initial state
            var passengerState by remember { mutableStateOf(5) }  // Passenger's initial state
            var destinationState by remember { mutableStateOf(10) }  // Destination's initial state
            var selectedView by remember { mutableStateOf("Q-Table") }  // Initial view selection

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = "Modify Parameters", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))

                // Sliders for epsilon, alpha, gamma
                ParameterSlider(label = "Epsilon", value = epsilon, onValueChange = { epsilon = it })
                ParameterSlider(label = "Alpha", value = alpha, onValueChange = { alpha = it })
                ParameterSlider(label = "Gamma", value = gamma, onValueChange = { gamma = it })

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    resetQTable()  // Reset the Q-table before starting training
                    rewardHistory = trainTaxiGame { taxiState, passState, destState ->
                        currentTaxiState = taxiState
                        passengerState = passState
                        destinationState = destState
                    }
                    trainingComplete = true
                }) {
                    Text("Start Training")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Radio buttons to select the view to show
                Row {
                    RadioButton(
                        selected = selectedView == "Q-Table",
                        onClick = { selectedView = "Q-Table" }
                    )
                    Text("Q-Table", modifier = Modifier.padding(start = 8.dp))

                    Spacer(modifier = Modifier.width(16.dp))

                    RadioButton(
                        selected = selectedView == "Taxi Grid",
                        onClick = { selectedView = "Taxi Grid" }
                    )
                    Text("Taxi Grid", modifier = Modifier.padding(start = 8.dp))

                    Spacer(modifier = Modifier.width(16.dp))

                    RadioButton(
                        selected = selectedView == "Reward Graph",
                        onClick = { selectedView = "Reward Graph" }
                    )
                    Text("Reward Graph", modifier = Modifier.padding(start = 8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (trainingComplete) {
                    when (selectedView) {
                        "Q-Table" -> {
                            QTableView(qValues)  // Show Q-table if selected
                        }
                        "Taxi Grid" -> {
                            TaxiGrid(currentTaxiState, passengerState, destinationState)  // Show taxi grid if selected
                        }
                        "Reward Graph" -> {
                            Spacer(modifier = Modifier.height(100.dp))
                            RewardGraph(rewardHistory)  // Show reward graph if selected
                        }
                    }
                }
            }
        }

        @Composable
        fun ParameterSlider(label: String, value: Float, onValueChange: (Float) -> Unit) {
            Column {
                Text(text = "$label: ${"%.2f".format(value)}", fontSize = 18.sp)
                Slider(
                    value = value,
                    onValueChange = onValueChange,
                    valueRange = 0f..1f,
                    steps = 10,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        @Composable
        fun QTableView(qValues: Array<FloatArray>) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    qTableHeaders.forEach { header ->
                        Text(text = header, fontSize = 16.sp, modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Display Q-values
                qValues.forEachIndexed { state, actions ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "State $state", fontSize = 16.sp, modifier = Modifier.weight(1f))
                        actions.forEach { qValue ->
                            Text(text = "${"%.2f".format(qValue)}", fontSize = 16.sp, modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        @Composable
        fun TaxiGrid(
            taxiState: Int,
            passengerState: Int,
            destinationState: Int,
            gridSize: Int = 5
        ) {
            // A list representing the grid cells, each cell is its own index
            val cells = List(gridSize * gridSize) { it }

            LazyVerticalGrid(
                columns = GridCells.Fixed(gridSize), // Create a fixed grid of `gridSize` x `gridSize`
                modifier = Modifier.padding(16.dp)
            ) {
                // Loop over the cells and set the background color based on the current taxi, passenger, and destination states
                itemsIndexed(cells) { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .border(1.dp, Color.Black)
                            .background(
                                when (index) {
                                    taxiState -> Color.Blue    // Taxi is blue
                                    passengerState -> Color.Green // Passenger is green
                                    destinationState -> Color.Red  // Destination is red
                                    else -> Color.White  // Empty cells are white
                                }
                            )
                    )
                }
            }
        }

        @Composable
        fun RewardGraph(rewardHistory: List<Float>) {
            if (rewardHistory.isNotEmpty()) {
                val minReward = rewardHistory.minOrNull() ?: 0f
                val maxReward = rewardHistory.maxOrNull() ?: 0f
                val range = maxReward - minReward

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)  // Added padding around the graph
                ) {
                    val stepX = size.width / (rewardHistory.size - 1).coerceAtLeast(1)
                    val stepY = if (range == 0f) 1f else size.height / range

                    val centerY = size.height / 2

                    for (i in 1 until rewardHistory.size) {
                        val startX = (i - 1) * stepX
                        val startY = centerY - (rewardHistory[i - 1] - minReward) * stepY
                        val endX = i * stepX
                        val endY = centerY - (rewardHistory[i] - minReward) * stepY

                        drawLine(
                            color = Color.Blue,
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = 4f,
                            cap = StrokeCap.Round
                        )
                    }
                }
            } else {
                Text(text = "No reward data to display", fontSize = 18.sp, color = Color.Red, modifier = Modifier.padding(16.dp))
            }
        }

        private fun step(s: Int, action: Int): Pair<Int, Int> {
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


        private fun explorationPolicy(): Int {
            return Random.nextInt(0, 6)
        }

        private fun exploitingPolicy(state: Int): Int {
            return qValues[state].indexOfMax()
        }

        // Function to reset the Q-table
        private fun resetQTable() {
            qValues = Array(sp.size) { FloatArray(6) { 0f } }
            Log.d("Q-Table", "Q-table reset successfully")
        }

        private fun trainTaxiGame(onStateUpdate: (Int, Int, Int) -> Unit): List<Float> {
            resetQTable()

            val episodes = 1500
            val maxSteps = 200
            val rewardHistory = mutableListOf<Float>()

            for (episode in 0 until episodes) {
                var state = Random.nextInt(0, sp.size)
                var passengerState = Random.nextInt(0, sp.size)  // Random passenger position
                var destinationState = Random.nextInt(0, sp.size)  // Random destination position
                var totalReward = 0f

                for (step in 0 until maxSteps) {
                    val action = if (Random.nextFloat() < epsilon) {
                        explorationPolicy()
                    } else {
                        exploitingPolicy(state)
                    }

                    val (nextState, reward) = step(state, action)

                    if (nextState >= 0 && nextState < qValues.size) {
                        val nextMaxQ = qValues[nextState].maxOrNull() ?: 0f
                        qValues[state][action] = qValues[state][action] * (1 - alpha) + alpha * (reward + gamma * nextMaxQ)
                        state = nextState

                        // Update the grid with the taxi, passenger, and destination positions
                        onStateUpdate(state, passengerState, destinationState)
                    } else {
                        break
                    }

                    totalReward += reward
                }

                rewardHistory.add(totalReward)
                Log.d("Training", "Episode $episode: totalReward = $totalReward")
            }

            Log.d("Training", "Taxi game training completed")
            return rewardHistory
        }

        private fun FloatArray.indexOfMax(): Int {
            return this.indices.maxByOrNull { this[it] } ?: -1
        }
    }
