package com.example.rastgele_sayi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rastgele_sayi.ui.theme.Rastgele_sayiTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Rastgele_sayiTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "mainScreen") {
                    composable("mainScreen") {
                        RandomNumberGenerator(navController)
                    }
                    composable("resultScreen") {
                        val generatedNumbers = remember {
                            navController.currentBackStackEntry?.arguments?.getIntArray("generatedNumbers")?.toList() ?: emptyList()
                        }
                        ResultScreen(navController = navController, generatedNumbers = generatedNumbers)
                    }
                }
            }
        }
    }
}

@Composable
fun RandomNumberGenerator(navController: NavHostController) {
    var min by remember { mutableIntStateOf(0) }
    var max by remember { mutableIntStateOf(100) }
    var numberOfNumbers by remember { mutableIntStateOf(1) }
    var allowRepetition by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = min.toString(),
            onValueChange = { min = it.toIntOrNull() ?: 0 },
            label = { Text("Minimum Değer") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = max.toString(),
            onValueChange = { max = it.toIntOrNull() ?: 0 },
            label = { Text("Maksimum Değer") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = numberOfNumbers.toString(),
            onValueChange = { numberOfNumbers = it.toIntOrNull() ?: 1 },
            label = { Text("Üretilecek Sayı Adedi") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = allowRepetition,
                onCheckedChange = { allowRepetition = it },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Tekrar Edilebilir")
        }

        Button(
            onClick = {
                val numbers = generateRandomNumbers(min, max, numberOfNumbers, allowRepetition)
                navController.navigate("resultScreen") {
                    popUpTo("mainScreen") { inclusive = false }
                }
                navController.previousBackStackEntry?.arguments?.putIntArray("generatedNumbers", numbers.toIntArray())

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Rastgele Sayıları Üret")
        }
    }
}

fun generateRandomNumbers(min: Int, max: Int, count: Int, allowRepetition: Boolean): List<Int> {
    val random = Random
    val generatedNumbers = mutableListOf<Int>()

    if (allowRepetition) {
        repeat(count) {
            generatedNumbers.add(random.nextInt(min, max + 1))
        }
    } else {
        val uniqueNumbers = mutableSetOf<Int>()
        while (uniqueNumbers.size < count) {
            uniqueNumbers.add(random.nextInt(min, max + 1))
        }
        generatedNumbers.addAll(uniqueNumbers)
    }

    return generatedNumbers
}



