package com.example.composelayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Savings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.composelayout.ui.theme.ComposeLayoutTheme
import kotlinx.coroutines.launch

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

                //LazyList()

                BodyContent2()
            }
        }
    }
}

// you can only measure your children once.
fun Modifier.firstBaselineToTop(
    firstBaselineToTop : Dp
) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints) // 를 불러서 Composable 을 measure(constraints)

        // 컴포저블에 첫 번째 기준선이 있는지 확인
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // 패딩이 있는 컴포저블 높이 - 첫 번째 기준선
        /**
         * 현 예시 코드로 봤을 때
         * 1. [firstBaselineToTop] 예시 Text에서 텍스트 아랫라인 ~ 레이아웃 최대 top 까지
         * 2. [firstBaseline] Text 자체 크기 (텍스트 박스 자체 크기 높이라인) (텍박 아랫라인 부터 윗라인)
         * 3. [placeableY] 텍스트박스 윗라인 ~ 레이아웃 최대 top 라인 까지
         */
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        // layout 이 불릴때 까지 경고 표시
        layout(placeable.width, height) {
            // 컴포저블이 배치되는 위치
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // 여기에 제약 조건 논리가 주어진 자식을 측정하고 배치하십시오.

        // region 하위 항목 측정 - 이전 코드 스니펫의 코드
        // 자식 뷰를 더 이상 제한하지 않고 주어진 제약 조건으로 측정합니다.
        // 측정된 자식 목록
        val placeables = measurables.map { measurable ->
            // 각 자식들 측
            measurable.measure(constraints)
        }
        // endregion

        // 우리가 자식들을 배치한 y 좌표를 추적
        var yPosition = 0

        //layout(width, height) 메서드를 호출하여 자체 열의 크기를 지정합니다. 이 메서드는 자식 배치에 사용되는 람다도 제공합니다.


        // 레이아웃의 크기를 최대한 크게 설정
        layout(constraints.maxWidth, constraints.maxHeight) {
            // 자식 위치시키기
            placeables.forEach { placeable ->
                // 화면에서 항목 위치 지정
                placeable.placeRelative(x = 0, y = yPosition)

                // 여태까지 배치된 y 좌표를 기록합니다.
                yPosition += placeable.height
            }
        }
    }
}

// 그럼 실제로 사용해보자!
@Composable
fun BodyContent2(modifier: Modifier = Modifier) {
    MyOwnColumn(modifier.padding(8.dp)) {
        Text("MyOwnColumn")
        Text("아이템들 배치")
        Text("수직으로.")
        Text("이걸 한다고?ㅋㅋ")
        MyOwnColumn(modifier.padding(20.dp)) {
            Text("이게 되나?")
            Text("이게 된다고?")
        }
    }
}

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ){ measurables, constraints ->
        // region 여기에 제약 조건 논리가 주어진 자식을 측정하고 배치하십시오.

        // 각 행의 너비 추적
        val rowWidths = IntArray(rows) { 0}

        // 각 행의 최대 높이를 추적하십시오.
        val rowHeights = IntArray(rows) { 0 }

        // 자식 보기를 더 이상 제한하지 말고 주어진 제약 조건으로 측정하십시오.
        // 측정된 자식 목록
        val placeables = measurables.mapIndexed { index, measurable ->
            // 각 자식 측정
            val placeable = measurable.measure(constraints)

            // 각 행의 너비와 최대 높이를 추적합니다.
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = Math.max(rowHeights[row], placeable.height)

            placeable
        }

        // 그리드의 너비는 가장 넓은 행입니다.
        val width = rowWidths.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
            ?: constraints.minWidth

        // 그리드의 높이는 각 행의 가장 높은 요소의 합입니다.
        // 높이 제약 조건으로 강제 변환
        val height = rowHeights.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // 이전 행의 누적 높이를 기준으로 각 행의 Y 구하기
        // 그려질 시작 Y 구하는 거
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i-1] + rowHeights[i-1]
        }

        // 부모 레이아웃의 크기 설정
        layout(width, height) {
            // 행당 우리가 배치한 x 코드
            val rowX = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
        // endregion
    }

}

/**
 * ================================================================================================
 */

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    ComposeLayoutTheme {
        // 텍스트의 아랫 라인(베이스라인) 기준으로 24.dp
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    ComposeLayoutTheme {
        // 일반 적인 24.dp (텍스트 박스 상단 라인 기준 24.dp 패딩)
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}

/**
 * ================================================================================================
 */

@Composable
fun SimpleList() {
    // We save the scrolling position with this state that can also
    // be used to programmatically scroll the list
    val scrollState = rememberScrollState()

    Column(Modifier.verticalScroll(scrollState)) {
        repeat(100) {
            Text("Item #$it")
        }
    }
}

@Composable
fun LazyList() {
    // We save the scrolling position with this state that can also
    // be used to programmatically scroll the list
    //val scrollState = rememberLazyListState()

    val listSize = 10000
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()
    // We save the coroutine scope where our animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    // 0 is the first item index
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text("Scroll to the top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    // listSize - 1 is the last index of the list
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("Scroll to the end")
            }
        }
        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(it)
            }
        }
    }


}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Image(
            painter = rememberImagePainter(
                data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

// https://developer.android.com/jetpack/compose/lifecycle?authuser=1
/*
val listSize = 100
// We save the scrolling position with this state
val scrollState = rememberLazyListState()
// We save the coroutine scope where our animated scroll will be executed
val coroutineScope = rememberCoroutineScope()
*/

/**
* ================================================================================================
 */
@Composable
fun LayoutCodelab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutCodelab")
                },
                actions = {
                    IconButton(onClick = { /* doSomething()*/ }) {
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
    Column(
        modifier = modifier
            .padding(8.dp) /*내부에 직접 추가하는 방법*/
    ) {
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

/**
 * ================================================================================================
 */

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