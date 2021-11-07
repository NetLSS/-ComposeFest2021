package com.example.composelayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.composelayout.ui.theme.ComposeLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeLayoutTheme {
        Greeting("Android")
    }
}

@Composable
fun PhotographerCard() {
    Column {
        Text(text = "SangSu Lee", fontWeight = FontWeight.Bold)

        // LocalContentAlpha는 자식의 불투명도 수준을 정의합니다.
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "3 minutes ago",
                style = MaterialTheme.typography.body2
            )


        }
    }
}

@Preview
@Composable
fun PhotographerCardPreview() {
    ComposeLayoutTheme {
        PhotographerCard()
    }
}