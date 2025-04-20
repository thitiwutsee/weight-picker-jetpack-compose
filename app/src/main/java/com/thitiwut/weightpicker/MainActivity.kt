package com.thitiwut.weightpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thitiwut.weightpicker.ui.theme.WeightPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeightPickerTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Scale(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            style = ScaleStyle(
                scaleWidth = 150.dp
            )
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeightPickerTheme {
        MainContent()
    }
}