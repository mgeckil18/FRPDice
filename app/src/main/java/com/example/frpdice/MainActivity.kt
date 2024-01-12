package com.example.frpdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

val primaryColor = Color(0xFF1E88E5) // Blue
val primaryVariantColor = Color(0xFF1565C0) // Darker Blue
val secondaryColor = Color(0xFFE57373) // Red



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiceRollerApp() {
    val primaryColor = Color(0xFF1E88E5)
    val diceTypes = listOf(4, 6, 8, 10, 12, 20)
    val diceCounts = remember { mutableStateListOf(0, 0, 0, 0, 0, 0) }
    var rollResults by remember { mutableStateOf<List<String>>(listOf()) }

    MaterialTheme() {
        Scaffold(
            topBar = { TopAppBar(title = { Text("FRP Dice Roller") }) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                diceTypes.forEachIndexed { index, diceType ->
                    DiceSlot(diceType, diceCounts, index, primaryColor)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { rollResults = rollDice(diceCounts, diceTypes) },
                    modifier = Modifier
                        .size(100.dp)
                        .border(2.dp, primaryColor, shape = MaterialTheme.shapes.small),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    Text("Roll", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Results:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                rollResults.forEach { result ->
                    Text(result, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun DiceSlot(diceType: Int, diceCounts: MutableList<Int>, index: Int, primaryColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text("D$diceType:", modifier = Modifier.width(60.dp), fontSize = 16.sp)
        IconButton(onClick = { if (diceCounts[index] > 0) diceCounts[index]-- }) {
            Text("-", fontSize = 18.sp, color = primaryColor)
        }
        Text("${diceCounts[index]}", modifier = Modifier.width(30.dp), fontSize = 16.sp)
        IconButton(onClick = { if (diceCounts[index] < 3) diceCounts[index]++ }) {
            Text("+", fontSize = 18.sp, color = primaryColor)
        }
    }
}

fun rollDice(diceCounts: List<Int>, diceTypes: List<Int>): List<String> {
    return diceCounts.zip(diceTypes).flatMap { (count, type) ->
        List(count) { "(${Random.nextInt(1, type + 1)}/$type)" }
    }
}
