package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test

class RallyNavHostTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var  navController: NavHostController

    @Test
    fun rallyNavHost() {
        composeTestRule.setContent {
            navController = rememberNavController()
            RallyNavHost(navController = navController)
        }
        //fail() // TEST 를 완료하라는 TODO 역할을 함
        composeTestRule
            .onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed() // 현재 해당 화면이 표시 되는지 검증 
    }


}