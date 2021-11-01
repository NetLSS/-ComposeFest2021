package com.example.jetpackcomposebasics

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import com.example.jetpackcomposebasics.ui.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.primary) {
//                    Greeting("Android")
//                }

                MyApp()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(color = MaterialTheme.colors.primary, modifier = Modifier.padding(3.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Hello,")
            Text(text = "$name!")
        }
    }
}

@Composable
private fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {
        Greeting("Android")
    }
}

@Preview(showBackground = true, name = "Text preview")
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        //Greeting("Android")
        MyApp()
    }
}

