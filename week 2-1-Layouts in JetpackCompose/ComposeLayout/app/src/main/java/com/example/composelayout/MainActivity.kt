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
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.composelayout.ui.theme.ComposeLayoutTheme
import com.google.android.material.chip.Chip
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

                //BodyContent2()

                //BodyContent3()

                //ConstraintLayoutContent()

                //ConstraintLayoutContent2()

                //LargeConstraintLayout()

                Surface {
                    TwoTexts(text1 = "Hi", text2 = "there")
                }
            }
        }
    }
}

/**
 * ================================================================================================
 * Compose의 규칙 중 하나는 자녀를 한 번만 측정해야 한다는 것입니다.
 * 자식을 두 번 측정하면 런타임 예외가 발생합니다.
 * 그러나 자녀를 측정하기 전에 자녀에 대한 정보가 필요할 때가 있습니다.
 *
 * Intrinsics를 사용하면 실제로 측정되기 전에 자식을 쿼리할 수 있습니다.
 * (min|max)IntrinsicWidth: 이 높이가 주어지면 콘텐츠를 적절하게 칠할 수 있는 최소/최대 너비가 얼마입니까?
 * (min|max)IntrinsicHeight: 이 너비가 주어지면 콘텐츠를 적절하게 칠할 수 있는 최소/최대 높이입니다.
 *
 *
 * 예를 들어 무한 너비의 텍스트에 minIntrinsicHeight를 요청하면
 * 텍스트가 한 줄에 그려진 것처럼 텍스트의 높이가 반환됩니다.
 */

@Composable
fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String) {
    /**
    height(IntrinsicSize.Min) 은 최소 고유 높이만큼 키가 커지도록 강제되는 자식 크기를 조정합니다.
    재귀적이므로 Row와 그 자식 minIntrinsicHeight를 쿼리합니다.

    행의 minIntrinsicHeight는 자식의 최대 minIntrinsicHeight가 됩니다.

    Divider의 minIntrinsicHeight는 제약 조건이 주어지지 않으면 공간을 차지하지 않으므로 0입니다.

    텍스트의 minIntrinsicHeight는 특정 너비가 지정된 텍스트의 것입니다. 그러므로
    행의 높이 제한은 텍스트의 최대 minIntrinsicHeight가 됩니다.

    그런 다음 Divider는 Row에서 지정한 높이 제약 조건으로 높이를 확장합니다.

    사용자 지정 레이아웃을 생성할 때마다 MeasurePolicy 인터페이스의
    (min|max)Intrinsic(Width|Height)을 사용하여 내장 함수를 계산하는 방법을 수정할 수 있습니다.
    그러나 대부분의 경우 기본값으로 충분해야 합니다.

    또한 좋은 기본값을 가지고 있는 Modifier 인터페이스의 Density.
    (min|max)Intrinsic(Width|Height)Of 메서드를 재정의하는 modifier로 내장 함수를 수정할 수 있습니다.
     */
    Row(modifier.height(IntrinsicSize.Min)) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp) // start
                .wrapContentWidth(Alignment.Start),
            text = text1
        )

        Divider(
            color = androidx.compose.ui.graphics.Color.Blue,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp) // end
                .wrapContentWidth(Alignment.End),
            text = text2
        )
    }
}

@Preview
@Composable
fun TwoTextsPreview() {
    ComposeLayoutTheme {
        Surface {
            TwoTexts(text1 = "Hi", text2 = "there")
        }
    }
}

/**
 * ================================================================================================
 */
// Constraint Layout

// Decoupled API (분리된 API)
@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // 세로 제한
        } else {
            decoupledConstraints(margin = 32.dp) // 랜드스케이프 제약
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}

@Preview
@Composable
fun DecoupledConstraintLayoutPreview() {
    ComposeLayoutTheme {
        DecoupledConstraintLayout()
    }
}

