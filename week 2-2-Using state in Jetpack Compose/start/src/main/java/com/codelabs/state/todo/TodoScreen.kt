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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.state.util.generateRandomTodoItem
import kotlin.random.Random

/**
 * Stateless component that is responsible for the entire todo screen.
 *
 * @param items (state) list of [TodoItem] to display (items – 화면에 표시할 변경할 수 없는 항목 목록)
 * @param onAddItem (event) request an item be added (onAddItem – 사용자가 항목 추가를 요청할 때의 이벤트)
 * @param onRemoveItem (event) request an item be removed (onRemoveItem – 사용자가 항목 제거를 요청할 때의 이벤트)
 *
 * 사실, 이 컴포저블은 stateless입니다.
 * 전달된 항목 목록만 표시되며 목록을 직접 편집할 수 있는 방법은 없습니다.
 * 대신 변경을 요청할 수 있는 두 개의 이벤트 onRemoveItem 및 onAddItem이 전달됩니다.
 *
 * 이것은 다음과 같은 질문을 제기합니다.
 * 상태가 없는 경우 어떻게 편집 가능한 목록을 표시할 수 있습니까?
 * 상태 호이스팅(state hoisting)이라는 기술을 사용하여 수행합니다.
 *
 * 상태 호이스팅은 구성 요소를 상태 비저장 상태로 만들기 위해
 * 상태를 위로 이동하는 패턴입니다.
 *
 * 상태 비저장 구성 요소는 테스트하기 쉽고 버그가 적으며 재사용 기회가 더 많습니다.
 *
 * 이러한 매개변수의 조합은 호출자가 이 구성 가능에서 상태를 끌어올릴 수 있도록 합니다.
 *
 * 이것이 어떻게 작동하는지 보기 위해 이 컴포저블의 UI 업데이트 루프를 살펴보겠습니다.
 *
 * 이벤트 – 사용자가 항목 추가 또는 제거를 요청할 때 TodoScreen은 onAddItem 또는 onRemoveItem을 호출합니다.
 *
 * 상태 업데이트 – TodoScreen 호출자는 상태를 업데이트하여 이러한 이벤트에 응답할 수 있습니다.
 *
 * 표시 상태 – 상태가 업데이트되면 TodoScreen이 새 항목과 함께 다시 호출되고 화면에 표시할 수 있습니다.


 */
@Composable
fun TodoScreen(
    items: List<TodoItem>,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit
) {
    Column {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items = items) {
                TodoRow(
                    todo = it,
                    onItemClicked = { onRemoveItem(it) },
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
        }

        // For quick testing, a random item generator button
        Button(
            onClick = { onAddItem(generateRandomTodoItem()) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text("Add random item")
        }
    }
}

/**
 * Stateless composable that displays a full-width [TodoItem].
 *
 * @param todo item to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param modifier modifier for this element
 */
@Composable
fun TodoRow(
    todo: TodoItem, onItemClicked: (TodoItem) -> Unit, modifier: Modifier = Modifier,
    iconAlpha: Float = remember(todo.id) { randomTint() } //호출자가 이 값을 제어할 수 있도록 하려면 새 iconAlpha 매개변수의 기본 인수로 기억 호출을 이동하기만 하면 됩니다.
) {
    Row(
        modifier = modifier
            .clickable { onItemClicked(todo) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(todo.task)
        /**
         * remember
         * 키 인수 – 이 메모리가 사용하는 "키"는 괄호 안에 전달되는 부분입니다. 여기서 todo.id를 키로 전달합니다.
         * 계산 – 기억할 새 값을 계산하는 람다로 후행 람다로 전달됩니다. 여기에서 randomTint()를 사용하여 임의의 값을 계산합니다.
         *
         * 컴포지션에 기억된 값은 호출 컴포저블이 트리에서 제거되는 즉시 잊혀집니다.
         * 호출하는 컴포저블이 트리에서 이동하는 경우에도 다시 초기화됩니다. 상단의 항목을 제거하여 LazyColumn에서 이 문제를 일으킬 수 있습니다.
         *
         * 컴포저블은 재구성을 지원하기 위해 멱등해야 합니다.

         */
        Icon(
            imageVector = todo.icon.imageVector,
            tint = LocalContentColor.current.copy(alpha = iconAlpha),
            /*
            LocalContentColor는 아이콘 및 타이포그래피와 같은 콘텐츠에 대해 선호하는 색상을 제공합니다.
            배경을 그리는 Surface와 같은 컴포저블에 의해 변경됩니다.
             */
            contentDescription = stringResource(id = todo.icon.contentDescription)
        )
    }
}

private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}

@Composable
fun TodoInputTextField(modifier: Modifier) {
    /*
    경고: 이 텍스트 필드는 상태를 끌어올려야 할 때 호이스트하지 않습니다.
     이 섹션의 뒷부분에서 우리는 이 기능을 제거할 것입니다.
     */
    /*
    You declare a MutableState object in a composable three ways:

    val state = remember { mutableStateOf(default) }
    var value by remember { mutableStateOf(default) }
    val (value, setValue) = remember { mutableStateOf(default) }
     */
    /*
    MutableState<T>는 MutableLiveData<T>와 유사하지만 compose 런타임과 통합됩니다.
    관찰 가능하기 때문에 업데이트될 때마다 compose에 지시하므로
     compose는 이를 읽는 모든 구성 요소를 재구성할 수 있습니다.
     */
    val (text, setText) = remember { // getter, setter
        mutableStateOf("")
    }
    TodoInputText(text, setText, modifier)
}

@Preview
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        TodoItem("Learn compose", TodoIcon.Event),
        TodoItem("Take the codelab"),
        TodoItem("Apply state", TodoIcon.Done),
        TodoItem("Build dynamic UIs", TodoIcon.Square)
    )
    TodoScreen(items, {}, {})
}

@Preview
@Composable
fun PreviewTodoRow() {
    val todo = remember { generateRandomTodoItem() }
    TodoRow(todo = todo, onItemClicked = {}, modifier = Modifier.fillMaxWidth())
}
