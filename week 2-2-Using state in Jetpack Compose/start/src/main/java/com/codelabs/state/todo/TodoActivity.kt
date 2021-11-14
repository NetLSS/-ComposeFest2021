/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelabs.state.todo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.codelabs.state.ui.StateCodelabTheme

class TodoActivity : AppCompatActivity() {

    val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateCodelabTheme {
                Surface {
                    TodoActivityScreen(todoViewModel)
                }
            }
        }
    }
}

@Composable
private fun TodoActivityScreen(todoViewModel: TodoViewModel) {
    // 이 줄은 LiveData를 관찰하고 현재 값을 List<TodoItem>으로 직접 사용하도록 합니다.
    /**
     * val itemsv: List<TodoItem>은 List<TodoItem> 유형의 변수 항목을 선언합니다.
     *
     * todoViewModel.todoItems는 ViewModel의 LiveData<List<TodoItem>입니다.
     *
     * [.observeAsState]는 LiveData<T>를 관찰하고 이를 State<T> 객체로 변환하여 Compose가 값 변경에 반응할 수 있도록 합니다.
     *
     * listOf()는 LiveData가 초기화되기 전에 가능한 null 결과를 피하기 위한 초기 값입니다. 전달되지 않은 항목은 List<TodoItem>이 될까요? nullable입니다.
     *
     * by는 Kotlin의 속성 대리자 구문이며, 이를 통해 자동으로 State<List<TodoItem>>을 observeAsState에서 일반 List<TodoItem>으로 래핑 해제할 수 있습니다.
     *
     *
     * ObservAsState는 LiveData를 관찰하고 LiveData가 변경될 때마다 업데이트되는 State 객체를 반환합니다.
     * 컴포지션에서 컴포저블이 제거되면 자동으로 관찰이 중지됩니다.


     */
    //val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(listOf())
    TodoScreen(items = todoViewModel.todoItems,
        onAddItem = todoViewModel::addItem, //{ todoViewModel.addItem(it) },
        onRemoveItem = todoViewModel::removeItem,
        onStartEdit = todoViewModel::onEditItemSelected,
        onEditItemChange = todoViewModel::onEditItemChange,
        onEditDone = todoViewModel::onEditDone,
        currentlyEditing = todoViewModel.currentEditItem
        )
}