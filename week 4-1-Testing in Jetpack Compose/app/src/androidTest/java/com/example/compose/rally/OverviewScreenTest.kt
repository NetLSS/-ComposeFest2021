package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.compose.rally.ui.overview.OverviewBody
import org.junit.Rule
import org.junit.Test

class OverviewScreenTest {

    /*
    동기화가 없으면 테스트에서 표시되기 전에 요소를 찾거나 불필요하게 기다릴 수 있습니다.
     */

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun overviewScreen_alertsDisplayed() {
        composeTestRule.setContent {
            OverviewBody()
        }

        composeTestRule
            .onNodeWithText("Alerts")
            .assertIsDisplayed() // fail
    }
    /*
    이것은 기본적으로 Compose가 영구적으로 사용 중이므로 앱을 테스트와
    동기화할 방법이 없다는 것을 알려줍니다.

    이 테스트를 수정하는 한 가지 방법은 개발자 옵션에서 애니메이션을 비활성화하는 것입니다.
    이는 View 세계에서 이를 처리하는 널리 허용되는 방법 중 하나입니다

    Compose에서 애니메이션 API는 테스트 가능성을 염두에 두고 설계되었으므로
     올바른 API를 사용하여 문제를 해결할 수 있습니다. animateDpAsState 애니메이션을 다시 시작하는
     대신 무한 애니메이션을 사용할 수 있습니다.

     무한 애니메이션은 Compose 테스트가 이해하는 특별한 경우이므로
      테스트를 바쁘게 유지하지 않습니다.

      축하합니다! 이 단계에서는 동기화와 애니메이션이 테스트에 미치는 영향에 대해 배웠습니다.

     */
}