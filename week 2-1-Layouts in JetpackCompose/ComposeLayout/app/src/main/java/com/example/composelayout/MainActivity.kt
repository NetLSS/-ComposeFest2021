package com.example.composelayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun PhotographerCard(modifier: Modifier = Modifier) {

    Row(
        modifier.padding(16.dp)
            .clickable(onClick = { /* 클릭 무시*/})
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            // Image goes here
        }
        Column(
            // modifier 체이닝은 순서가 영향을 미친다.
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
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


}

@Preview
@Composable
fun PhotographerCardPreview() {
    ComposeLayoutTheme {
        PhotographerCard()
    }
}