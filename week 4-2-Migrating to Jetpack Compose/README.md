# ComposeFest2021
2021 DevFest ComposeFest 코드랩 Repo 입니다
본 폴더를 Android Studio를 이용해서 열어주세요.
작업을 완료하고, push 해주세요.

## 소개

Compose와 View 시스템은 나란히 함께 작동할 수 있습니다.

## 배우는 것

- 따라갈 수 있는 다양한 마이그레이션 경로
- Compose로 앱을 점진적으로 마이그레이션하는 방법
- Android 보기를 사용하여 빌드된 기존 화면에 Compose를 추가하는 방법
- Compose 내부에서 Android 보기를 사용하는 방법
- Compose의 View 시스템에서 테마를 사용하는 방법
- View 시스템 및 Compose 코드로 화면을 테스트하는 방법

## 마이그레이션 계획

보다 일반적인 두 가지 전략은 새 화면만 마이그레이션하고 기존 화면의 일부에 대해 View 시스템의
대체품으로 작성을 사용하는 것입니다.

### 컴포즈, 뷰 둘다

주어진 화면에서 일부 부품은 Compose로 마이그레이션되고 다른 부품은 View 시스템으로
마이그레이션될 수 있습니다. 예를 들어, View 시스템의 나머지 화면은 그대로 두고
RecyclerView를 마이그레이션할 수 있습니다.

또는 그 반대의 경우 Compose를 외부 레이아웃으로 사용하고 MapView 또는 AdView와 같이 Compose에서
사용할 수 없는 일부 기존 보기를 사용합니다.

### 마이그레이션 완료

전체 조각 또는 화면을 한 번에 하나씩 Compose로 마이그레이션합니다.
가장 단순하지만 매우 거친 입자입니다.

### 해당 코드랩은?

이 코드랩에서는 Compose와 Views가 함께 작동하는 해바라기 식물 세부 정보 화면의 Compose로
점진적 마이그레이션을 수행합니다. 그런 다음 원하는 경우 마이그레이션을 계속할 수 있을 만큼
충분히 알게 됩니다.

# 마이그레이션

## 순서

NestedScrollView 내에서 ConstraintLayout 코드와 중첩된 TextView를 제거합니다
(Codelab은 개별 항목을 마이그레이션할 때 XML 코드를 비교하고 참조하므로
코드를 주석 처리하면 유용할 것입니다)

compose_view를 뷰 ID로 사용하는 대신 Compose 코드를 호스팅할 ComposeView를 추가합니다.

## 10. ViewCompositionStrategy


기본적으로 Compose는 ComposeView가 창에서 분리될 때마다 컴포지션을 삭제합니다. 여러 가지 이유로
ComposeView가 조각(Fragment)에서 사용되는 경우 이는 바람직하지 않습니다.

- 컴포지션은 Compose UI 보기 유형이 상태를 저장하기 위해 프래그먼트의 보기 수명 주기를 따라야 합니다.
- 전환 또는 창 전환이 발생할 때 Compose UI 요소를 화면에 유지합니다. 전환하는 동안 ComposeView 자체는 창에서 분리된 후에도 계속 표시됩니다.

AbstractComposeView.disposeComposition 메서드를 수동으로 호출하여 컴포지션을 수동으로 삭제할 수 있습니다.

또는 더 이상 필요하지 않을 때 컴포지션을 자동으로 폐기하려면 다른 전략을 설정하거나 setViewCompositionStrategy 메서드를 호출하여 자신만의 전략을 만드십시오.

DisposeOnViewTreeLifecycleDestroyed 전략을 사용하여 조각의 LifecycleOwner가 파괴될 때 컴포지션을 삭제합니다.

PlantDetailFragment에는 진입 및 종료 전환이 있고(자세한 정보는 nav_garden.xml 확인) 나중에 Compose 내부에서 View 유형을 사용할 것이므로 ComposeView가 DisposeOnViewTreeLifecycleDestroyed 전략을 사용하는지 확인해야 합니다.

그럼에도 불구하고 조각에서 ComposeView를 사용할 때 항상 이 전략을 설정하는 것이 좋습니다.


# Migrating to Jetpack Compose

This folder contains the source code for the [Migrating to Jetpack Compose codelab](https://developer.android.com/codelabs/jetpack-compose-migration).

The codelab which migrates parts of [Sunflower](https://github.com/android/sunflower)'s Plant
details screen to Jetpack Compose is built in multiple GitHub branches:

* `main` is the codelab's starting point.
* `end` contains the solution to this codelab.

## Pre-requisites
* Experience with Kotlin syntax, including lambdas.
* Knowing the [basics of Compose](https://developer.android.com/codelabs/jetpack-compose-basics/).

## Getting Started
1. Install the latest Android Studio [canary](https://developer.android.com/studio/preview/).
2. Download the sample.
3. Import the sample into Android Studio.
4. Build and run the sample.


## Screenshots

![List of plants](screenshots/phone_plant_list.png "A list of plants")
![Plant details](screenshots/phone_plant_detail.png "Details for a specific plant")
![My Garden](screenshots/phone_my_garden.png "Plants that have been added to your garden")

## License

```
Copyright (C) 2020 The Android Open Source Project

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
```
