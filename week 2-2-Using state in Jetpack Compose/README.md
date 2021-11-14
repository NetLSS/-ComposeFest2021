# ComposeFest2021 For @NetLss
Using state in Jetpack Compose 본 폴더를 Android Studio를 이용해서 열어주세요.
작업을 완료하고, push 해주세요.

# Using State in Jetpack Compose Codelab

This folder contains the source code for the [Using State in Jetpack Compose codelab](https://developer.android.com/codelabs/jetpack-compose-state).


In this codelab, you will explore patterns for working with state in a declarative world by building a Todo application. We'll see what unidirectional
data flow is, and how to apply it in a Jetpack Compose application to build stateless and stateful composables.

## Screenshots

![Finished code](screenshots/state_movie.gif "After: Animation of fully completed project")

## 정리

### 상태란?  

기본적으로 애플리케이션의 상태는 시간이 지남에 따라 변경될 수 있는 모든 값입니다.

이것은 매우 광범위한 정의이며 Room 데이터베이스에서 클래스의 변수에 이르기까지 모든 것을 포함합니다.



### 용어 정의

- 상태 – 시간이 지남에 따라 변경될 수 있는 모든 값

- 이벤트 – 어떤 일이 발생했음을 프로그램의 일부에 알립니다.

- 단방향 데이터 흐름 – 이벤트는 위로 흐르고 상태는 아래로 흐르는 디자인

- 상태 비저장 컴포저블 – 은 상태를 직접 변경할 수 없는 컴포저블입니다.

- 상태 호이스팅은 구성 요소를 상태 비저장 상태로 만들기 위해 상태를 위로 이동하는 패턴입니다.

- Surface는 앱에 배경을 추가하고 텍스트 색상을 구성합니다.

- 재구성은 데이터가 변경될 때 트리를 업데이트하기 위해 동일한 컴포저블을 다시 실행하는 프로세스입니다.



### 단방향 데이터 흐름

단방향 데이터 흐름은 상태가 아래로 흐르고 이벤트가 위로 흐르는 디자인입니다.

단방향 데이터 흐름은 이벤트가 위로 흐르고 상태가 아래로 흐르는 디자인입니다.

예를 들어 ViewModel에서는 상태가 LiveData를 사용하여 아래로 흐르는 동안 UI의 메서드 호출과 함께 이벤트가 전달됩니다.

ViewModel을 설명하는 용어가 아닙니다. 이벤트가 위로 흐르고 상태가 내려가는 모든 디자인은 단방향입니다.

(https://developer.android.com/codelabs/jetpack-compose-state?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-state#2)

#### 단방향 데이터 흐름의 장점

- 테스트 가능성 – 상태를 표시하는 UI에서 상태를 분리하여 ViewModel과 Activity를 모두 테스트하는 것이 더 쉽습니다.

- 상태 캡슐화 – 상태는 한 곳(ViewModel)에서만 업데이트할 수 있으므로 UI가 커짐에 따라 부분 상태 업데이트 버그가 발생할 가능성이 적습니다.

- UI 일관성 – 관찰 가능한 상태 홀더를 사용하여 모든 상태 업데이트가 UI에 즉시 반영됩니다.

## What you'll learn

- 단방향 데이터 흐름이란
- UI에서 상태 및 이벤트에 대해 생각하는 방법
- Compose에서 Architecture Component의 ViewModel 및 LiveData를 사용하여 상태를 관리하는 방법
- Compose가 상태를 사용하여 화면을 그리는 방법
- 상태를 호출자로 이동해야 하는 경우
- Compose에서 내부 상태를 사용하는 방법
- State<T>를 사용하여 Compose와 상태를 통합하는 방법

## 시작 코드 탐색
시작 코드에는 4개의 패키지가 포함되어 있습니다.

examples– 단방향 데이터 흐름의 개념을 탐색하기 위한 예제 활동. 이 패키지를 편집할 필요가 없습니다.
ui– 새 작성 프로젝트를 시작할 때 Android Studio에서 자동 생성된 테마를 포함합니다. 이 패키지를 편집할 필요가 없습니다.
util– 프로젝트에 대한 도우미 코드를 포함합니다. 이 패키지를 편집할 필요가 없습니다.
todo– 우리가 만들고 있는 Todo 화면에 대한 코드를 포함하는 패키지. 이 패키지를 수정하게 됩니다.
이 코드랩은 todo패키지 의 파일에 초점을 맞춥니다 . 에서 start모듈에 익숙해 질 수있는 몇 가지 파일이 있습니다.

todo패키지에 제공된 파일
Data.kt – 표현에 사용되는 데이터 구조 TodoItem
TodoComponents.kt– Todo 화면을 구축하는 데 사용할 재사용 가능한 컴포저블. 이 파일을 편집할 필요가 없습니다.
패키지 에서 편집할 파일todo
TodoActivity.kt – 이 코드랩을 마친 후 Compose를 사용하여 Todo 화면을 그리는 Android 활동입니다.
TodoViewModel.kt– ViewModelCompose와 통합하여 Todo 화면을 구축할 A. 이 코드랩을 완료하면 이를 Compose에 연결하고 확장하여 더 많은 기능을 추가할 것입니다.
TodoScreen.kt – 이 코드랩에서 구축할 Todo 화면의 구현을 작성합니다.



## License

```
Copyright 2020 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
