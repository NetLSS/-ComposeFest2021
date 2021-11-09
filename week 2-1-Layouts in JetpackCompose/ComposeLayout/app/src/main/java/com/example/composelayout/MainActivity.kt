package com.example.composelayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Savings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
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
                /*Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }*/

                //PhotographerCard()

                //LayoutCodelab()

                SimpleList()
            }
        }
    }
}

@Composable
fun SimpleList() {
    Column {
        repeat(100) {
            Text("Item #$it")
        }
    }
}

@Composable
fun LayoutCodelab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutCodelab")
                },
                actions = {
                    IconButton(onClick = { /* doSomething()*/}){
                        Icon(Icons.Filled.Savings, contentDescription = null)
                    }
                } // 관련 학습 https://codelabs.developers.google.com/codelabs/jetpack-compose-basics?authuser=1
            )
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding)/*.padding(8.dp)*/) // 외부에서 전달해주는 방법
    }
}

/*
more icons
https://fonts.google.com/icons?selected=Material+Icons

dependencies {
  ...
  implementation "androidx.compose.material:material-icons-extended:$compose_version"
}

more works

The same can be done for other Material components such as BottomNavigation or BottomDrawer. As an exercise, we invite you to try to fill the Scaffold slots with those APIs in the same way we've done until now.
 */

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .padding(8.dp) /*내부에 직접 추가하는 방법*/) {
        Text(text = "Hi there!")
        Text(text = "Thanks for going through the Layouts codelab")
    }
}

@Preview
@Composable
fun LayoutsCodelabPreview() {
    ComposeLayoutTheme {
        LayoutCodelab()
    }
}


@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {

    Row(
        modifier
            //.clickable(onClick = { /* 클릭 무시*/ }) // 전체영역 클
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = { /* 클릭 무시*/ }) // 패딩 제외 클릭
            .padding(16.dp)
            .onGloballyPositioned {
                android.util.Log.i("TEMP", "size ${it.size}")
            }
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