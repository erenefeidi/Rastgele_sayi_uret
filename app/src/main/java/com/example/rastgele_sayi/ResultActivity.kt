package com.example.rastgele_sayi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rastgele_sayi.ui.theme.Rastgele_sayiTheme

class ResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Rastgele_sayiTheme {
                val navController = rememberNavController()
                val navBackStackEntry = navController.currentBackStackEntry!!
                val generatedNumbers = navBackStackEntry.arguments?.getIntArray("generatedNumbers")?.toList() ?: emptyList()
                ResultScreen(navController, generatedNumbers)
            }
        }
    }
}


@Composable
fun ResultScreen(navController: NavHostController, generatedNumbers: List<Int>) {
    //val navBackStackEntry = navController.currentBackStackEntry!!
   // val generatedNumbers = navBackStackEntry.arguments?.getIntArray("generatedNumbers")?.toList() ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (generatedNumbers.isNotEmpty()) {
            Text("Üretilen Sayılar: ${generatedNumbers.joinToString(", ")}")
        } else {
            Text("Henüz sayı üretilmedi.")
        }

        Spacer(modifier = Modifier.height(10.dp))

        BackButton(navController)
    }
}


@Composable
fun BackButton(navController: NavHostController) {
    Button(
        onClick = {
            navController.navigateUp()
        },
        modifier = Modifier
            .height(50.dp)
            .padding(8.dp)
    ) {
        Text("Geri Dön")
    }
}
