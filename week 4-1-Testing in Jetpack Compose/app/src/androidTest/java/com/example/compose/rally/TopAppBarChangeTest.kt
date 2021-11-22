package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import com.example.compose.rally.ui.overview.OverviewBody
import org.junit.Rule
import org.junit.Test

/*
In this step, you'll use an action (see the Testing Cheat Sheet)
 to verify that clicking on the different tabs of the RallyTopAppBar changes the selection.

Hints:

The scope of the test needs to include the state, which is owned by RallyApp.
Verify state, not behavior.
Use assertions on the state of the UI instead of relying on which objects have been called and how.
There's no provided solution for this exercise.
 */
class TopAppBarChangeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun topAppBar_change() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule
            .onNode(
                hasContentDescription(RallyScreen.Bills.name)
            )
            .performClick()
            .assertContentDescriptionContains(RallyScreen.Bills.name)
    }
}