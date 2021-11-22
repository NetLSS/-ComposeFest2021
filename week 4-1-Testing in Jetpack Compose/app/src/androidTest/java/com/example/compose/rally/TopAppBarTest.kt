package com.example.compose.rally

import androidx.compose.material.Text
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.toUpperCase
import com.example.compose.rally.ui.components.RallyTopAppBar
import com.example.compose.rally.ui.theme.RallyTheme
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    // TODO: Add tests
    /*
    Compose 테스트에서는 예를 들어 Espresso를 사용하여 Android View 세계에서
     수행하는 것과 유사하게 앱의 주요 활동을 시작할 수 있습니다.
     createAndroidComposeRule을 사용하여 이 작업을 수행할 수 있습니다.

    그러나 Compose를 사용하면 격리된 구성 요소를 테스트하여 작업을 상당히 단순화할 수 있습니다.


     테스트에 사용할 Compose UI 콘텐츠를 선택할 수 있습니다.
     이것은 ComposeTestRule의 setContent 메소드를 사용하여 수행되며
      어디에서나 호출할 수 있습니다(단 한 번만).
     */

    @Test
    fun myTest() {
        composeTestRule.setContent {
            Text("You can set any Compose content!")
        }
    }

    @Test
    fun rallyTopAppBarTest() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTheme {
                RallyTopAppBar(
                    allScreens = allScreens,
                    onTabSelected = { /*todo*/ },
                    currentScreen = RallyScreen.Accounts
                )
            }
        }
        Thread.sleep(5000)
    }

    /*
    composeTestRule{.finder}{.assertion}{.action}

    For example: onNodeWithText, onNodeWithContentDescription, isSelected, hasContentDescription,
     assertIsSelected...
     */

    @Test
    fun rallyTopAppBarTest_currentTabSelected() {
        val allScreen = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreen,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }


    /*
    가능한 해결책은 텍스트를 찾아 존재한다고 주장하는 것입니다.
     */

    @Test // 탭바 텍스트가 대문자 인지 테스트
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule
            .onNodeWithText(RallyScreen.Accounts.name.uppercase())
            .assertExists()
        // However, if you run the test, it fails 😱
        // In this step you'll learn how to debug this using the [semantics tree].
    }

    /*

    Compose 테스트는 의미 체계 트리라는 구조를 사용하여 화면에서 요소를 찾고 해당 속성을 읽습니다.
    이는 TalkBack과 같은 서비스에서 읽을 수 있도록 접근성 서비스에서도 사용하는 구조입니다.

    Warning: Layout Inspector support for Semantics properties is not available yet.

    노드에서 printToLog 함수를 사용하여 시맨틱 트리를 인쇄할 수 있습니다. 테스트에 새 줄을 추가합니다.
     */

    @Test // 탭바 텍스트가 대문자 인지 테스트
    fun rallyTopAppBarTest_currentLabelExists_2() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule.onRoot().printToLog("currentLabelExists")

        /*
        Printing with useUnmergedTree = 'false'
    Node #1 at (l=0.0, t=80.0, r=1080.0, b=248.0)px
     |-Node #2 at (l=0.0, t=80.0, r=1080.0, b=248.0)px
        |-Node #3 at (l=0.0, t=80.0, r=1080.0, b=248.0)px
          [SelectableGroup]
           |-Node #4 at (l=48.0, t=128.0, r=120.0, b=200.0)px
           | Role = 'Tab'
           | Selected = 'false'
           | ContentDescription = '[Overview]'
           | Actions = [OnClick]
           | MergeDescendants = 'true'
           | ClearAndSetSemantics = 'true'
           |-Node #7 at (l=216.0, t=128.0, r=551.0, b=200.0)px
           | Role = 'Tab'
           | Selected = 'true'
           | ContentDescription = '[Accounts]'
           | Actions = [OnClick]
           | MergeDescendants = 'true'
           | ClearAndSetSemantics = 'true'
           |-Node #12 at (l=647.0, t=128.0, r=719.0, b=200.0)px
             Role = 'Tab'
             Selected = 'false'
             ContentDescription = '[Bills]'
             Actions = [OnClick]
             MergeDescendants = 'true'
             ClearAndSetSemantics = 'true'
        */

        composeTestRule
            //.onNodeWithText(RallyScreen.Accounts.name.uppercase())
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertExists() // 성공

        /*
        하지만 나쁜 소식은 이 테스트가 그다지 유용하지 않다는 것입니다!
         Semantics 트리를 자세히 보면 탭이 선택되었는지 여부에 관계없이
          세 개의 탭 모두에 대한 내용 설명이 있습니다. 더 깊이 들어가야 합니다!
         */
    }


}