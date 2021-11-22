# ComposeFest2021
2021 DevFest ComposeFest 코드랩 Repo 입니다
본 폴더를 Android Studio를 이용해서 열어주세요.
작업을 완료하고, push 해주세요.

# Testing in Jetpack Compose Codelab

This folder contains the source code for the
[Testing in Jetpack Compose Codelab ](https://developer.android.com/codelabs/jetpack-compose-testing)
codelab.

## 배우는 것

이 코드랩에서는 Jetpack Compose로 만든 UI를 테스트하는 방법을 배웁니다. 격리 테스트, 디버깅 테스트, 시맨틱 트리 및 동기화에 대해 배우면서 첫 번째 테스트를 작성합니다.


## 참고

작성 테스트는 계측 테스트입니다. 즉, 실행하려면 장치(물리적 장치 또는 에뮬레이터)가 필요합니다.

Rally에는 이미 일부 계측 UI 테스트가 포함되어 있습니다. androidTest 소스 세트에서 찾을 수 있습니다.

## 테스트 하는 것

- 탭에 의도한 아이콘과 텍스트가 표시되는지 테스트합니다.
- 애니메이션이 사양과 일치하는지 테스트
- 트리거된 탐색 이벤트가 올바른지 테스트
- 다양한 상태에서 UI 요소의 배치 및 거리 테스트
- 막대의 스크린샷을 찍어 이전 스크린샷과 비교합니다.

구성 요소를 테스트하는 방법 또는 양에 대한 정확한 규칙은 없습니다. 위의 모든 작업을 수행할 수 있습니다! 이 코드랩에서는 다음을 확인하여 상태 논리가 올바른지 테스트할 것입니다.

- 탭은 선택된 경우에만 레이블을 표시합니다.
- 활성 화면은 선택된 탭을 정의합니다.


## License
```
Copyright 2021 The Android Open Source Project

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
