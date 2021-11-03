package com.example.jetpackcomposebasics

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import com.example.jetpackcomposebasics.ui.BasicsCodelabTheme
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.primary) {
//                    Greeting("Android")
//                }
                // 온보딩 페이지를 보여주기 위해서 (위 코드)는 주석처리
                // 왜 주석? shouldShowOnboarding 을 사용해야 되기 때문임.

                MyApp()  // MyApp 이 초기에 보여지는 거지
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    // remember 를 붙여주어야 변경되었을 때 UI 변경이 이루어진다.
    val expanded = remember { mutableStateOf(false) }

    // expanded 가 remember 이기 때문에 expanded 변경 시 extraPadding 도 변경된다.
    val extraPadding = if (expanded.value) 48.dp else 0.dp

    // Surface 가 ui 홀더 같은 느낌?? 인것 같다. 여기 안에 여러가지를 담을 수 있는거지
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        // 가로 배치를 하려고 Row 사용 (이렇게 하면 버튼 달린 네임텍(?) 하나가 생성되는 거지)
        // 이를 여러개 생성하면(Greetings) Greeting 리스트로 보여줄 수 있는것
        // 가로배치는 이런거지 예를 들면 Hello, World 오른쪽에 버튼이 붙는 것
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }
            OutlinedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}


@Composable
private fun MyApp(names: List<String> = listOf("World", "Compose")) {

    var shouldShowOnboarding by remember {
        mutableStateOf(true) // 최초 앱 실행시에는 true 로 해서 온보딩 페이지를 보여줌
    }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        // 이후 콜백에서 버튼 클릭시 이를 false 로 하면 이건 remember 니까 ui 업데이트가 자동으로 발생.
    } else{
        Greetings()
    }
}

@Composable
private fun Greetings(names: List<String> = listOf("World", "Compose")) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        for (name in names) { // for문 을 사용해서 ui 를 구성할 수 있다는 것을 보여줌
            Greeting(name = name)
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) { // 클릭 리스너를 받음 (람다)
    // TODO: THis state Should be hoisted
    //var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface {
        // Column 으로 세로배치
        Column(
            modifier = Modifier.fillMaxSize(), // 화면 전체를 채우겠다
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked //{ shouldShowOnboarding = false }
            ) { 
                Text(text = "Continue")
            }
            
        }
    }
}

@Preview(showBackground = true, name = "Text preview", widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        //Greeting("Android")
        MyApp()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
fun GreatingsPreview() {
    Greetings()
}