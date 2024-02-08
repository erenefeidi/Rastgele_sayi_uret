package com.example.rastgele_sayi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomNumberGeneratorScreen()
        }
    }
}



@Composable
fun RandomNumberGeneratorScreen() {
    var minNumber by remember { mutableStateOf("") }
    var maxNumber by remember { mutableStateOf("") }
    var numberOfNumbers by remember { mutableStateOf("") }
    var allowRepeats by remember { mutableStateOf(false) }

    var generatedNumbers by remember { mutableStateOf(emptyList<Int>()) }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.random_icon_app),
                contentDescription = null,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )

            OutlinedTextField(
                value = minNumber,
                onValueChange = { minNumber = it.filter { char -> char.isDigit() } },
                label = { Text("Minimum Değer") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),

                )

            OutlinedTextField(
                value = maxNumber,
                onValueChange = { maxNumber = it.filter { char -> char.isDigit() } },
                label = { Text("Maksimum Değer") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),

                )

            OutlinedTextField(
                value = numberOfNumbers,
                onValueChange = { numberOfNumbers = it.filter { char -> char.isDigit() } },
                label = { Text("Üretilecek Sayı Adedi") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),

                )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = allowRepeats,
                    onCheckedChange = { allowRepeats = it },
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Tekrar Edilebilir")
            }

            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    generatedNumbers = emptyList()
                    if (minNumber.isEmpty() || maxNumber.isEmpty() || numberOfNumbers.isEmpty()) {
                        errorMessage = "Lütfen tüm kutucukları doldurun."
                    } else {
                        val min = minNumber.toInt()
                        val max = maxNumber.toInt()

                        if (min > max) {
                            errorMessage = "Minimum değer maximum değerden büyük olamaz."
                        } else if (!allowRepeats && (max - min + 1) < numberOfNumbers.toInt()) {
                            errorMessage =
                                "Tekrar edilebilirlik kapalı olduğunda, üretilecek sayı adedi maksimum (${max - min + 1}) arasında olmalıdır."
                        } else {
                            errorMessage = ""
                            generatedNumbers = generateRandomNumbers(
                                min,
                                max,
                                numberOfNumbers.toInt(),
                                allowRepeats
                            )
                        }
                    }
                },


                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Rastgele Sayıları Üret")
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (generatedNumbers.isNotEmpty()) {
                Text("Üretilen Sayılar: ${generatedNumbers.joinToString(", ")}")
            }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RandomNumberGeneratorScreen()
}