// ex3
/*
preferredWrapContent - 레이아웃은 해당 차원의 제약 조건이 적용되는 랩 콘텐츠입니다.

wrapContent - 제약 조건에서 허용하지 않는 경우에도 레이아웃은 랩 콘텐츠입니다.

fillToConstraints - 해당 차원의 제약 조건에 의해 정의된 공간을 채우도록 레이아웃이 확장됩니다.

preferredValue - 레이아웃은 해당 차원의 제약 조건에 따라 고정된 dp 값입니다.

value - 레이아웃은 해당 차원의 제약 조건에 관계없이 고정 dp 값입니다.

also
width = Dimension.preferredWrapContent.atLeast(100.dp)
 */
@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(0.5f)
        Text(
            "This is a very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(guideline, parent.end)
                width = Dimension.preferredWrapContent
            }
        )
    }
}

@Preview
@Composable
fun LargeConstraintLayoutPreview() {
    ComposeLayoutTheme {
        LargeConstraintLayout()
    }
}

// ex2
@Composable
fun ConstraintLayoutContent2() {
    ConstraintLayout {
        // 3개의 컴포저블에 대한 참조를 생성합니다.
        // ConstraintLayout의 본문에서
        val (button1, button2, text) = createRefs()

        Button(onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }) {
            Text("Button 1")
        }

        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        // constrainAs 안에서는 만들 수 없음. 밖에서 만들자
        val barrier = createEndBarrier(button1, text) // button1 과 text 를 감싼 End(제약)
        Button(onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier) // End 장벽부분을 start 로
            }
        ) {
            Text("Button 2")
        }

    }
}

// ex1
@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // 제약할 컴포저블에 대한 참조 생성
        val (button, text) = createRefs()

        Button(onClick = { /*TODO*/ },
            // 버튼 컴포저블에 참조 "버튼" 할당
            // ConstraintLayout의 맨 위에 제한합니다.
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }

        // 텍스트 컴포저블에 참조 "텍스트" 할당
        // 그리고 그것을 Button 컴포저블의 맨 아래로 제한합니다.
        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
            // ConstraintLayout에서 텍스트를 가로로 가운데 정렬합니다.
            centerHorizontallyTo(parent)
        })

    }
}

@Preview
@Composable
fun ConstraintLayoutContentPreview() {
    ComposeLayoutTheme {
        //ConstraintLayoutContent()
        ConstraintLayoutContent2()
    }
}

/**
 * ================================================================================================
 */

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun BodyContent3(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = androidx.compose.ui.graphics.Color.LightGray)
            .padding(16.dp) // 순서 중요. (스크롤 가능한 사이즈 232x232 됨)
            .size(200.dp)
            //.padding(16.dp) // 순서 중요. (스크롤 가능한 사이즈 200x200 됨) (200-16-16)
            .horizontalScroll(rememberScrollState())
    ) {
        StaggeredGrid(modifier = modifier, rows = 5) {
            for (topic in topics) {
                Chip(modifier = Modifier.padding(8.dp), text = topic)
            }
        }
    }
}

//@Preview
@Composable
fun LayoutsCodelabPreview2() {
    ComposeLayoutTheme {
        BodyContent3()
    }
}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(
            color = androidx.compose.ui.graphics.Color.Black,
            width = Dp.Hairline
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

//@Preview
@Composable
fun ChipPreview() {
    ComposeLayoutTheme {
        Chip(text = "Hi there")
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
    ) { measurables, constraints ->
        // region 여기에 제약 조건 논리가 주어진 자식을 측정하고 배치하십시오.

        // 각 행의 너비 추적
        val rowWidths = IntArray(rows) { 0 }

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
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
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


// you can only measure your children once.
fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
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


/**
 * ================================================================================================
 */

//@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    ComposeLayoutTheme {
        // 텍스트의 아랫 라인(베이스라인) 기준으로 24.dp
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}

//@Preview
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

//@Preview
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

//@Preview
@Composable
fun PhotographerCardPreview() {
    ComposeLayoutTheme {
        PhotographerCard()
    }
}