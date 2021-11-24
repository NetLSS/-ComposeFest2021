/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.plantdetail

import android.content.res.Configuration
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.google.android.material.composethemeadapter.MdcTheme
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {
    // VM의 LiveData<Plant> 필드에서 오는 값을 관찰합니다.
    val plant by plantDetailViewModel.plant.observeAsState()

    // 식물이 null이 아닌 경우 내용을 표시합니다.
    plant?.let {
        PlantDetailContent(plant = it)
    }
}

/*
view model

참고:** ViewModel을 사용할 수 없거나 해당 종속성을 컴포저블에 전달하고 싶지 않은 경우 컴포저블
 내에서 viewModel** 함수를 사용하여 ViewModel의 인스턴스를 가져올 수 있습니다.

컴포저블에는 자체 ViewModel 인스턴스가 없으며 컴포저블과 해당 Compose 코드(활동 또는 조각)를
 호스팅하는 수명 주기 소유자 간에 동일한 인스턴스가 공유됩니다.

 */

/*
live data

컴포저블에서 LiveData를 관찰하려면 LiveData.observeAsState() 함수를 사용하세요.

참고: LiveData.observeAsState()는 LiveData 관찰을 시작하고 State 객체를 통해 값을 나타냅니다.
LiveData에 새 값이 게시될 때마다 반환된 State가 업데이트되어 모든 State.value 사용이 재구성됩니다.
 */

/*
LiveData에서 내보낸 값이 null일 수 있으므로 null 검사에서 사용을 래핑해야 합니다.
그 때문에 그리고 재사용성을 위해 LiveData 소비를 분할하고 다른 구성 요소에서 수신하는 것이
 좋은 패턴입니다.
 */
@Composable
fun PlantDetailContent(plant: Plant) {
    Surface {
        Column(Modifier.padding(dimensionResource(id = R.dimen.margin_normal))) {
            PlantName(name = plant.name)
            PlantWatering(wateringInterval = plant.wateringInterval)
            PlantDescription(description = plant.description)
        }
    }
}

@Composable
private fun PlantName(name: String) {
    Text(
        text = name,
        //  android:textAppearance="?attr/textAppearanceHeadline5"
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            //android:layout_width="match_parent"
            .fillMaxWidth()
            //android:layout_marginStart="@dimen/margin_small"
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            //android:gravity="center_horizontal"
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
    /*
    참고: Compose는 dimens.xml 및 strings.xml 파일,
    즉 dimensionResource(id) 및 stringResource(id)에서 값을 가져오는 편리한 방법을 제공합니다.
     */
}

@Composable
private fun PlantWatering(wateringInterval: Int) {
    Column(Modifier.fillMaxWidth()) {
        // 두 텍스트에서 사용하는 동일한 수정자

        /*
        텍스트 컴포저블에서 수평 패딩 및 정렬 장식을 공유하므로 수정자를 로컬 변수
        (예: centerWithPaddingModifier)에 할당하여 수정자를 재사용할 수 있습니다.
        수정자는 일반 Kotlin 객체이므로 그렇게 할 수 있습니다.
         */
        val centerWithPaddingModifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .align(Alignment.CenterHorizontally)

        val normalPadding = dimensionResource(id = R.dimen.margin_normal)

        /*
        Compose의 MaterialTheme는 plant_watering_header에 사용된 colorAccent와 정확히 일치하지
        않습니다. 지금은 테마 섹션에서 개선할 MaterialTheme.colors.primaryVariant를 사용하겠습니다.
         */
        Text(
            text = stringResource(id = R.string.watering_needs_prefix),
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = centerWithPaddingModifier.padding(top = normalPadding)
        )

        val wateringIntervalText = LocalContext.current.resources.getQuantityString(
            R.plurals.watering_needs_suffix, wateringInterval, wateringInterval
        )
        Text(
            text = wateringIntervalText,
            modifier = centerWithPaddingModifier.padding(bottom = normalPadding)
        )
    }
    /*
    경고: Compose의 현재 버전에서는 차원에서 수량화된 문자열을 가져오는 것이 지원되지 않습니다.
    그렇기 때문에 LocalContext.current.resources를 통해 액세스해야 합니다.
    기능 요청이 이미 제출되었습니다.

    간단하게 하기 위해 함수를 인라인으로 호출했지만 앱에서 이 작업을 수행하는 경우 재사용할 수
     있도록 다른 함수로 추출합니다.
     */
}

/*
Note: AndroidView takes a View created programmatically. In case you want to embed a XML file,
 you can do it using view binding with the AndroidViewBinding API from the
 androidx.compose.ui:ui-viewbinding library.
 */

@Composable
private fun PlantDescription(description: String) {
    // HTML 형식의 설명을 기억합니다. 새 설명에서 재실행
    // Remembers the HTML formatted description. Re-executes on a new description
    val htmlDescription = remember(description) {
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT) // Spanned ? 가뭐지..
        // 텍스트 개별로 스타일 주는 것 인듯,
    }

    // Displays the TextView on the screen and updates with the HTML description when inflated
    // Updates to htmlDescription will make AndroidView recompose and update the text
    // TextView를 화면에 표시하고 확장되면 HTML 설명으로 업데이트합니다.
    // htmlDescription을 업데이트하면 AndroidView가 텍스트를 재구성하고 업데이트합니다.
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = {
            it.text = htmlDescription // 변경되면 재구성.
        })


}

@Preview
@Composable
private fun PlantDescriptionPreview() {
    //MaterialTheme {
    MdcTheme {
        PlantDescription("HTML<br><br>description")
    }
}

/*
미리보기에서 볼 수 있듯이 MdcTheme는 styles.xml 파일의 테마에서 색상을 선택하고 있습니다.
 */

// 다크 테마 미리 보기
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PlantDetailContentDarkPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    MdcTheme {
        PlantDetailContent(plant)
    }
}

@Preview
@Composable
private fun PlantNamePreview() {
    //MaterialTheme {
    MdcTheme {
        PlantName("Apple")
    }
}

@Preview
@Composable
private fun PlantDetailContentPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    //MaterialTheme {
    MdcTheme {
        PlantDetailContent(plant = plant)
    }
}

@Preview
@Composable
private fun PlantWateringPreview() {
    //MaterialTheme {
    MdcTheme {
        PlantWatering(wateringInterval = 7)
    }
}