package com.example.compose.rally

import androidx.compose.material.Text
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
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
            .onNodeWithText(RallyScreen.Accounts.name.toUpperCase())
            .assertExists()
        // However, if you run the test, it fails 😱
        // In this step you'll learn how to debug this using the [semantics tree].
    }

    /*
    Compose 테스트는 의미 체계 트리라는 구조를 사용하여 화면에서 요소를 찾고 해당 속성을 읽습니다.
    이는 TalkBack과 같은 서비스에서 읽을 수 있도록 접근성 서비스에서도 사용하는 구조입니다.
     */
}