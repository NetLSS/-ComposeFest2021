package com.example.jetpackcomposebasics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposebasics.ui.BasicsCodelabTheme
import com.example.jetpackcomposebasics.ui.Blue

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
    // savable 을 사용해서 리스트 내려갔다 올라와도 펼침 유지하게 하기 Fa
//    val expanded = rememberSaveable { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    // expanded 가 remember 이기 때문에 expanded 변경 시 extraPadding 도 변경된다.
    //val extraPadding = if (expanded.value) 48.dp else 0.dp

    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio =  Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
    )

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
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))// 0 으로 잡아주는 이유는
                                        //  접을 때!, 접을 때  마이너스가 되면 앱이 중단된다...!
                                // TODO 버그가 있따..! 두손가락으로 버튼막누르면 앱이 중단된다..
            ) {
                Text(text = "Hello,")
                Text(text = name, style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
            }
            OutlinedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Show more", color = Blue)
            }
        }
    }
}


@Composable
private fun MyApp(names: List<String> = listOf("World", "Compose")) {

    // Savable 로 화면 회전, 테마변경 등 에도 값 유지하기! 그래서 온보딩 화면은 최초만 보이도록
    var shouldShowOnboarding by rememberSaveable {
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
private fun Greetings(names: List<String> = List(1000) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->  // LazyColumn 사용 (리사이클러뷰 대응)
            Greeting(name = name)
        }
    } // 하지만 재활용은 하지 않음. 단지 컴포저블 될뿐. 더욱 저렴하다.
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

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, name = "Text preview 2", widthDp = 320)
@Composable
fun DefaultPreview2() {
    BasicsCodelabTheme {
        Greetings()
    }
}