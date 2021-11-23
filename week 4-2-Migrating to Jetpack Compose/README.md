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
