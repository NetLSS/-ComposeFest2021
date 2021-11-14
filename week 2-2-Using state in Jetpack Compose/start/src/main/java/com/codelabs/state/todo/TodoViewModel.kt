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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * 이 ViewModel을 사용하여 TodoScreen에서 상태를 호이스트하려고 합니다.
 */

class TodoViewModel : ViewModel() {

    // LiveData를 제거하고 mutableStateListOf로 바꿉니다.
    //private var _todoItems = MutableLiveData(listOf<TodoItem>())
    //val todoItems: LiveData<List<TodoItem>> = _todoItems

//    fun addItem(item: TodoItem) {
//        _todoItems.value = _todoItems.value!! + listOf(item)
//    }
//
//    fun removeItem(item: TodoItem) {
//        _todoItems.value = _todoItems.value!!.toMutableList().also {
//            it.remove(item)
//        }
//    }

    /**
     * mutableStateListOf 및 MutableState로 수행된 작업은 Compose를 위한 것입니다.
     *
     * 이 ViewModel이 View 시스템에서도 사용되었다면 LiveData를 계속 사용하는 것이 좋습니다.
     */

    // private state
    private var currentEditPosition by mutableStateOf(-1)

    // state: todoItems
    var todoItems = mutableStateListOf<TodoItem>()
        private set // private set을 지정함으로써 이 상태 객체에 대한 쓰기를 ViewModel 내부에서만 볼 수 있는 private setter로 제한하고 있습니다.

    // state
    val currentEditItem: TodoItem?
        get() = todoItems.getOrNull(currentEditPosition)

    // event: addItem
    fun addItem(item: TodoItem) {
        todoItems.add(item)
    }

    // event: removeItem
    fun removeItem(item: TodoItem) {
        todoItems.remove(item)
        onEditDone() // 항목을 제거할 때 편집기를 열어 두지 않음
    }

    // event : onEditItemSelected
    fun onEditItemSelected(item: TodoItem) {
        currentEditPosition = todoItems.indexOf(item)
    }

    // event : onEditDone
    fun onEditDone() {
        currentEditPosition = -1
    }

    // event : onEditItemChange
    fun onEditItemChange(item: TodoItem) {
        val currentItem = requireNotNull(currentEditItem)

        require(currentItem.id == item.id){
            "currentEditItem과 동일한 ID를 가진 항목만 변경할 수 있습니다."
        }

        todoItems[currentEditPosition] = item
    }



}
