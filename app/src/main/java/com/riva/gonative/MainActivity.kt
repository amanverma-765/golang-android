package com.riva.gonative

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.riva.core.rivacore.Rivacore
import com.riva.gonative.ui.theme.GoNativeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoNativeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GoExamples(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GoExamples(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Go + Android Demo",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(text = "Version: ${Rivacore.version()}")
        Text(text = Rivacore.greet("Android"))
        Text(text = "5 + 3 = ${Rivacore.add(5, 3)}")
        Text(text = "Fibonacci(10) = ${Rivacore.fibonacci(10)}")
        Text(text = "Time: ${Rivacore.getCurrentTime()}")
    }
}
