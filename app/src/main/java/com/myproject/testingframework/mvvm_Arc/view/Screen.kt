package com.myproject.testingframework.mvvm_Arc.view

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.core.os.bundleOf
import androidx.datastore.preferences.core.Preferences
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigator
import coil3.compose.AsyncImage
import com.myproject.composeflow.Actions.Button_Actions.ActionType_Alert
import com.myproject.composeflow.Actions.Button_Actions.ActionType_SimpleDialog
import com.myproject.composeflow.Actions.Button_Actions.ActionType_SnackBar
import com.myproject.composeflow.Actions.Button_Actions.ActionType_Url
import com.myproject.composeflow.Components.Button.ButtonElevated
import com.myproject.composeflow.Components.Button.ButtonFilled
import com.myproject.composeflow.Components.Button.ButtonFloatingAction
import com.myproject.composeflow.Components.Button.ButtonIcon
import com.myproject.composeflow.Components.Button.ButtonText
import com.myproject.composeflow.Components.Container.BoxContainer
import com.myproject.composeflow.Components.Container.HorizontalContainer
import com.myproject.composeflow.Components.Container.VerticalContainer
import com.myproject.composeflow.Components.Design.paddingValues
import com.myproject.composeflow.Components.Form.DropDownOption
import com.myproject.composeflow.Components.Layouts.Vertical.DynamicColumn
import com.myproject.composeflow.Components.Text.MultiLineInputText
import com.myproject.composeflow.Components.Text.SingleLineInputText
import com.myproject.composeflow.Components.Text.SubtitleText
import com.myproject.composeflow.Components.Text.TextBlock
import com.myproject.composeflow.Components.Text.TitleText
import com.myproject.composeflow.Components.Text.fontWeightMap
import com.myproject.composeflow.Components.Text.mapToKeyboardType
import com.myproject.composeflow.globalMap.textFieldValues
import com.myproject.testingframework.Authentication.extractType
import com.myproject.testingframework.SignUpForm.JsonFileParsing
import com.myproject.testingframework.SignUpForm.mapStringToAlignment
import com.myproject.testingframework.dataStore.createKey
import com.myproject.testingframework.dataStore.getDataFromDB
import com.myproject.testingframework.dataStore.saveDataToDB
import com.myproject.testingframework.mvvm_Arc.model.functions.AuthenticateUser
import com.myproject.testingframework.mvvm_Arc.model.functions.replacedynamicData
import com.myproject.testingframework.mvvm_Arc.model.repository.JsonToKotlin
import com.myproject.testingframework.mvvm_Arc.viewmodel.MyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.exp


@Composable
fun Screen(modifier: Modifier = Modifier, id: Int, NavController: NavController) {
    val context = LocalContext.current
    val template = JsonToKotlin(context = context, id = id)[0]
    val content = JsonToKotlin(context = context, id = id)[1]

//    val template = JsonFileParsing(context)[0]
//    val content = JsonFileParsing(context)[1]

    val name = template["name"] as String
    val orientation = template["orientation"]
    val templatePadding = paddingValues(path = template["paddings"])
    val bgColor =
        template["background"]?.toString()?.runCatching { Color(toColorInt()) }?.getOrNull()
            ?: Color.White

    val templateItems = template["items"] as List<Map<*, *>>
    val contentItems = content["items"] as List<Map<*, *>>

    var showalertdialog by remember { mutableStateOf(false) }
    var showsimpledialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }


    //signup form
    val newUserData = mutableListOf<Pair<String, String>>()

    //MyApi
    val myvm: MyViewModel = hiltViewModel()
    val organizationList = myvm.organizationList.collectAsState()


    //access data from dataStore
    val Stored_index = createKey("Stored_index")

    val hasLists = templateItems.any { it["type"] == "layout" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .let { Modifier.background(bgColor) }
            .then(templatePadding)
            .then(
                if (!hasLists) Modifier.verticalScroll(rememberScrollState())
                else Modifier
            )
//            .verticalScroll(rememberScrollState())

    ) {
        contentItems.forEach { contentItem ->
            when (contentItem["type"]) {
                name -> {
                    when (orientation ?: "vertical") {
                        "vertical" -> {
                            VerticalContainer(
                                modifier = Modifier,
                                contents = {
                                    templateItems.forEach { templateItem ->
                                        val type =
                                            when (val type =
                                                extractType(templateItem["type"] as String)) {
                                                Pair("text", "title") -> "TitleText"
                                                Pair("text", "body") -> "SubtitleText"
                                                Pair("text", "Subtitle") -> "SubtitleText"
                                                else -> type.first
                                            }
                                        when (type) {
                                            "verticalContainer" -> {
                                                val items = templateItem["items"] as List<Map<*, *>>
                                                items.forEach { item2 ->
                                                    val type2 = item2["type"] as String
                                                    when (type2) {
                                                        "boxContainer" -> {
                                                            val boxPadding =
                                                                paddingValues(item2["paddings"])
                                                            val wrap =
                                                                if (item2["contentHeight"] == "wrap") true else false
                                                            BoxContainer(
                                                                modifier = Modifier
                                                                    .then(
                                                                        boxPadding
                                                                    )
                                                                    .clip(
                                                                        shape = RoundedCornerShape(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    .background(
                                                                        Color.White,
                                                                        shape = RoundedCornerShape(5.dp)
                                                                    ),
                                                                wrapContentHeight = wrap,
                                                                content = {
                                                                    val items =
                                                                        item2["items"] as List<Map<*, *>>
                                                                    Column {
                                                                        items.forEach { item3 ->
                                                                            val type3 =
                                                                                when (val type =
                                                                                    extractType(
                                                                                        item3["type"] as String
                                                                                    )) {
                                                                                    Pair(
                                                                                        "text",
                                                                                        "title"
                                                                                    ) -> "TitleText"

                                                                                    Pair(
                                                                                        "text",
                                                                                        "body"
                                                                                    ) -> "SubtitleText"

                                                                                    Pair(
                                                                                        "text",
                                                                                        "Subtitle"
                                                                                    ) -> "SubtitleText"

                                                                                    Pair(
                                                                                        "text",
                                                                                        ""
                                                                                    ) -> "TextBlock"

                                                                                    else -> type.first
                                                                                }
                                                                            when (type3) {
                                                                                "TextBlock" -> {
                                                                                    val font_size =
                                                                                        (item3["font_size"].toString()).toFloat()
                                                                                            .toInt()
                                                                                    val font_weight =
                                                                                        fontWeightMap[item3["font_weight"]]
                                                                                    val bgColor =
                                                                                        item3["bgColor"]
                                                                                    val textPadding =
                                                                                        paddingValues(
                                                                                            item3["margins"]
                                                                                        )

                                                                                    val contentId =
                                                                                        item3["#text"] as String
                                                                                    val text =
                                                                                        contentItem[contentId].toString()

                                                                                    TextBlock(
                                                                                        text = text,
                                                                                        fontSize = font_size,
                                                                                        fontWeight = font_weight,
                                                                                        modifier = Modifier
                                                                                            .fillMaxWidth()
                                                                                            .then(
                                                                                                textPadding
                                                                                            )
                                                                                            .background(
                                                                                                Color(
                                                                                                    bgColor.toString()
                                                                                                        .toColorInt()
                                                                                                )
                                                                                            )
                                                                                    )
                                                                                }

                                                                                "inputText" -> {
                                                                                    when ((item3["maxLines"] as? Number)?.toInt()
                                                                                        ?: 1) {
                                                                                        1 -> {
                                                                                            val keyboardType =
                                                                                                mapToKeyboardType[item3["keyboardType"]]
                                                                                            val font_size =
                                                                                                (item3["font_size"] as Number).toInt()
                                                                                            val font_weight =
                                                                                                fontWeightMap[item3["font_weight"]]
                                                                                            val inputpaddings =
                                                                                                paddingValues(
                                                                                                    path = item3["margins"]
                                                                                                )
                                                                                            val suffixIcon =
                                                                                                item3["suffixIcon"]
                                                                                            val isRequired =
                                                                                                item3["isRequired"] as? Boolean
                                                                                                    ?: false
                                                                                            val contentId =
                                                                                                item3["#text"] as String

                                                                                            val text =
                                                                                                contentItem[contentId].toString()

                                                                                            var controller by remember(
                                                                                                contentId
                                                                                            ) {
                                                                                                mutableStateOf(
                                                                                                    ""
                                                                                                )
                                                                                            }

                                                                                            SingleLineInputText(
                                                                                                keyboardType = keyboardType
                                                                                                    ?: KeyboardType.Text,
                                                                                                font_size = font_size,
                                                                                                fontWeight = font_weight,
                                                                                                suffixIcon = suffixIcon.toString(),
                                                                                                hintText = text,
                                                                                                value = controller,
                                                                                                isRequired = isRequired,
                                                                                                onValueChange = { newValue ->
//                                                                                                    val index =
//                                                                                                        textFieldValues.indexOfFirst { it.first == contentId }
//                                                                                                    if (index != -1) {
//                                                                                                        textFieldValues[index] =
//                                                                                                            textFieldValues[index].copy(
//                                                                                                                second = newValue
//                                                                                                            )
//                                                                                                    } else {
//                                                                                                        textFieldValues.add(
//                                                                                                            contentId to newValue
//                                                                                                        )
//                                                                                                    }
                                                                                                    controller =
                                                                                                        newValue

                                                                                                    val index =
                                                                                                        newUserData.indexOfFirst { it.first == text }
                                                                                                    if (index != -1) {
                                                                                                        newUserData[index] =
                                                                                                            text to newValue
                                                                                                    } else {
                                                                                                        newUserData.add(
                                                                                                            text to newValue
                                                                                                        )
                                                                                                    }
                                                                                                },
                                                                                                modifier = Modifier
                                                                                                    .then(
                                                                                                        inputpaddings
                                                                                                    )
                                                                                                    .fillMaxWidth()
                                                                                            )

                                                                                        }

                                                                                        else -> {
                                                                                            MultiLineInputText()
                                                                                        }
                                                                                    }
                                                                                }

                                                                                "horizontalContainer" -> {
                                                                                    val horizontalItem =
                                                                                        item3["items"] as List<Map<*, *>>
                                                                                    HorizontalContainer(
                                                                                        content = {
                                                                                            horizontalItem.forEach {
                                                                                                val type4 =
                                                                                                    it["type"] as String
                                                                                                when (type4) {
                                                                                                    "inputText" -> {
                                                                                                        when ((it["maxLines"] as? Number)?.toInt()
                                                                                                            ?: 1) {
                                                                                                            1 -> {
                                                                                                                val keyboardType =
                                                                                                                    mapToKeyboardType[it["keyboardType"]]
                                                                                                                val font_size =
                                                                                                                    (it["font_size"] as Number).toInt()
                                                                                                                val font_weight =
                                                                                                                    fontWeightMap[it["font_weight"]]
                                                                                                                val inputpaddings =
                                                                                                                    paddingValues(
                                                                                                                        path = it["margins"]
                                                                                                                    )
                                                                                                                val suffixIcon =
                                                                                                                    it["suffixIcon"]
                                                                                                                val contentId =
                                                                                                                    it["#text"] as String
                                                                                                                val isRequired =
                                                                                                                    it["isRequired"] as? Boolean
                                                                                                                        ?: false

                                                                                                                val text =
                                                                                                                    contentItem[contentId].toString()

                                                                                                                var controller by remember(
                                                                                                                    contentId
                                                                                                                ) {
                                                                                                                    mutableStateOf(
                                                                                                                        ""
                                                                                                                    )
                                                                                                                }

                                                                                                                SingleLineInputText(
                                                                                                                    keyboardType = keyboardType
                                                                                                                        ?: KeyboardType.Text,
                                                                                                                    font_size = font_size,
                                                                                                                    fontWeight = font_weight,
                                                                                                                    suffixIcon = suffixIcon.toString(),
                                                                                                                    hintText = text,
                                                                                                                    value = controller,
                                                                                                                    isRequired = isRequired,
                                                                                                                    onValueChange = { newValue ->
                                                                                                                        controller =
                                                                                                                            newValue

                                                                                                                        val index =
                                                                                                                            newUserData.indexOfFirst { it.first == text }
                                                                                                                        if (index != -1) {
                                                                                                                            newUserData[index] =
                                                                                                                                text to newValue
                                                                                                                        } else {
                                                                                                                            newUserData.add(
                                                                                                                                text to newValue
                                                                                                                            )
                                                                                                                        }
                                                                                                                    },
                                                                                                                    modifier = Modifier
                                                                                                                        .then(
                                                                                                                            inputpaddings
                                                                                                                        )
                                                                                                                        .weight(
                                                                                                                            1f
                                                                                                                        )
                                                                                                                )
                                                                                                            }

                                                                                                            else -> {
                                                                                                                MultiLineInputText()
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    )
                                                                                }

                                                                                "dropdown" -> {
                                                                                    val contentId =
                                                                                        item3["#text"] as String

                                                                                    var selectedOption by remember(
                                                                                        contentId
                                                                                    ) {
                                                                                        mutableStateOf(
                                                                                            ""
                                                                                        )
                                                                                    }
                                                                                    var expanded by remember(
                                                                                        contentId
                                                                                    ) {
                                                                                        mutableStateOf(
                                                                                            false
                                                                                        )
                                                                                    }

                                                                                    val font_size =
                                                                                        (item3["font_size"] as Number).toInt()
                                                                                    val font_weight =
                                                                                        fontWeightMap[item3["font_weight"]]
                                                                                    val dropdownPadding =
                                                                                        paddingValues(
                                                                                            path = item3["margins"]
                                                                                        )

                                                                                    val hintText =
                                                                                        contentItem[contentId].toString()

                                                                                    val options =
                                                                                        item3["options"] as List<String>
                                                                                    val isRequired =
                                                                                        item3["isRequired"] as? Boolean
                                                                                            ?: false

                                                                                    DropDownOption(
                                                                                        modifier = Modifier
                                                                                            .fillMaxWidth()
                                                                                            .then(
                                                                                                dropdownPadding
                                                                                            ),
                                                                                        fontWeight = font_weight,
                                                                                        font_size = font_size,
                                                                                        isRequired = isRequired,
                                                                                        hintText = hintText,
                                                                                        options = options,
                                                                                        selectedOption = selectedOption,
                                                                                        onOptionSelected = {
                                                                                            selectedOption =
                                                                                                it
                                                                                            val index =
                                                                                                newUserData.indexOfFirst { it.first == hintText }
                                                                                            if (index != -1) {
                                                                                                newUserData[index] =
                                                                                                    hintText to it
                                                                                            } else {
                                                                                                newUserData.add(
                                                                                                    hintText to it
                                                                                                )
                                                                                            }
                                                                                        },
                                                                                        expanded = expanded,
                                                                                        onExpandedChange = {
                                                                                            expanded =
                                                                                                !expanded
                                                                                        }
                                                                                    )
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            )
                                                        }
                                                    }
                                                }
                                            }

                                            "boxContainer" -> {
                                                val bgColor =
                                                    if (templateItem["bgColor"] != null) templateItem["bgColor"] as String else null
                                                val wrap =
                                                    if (templateItem["contentHeight"] == "wrap") true else false
                                                val paddings =
                                                    paddingValues(path = templateItem["paddings"])
                                                val items = templateItem["items"] as List<Map<*, *>>
                                                val alignment =
                                                    if (templateItem["align"] != null) mapStringToAlignment(
                                                        templateItem["align"] as String
                                                    ) else null


                                                BoxContainer(
                                                    wrapContentHeight = wrap,
                                                    alignment = alignment,
                                                    modifier = Modifier
                                                        .then(paddings)
                                                        .let {
                                                            if (bgColor != null) Modifier.background(
                                                                Color(bgColor.toColorInt())
                                                            ) else Modifier
                                                        },
                                                    content = {
                                                        items.forEachIndexed { index, boxitem ->
                                                            val itemtype =
                                                                when (val type =
                                                                    extractType(boxitem["type"] as String)) {
                                                                    Pair(
                                                                        "text",
                                                                        "title"
                                                                    ) -> "TitleText"

                                                                    Pair(
                                                                        "text",
                                                                        "body"
                                                                    ) -> "SubtitleText"

                                                                    Pair(
                                                                        "text",
                                                                        "Subtitle"
                                                                    ) -> "SubtitleText"

                                                                    else -> type.first
                                                                }
                                                            when (itemtype) {
                                                                "boxContainer" -> {
                                                                    val bgColor =
                                                                        if (boxitem["bgColor"] != null) boxitem["bgColor"] as String else null
                                                                    val wrap =
                                                                        if (boxitem["contentHeight"] == "wrap") true else false
                                                                    val paddings =
                                                                        paddingValues(path = boxitem["paddings"])
                                                                    val items =
                                                                        boxitem["items"] as List<Map<*, *>>
                                                                    val alignment =
                                                                        if (boxitem["align"] != null) mapStringToAlignment(
                                                                            boxitem["align"] as String
                                                                        ) else null

                                                                    BoxContainer(
                                                                        alignment = alignment,
                                                                        wrapContentHeight = wrap,
                                                                        modifier = Modifier
                                                                            .then(
                                                                                paddings
                                                                            )
                                                                            .let {
                                                                                if (bgColor != null) Modifier.background(
                                                                                    Color(bgColor.toColorInt())
                                                                                ) else Modifier
                                                                            },
                                                                        content = {
                                                                            items.forEach { boxitem2 ->
                                                                                val type2 =
                                                                                    when (val type =
                                                                                        extractType(
                                                                                            boxitem2["type"] as String
                                                                                        )) {
                                                                                        Pair(
                                                                                            "text",
                                                                                            "title"
                                                                                        ) -> "TitleText"

                                                                                        Pair(
                                                                                            "text",
                                                                                            "body"
                                                                                        ) -> "SubtitleText"

                                                                                        Pair(
                                                                                            "text",
                                                                                            "Subtitle"
                                                                                        ) -> "SubtitleText"

                                                                                        else -> type.first
                                                                                    }
                                                                                when (type2) {
                                                                                    "verticalContainer" -> {
                                                                                        val items =
                                                                                            boxitem2["items"] as List<Map<*, *>>
                                                                                        VerticalContainer(
                                                                                            wrapContentHeight = true,
                                                                                            contents = {
                                                                                                items.forEach { containerItem ->
                                                                                                    val type3 =
                                                                                                        when (val type =
                                                                                                            extractType(
                                                                                                                containerItem["type"] as String
                                                                                                            )) {
                                                                                                            Pair(
                                                                                                                "text",
                                                                                                                "title"
                                                                                                            ) -> "TitleText"

                                                                                                            Pair(
                                                                                                                "text",
                                                                                                                "body"
                                                                                                            ) -> "SubtitleText"

                                                                                                            Pair(
                                                                                                                "text",
                                                                                                                "Subtitle"
                                                                                                            ) -> "SubtitleText"

                                                                                                            else -> type.first
                                                                                                        }
                                                                                                    when (type3) {
                                                                                                        "SubtitleText" -> {
                                                                                                            val font_size =
                                                                                                                (containerItem["font_size"] as Number).toInt()
                                                                                                            val font_weight =
                                                                                                                fontWeightMap[containerItem["font_weight"]]
                                                                                                            val paddings =
                                                                                                                paddingValues(
                                                                                                                    containerItem["margins"]
                                                                                                                )
                                                                                                            val align =
                                                                                                                if (containerItem["align"] != null) mapStringToAlignment(
                                                                                                                    containerItem["align"] as String
                                                                                                                ) else null
                                                                                                            val text =
                                                                                                                ""

                                                                                                            TextBlock(
                                                                                                                text = text,
                                                                                                                fontSize = font_size,
                                                                                                                fontWeight = font_weight,
                                                                                                                alignment = align,
                                                                                                                modifier = Modifier
                                                                                                                    .then(
                                                                                                                        paddings
                                                                                                                    )
                                                                                                                    .fillMaxWidth()
                                                                                                            )
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            },
                                                                                            modifier = Modifier
                                                                                        )
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    )
                                                                }

                                                                "TitleText" -> {
                                                                    val font_size =
                                                                        boxitem["font_size"].toString()
                                                                            .toFloat()
                                                                            .toInt()
                                                                    val font_weight =
                                                                        fontWeightMap[boxitem["font_weight"]]
                                                                            ?: FontWeight.Black
                                                                    val paddings =
                                                                        paddingValues(
                                                                            path = boxitem["margins"]
                                                                        )
                                                                    val contentId =
                                                                        boxitem["#text"] as String

                                                                    val text =
                                                                        contentItem[contentId] as String

                                                                    TitleText(
                                                                        text = text,
                                                                        fontWeight = font_weight,
                                                                        fontSize = font_size,
                                                                        modifier = Modifier.then(
                                                                            paddings
                                                                        )
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                )
                                            }

                                            "layout" -> {
                                                val layoutType =
                                                    templateItem["orientation"] as String
                                                val layoutItem =
                                                    templateItem["items"] as List<Map<*, *>>
                                                val count =
                                                    (templateItem["itemCount"])?.toString()
                                                        ?.toFloatOrNull()?.toInt()
                                                        ?: layoutType.length
                                                when (layoutType) {
                                                    "vertical" -> {
                                                        DynamicColumn(
                                                            modifier = Modifier,
                                                            itemCount = organizationList.value.size,
                                                            content = { index1 ->
                                                                val expanded = remember(index1) {
                                                                    mutableStateOf(false)
                                                                }
                                                                layoutItem.forEach {
                                                                    when (it["type"]) {
                                                                        "horizontalContainer" -> {
                                                                            val items =
                                                                                it["items"] as List<Map<*, *>>

                                                                            HorizontalContainer(
                                                                                modifier = Modifier
                                                                                    .padding(
                                                                                        vertical = 5.dp
                                                                                    )
                                                                                    .clickable {
                                                                                        expanded.value =
                                                                                            !expanded.value
                                                                                    },
                                                                                content = {
                                                                                    items.forEach { containerItem ->
                                                                                        val type =
                                                                                            containerItem["type"] as String
                                                                                        when (type) {
                                                                                            "image" -> {
                                                                                                val image_url =
                                                                                                    containerItem["image_url"] as String
                                                                                                val imagePadding =
                                                                                                    paddingValues(
                                                                                                        containerItem["margins"]
                                                                                                    )
                                                                                                AsyncImage(
                                                                                                    model = image_url,
                                                                                                    contentDescription = null,
                                                                                                    modifier = Modifier
                                                                                                        .height(
                                                                                                            80.dp
                                                                                                        )
                                                                                                        .then(
                                                                                                            imagePadding
                                                                                                        )
                                                                                                        .clip(
                                                                                                            shape = RoundedCornerShape(
                                                                                                                2.dp
                                                                                                            )
                                                                                                        )
                                                                                                )
                                                                                            }

                                                                                            "container" -> {
                                                                                                val item =
                                                                                                    containerItem["items"] as List<Map<*, *>>
                                                                                                val containerPadding =
                                                                                                    paddingValues(
                                                                                                        path = containerItem["margins"]
                                                                                                    )
                                                                                                VerticalContainer(
                                                                                                    modifier = Modifier
                                                                                                        .weight(
                                                                                                            1f
                                                                                                        )
                                                                                                        .then(
                                                                                                            containerPadding
                                                                                                        ),
                                                                                                    contents = {
                                                                                                        item.forEach {
                                                                                                            val type =
                                                                                                                when (val type =
                                                                                                                    extractType(
                                                                                                                        it["type"] as String
                                                                                                                    )) {
                                                                                                                    Pair(
                                                                                                                        "text",
                                                                                                                        "title"
                                                                                                                    ) -> "TitleText"

                                                                                                                    Pair(
                                                                                                                        "text",
                                                                                                                        "body"
                                                                                                                    ) -> "SubtitleText"

                                                                                                                    Pair(
                                                                                                                        "text",
                                                                                                                        "Subtitle"
                                                                                                                    ) -> "SubtitleText"

                                                                                                                    else -> type.first
                                                                                                                }
                                                                                                            when (type) {
                                                                                                                "TitleText" -> {
                                                                                                                    val font_size =
                                                                                                                        it["font_size"].toString()
                                                                                                                            .toFloat()
                                                                                                                            .toInt()
                                                                                                                    val font_weight =
                                                                                                                        fontWeightMap[it["font_weight"]]
                                                                                                                            ?: FontWeight.Black
                                                                                                                    val paddings =
                                                                                                                        paddingValues(
                                                                                                                            path = it["margins"]
                                                                                                                        )
                                                                                                                    val contentId =
                                                                                                                        it["#text"] as String

                                                                                                                    val text =
                                                                                                                        if (replacedynamicData(
                                                                                                                                contentId = contentId,
                                                                                                                                index = index1
                                                                                                                            ) == contentId
                                                                                                                        ) contentItem[contentId] as String else replacedynamicData(
                                                                                                                            contentId = contentId,
                                                                                                                            index = index1
                                                                                                                        )

                                                                                                                    TitleText(
                                                                                                                        text = organizationList.value[index1][text].toString(),
                                                                                                                        fontWeight = font_weight,
                                                                                                                        fontSize = font_size,
                                                                                                                        modifier = Modifier.then(
                                                                                                                            paddings
                                                                                                                        )
                                                                                                                    )
                                                                                                                }

                                                                                                                "SubtitleText" -> {
                                                                                                                    val font_size =
                                                                                                                        it["font_size"].toString()
                                                                                                                            .toFloat()
                                                                                                                            .toInt()
                                                                                                                    val font_weight =
                                                                                                                        fontWeightMap[it["font_weight"]]
                                                                                                                            ?: FontWeight.Black
                                                                                                                    val paddings =
                                                                                                                        paddingValues(
                                                                                                                            path = it["margins"]
                                                                                                                        )
                                                                                                                    val contentId =
                                                                                                                        it["#text"] as String

                                                                                                                    val text =
                                                                                                                        if (replacedynamicData(
                                                                                                                                contentId = contentId,
                                                                                                                                index = index1
                                                                                                                            ) == contentId
                                                                                                                        ) contentItem[contentId] as String else replacedynamicData(
                                                                                                                            contentId = contentId,
                                                                                                                            index = index1
                                                                                                                        )

                                                                                                                    SubtitleText(
                                                                                                                        text = organizationList.value[index1][text].toString(),
                                                                                                                        fontSize = font_size,
                                                                                                                        fontWeight = font_weight,
                                                                                                                        modifier = Modifier.then(
                                                                                                                            paddings
                                                                                                                        )
                                                                                                                    )
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                )
                                                                                            }

                                                                                            "button" -> {
                                                                                                val buttonType =
                                                                                                    containerItem["subtype"]
                                                                                                        ?: "elevated"
                                                                                                val buttonPadding =
                                                                                                    paddingValues(
                                                                                                        path = containerItem["margins"]
                                                                                                    )
                                                                                                val action =
                                                                                                    containerItem["action"] as Map<*, *>
                                                                                                val onclick: () -> Unit =
                                                                                                    {
                                                                                                        action.let { action ->
                                                                                                            when (action["type"]) {
                                                                                                                "toast" -> {
                                                                                                                    val message =
                                                                                                                        action["message"] as String
                                                                                                                    val duration =
                                                                                                                        action["duration"] as String

                                                                                                                    ActionType_SnackBar(
                                                                                                                        context = context,
                                                                                                                        message = message,
                                                                                                                        duration = duration
                                                                                                                    )
                                                                                                                }

                                                                                                                "navigate" -> {
                                                                                                                    val screenName =
                                                                                                                        action["destination"] as String




                                                                                                                    CoroutineScope(
                                                                                                                        Dispatchers.IO
                                                                                                                    ).launch {
                                                                                                                        saveDataToDB(
                                                                                                                            context = context,
                                                                                                                            key = Stored_index,
                                                                                                                            value = "$index1"
                                                                                                                        )
                                                                                                                        withContext(
                                                                                                                            Dispatchers.Main
                                                                                                                        ) {
                                                                                                                            NavController.navigate(
                                                                                                                                route = screenName
                                                                                                                            )
                                                                                                                        }
                                                                                                                    }


                                                                                                                }

//                                                                                                                "set_value" -> {
//                                                                                                                    expanded.value =
//                                                                                                                        !expanded.value
//                                                                                                                }

                                                                                                            }
                                                                                                        }
                                                                                                    }


                                                                                                when (buttonType) {
                                                                                                    "icon" -> {
                                                                                                        var icon =
                                                                                                            containerItem["icon"] as String
                                                                                                        if (icon == "arrow") {
                                                                                                            if (expanded.value) {
                                                                                                                icon =
                                                                                                                    "arrow_up"
                                                                                                            } else {
                                                                                                                icon =
                                                                                                                    "arrow_down"
                                                                                                            }
                                                                                                        }
                                                                                                        ButtonIcon(
                                                                                                            onclick = onclick,
                                                                                                            icon = icon,
                                                                                                            modifier = Modifier.then(
                                                                                                                buttonPadding
                                                                                                            )
                                                                                                        )
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            )
                                                                        }

                                                                        "expandable" -> {
                                                                            AnimatedVisibility(
                                                                                visible = expanded.value,
                                                                                enter = expandVertically(
                                                                                    animationSpec = tween(
                                                                                        durationMillis = 300
                                                                                    )
                                                                                ),
                                                                                exit = shrinkVertically(
                                                                                    animationSpec = tween(
                                                                                        durationMillis = 300
                                                                                    )
                                                                                ),
                                                                                modifier = Modifier
                                                                            ) {
                                                                                val expandableItems =
                                                                                    it["items"] as List<Map<*, *>>
                                                                                expandableItems.forEach { expItem ->
                                                                                    val type5 =
                                                                                        when (val type =
                                                                                            extractType(
                                                                                                expItem["type"] as String
                                                                                            )) {
                                                                                            Pair(
                                                                                                "text",
                                                                                                "title"
                                                                                            ) -> "TitleText"

                                                                                            Pair(
                                                                                                "text",
                                                                                                "body"
                                                                                            ) -> "SubtitleText"

                                                                                            Pair(
                                                                                                "text",
                                                                                                "Subtitle"
                                                                                            ) -> "SubtitleText"

                                                                                            else -> type.first
                                                                                        }
                                                                                    when (type5) {
                                                                                        "verticalContainer" -> {
                                                                                            val VcontainerItem =
                                                                                                expItem["items"] as List<Map<*, *>>
                                                                                            VerticalContainer(
                                                                                                wrapContentHeight = true,
                                                                                                modifier = Modifier,
                                                                                                contents = {
                                                                                                    VcontainerItem.forEach { VContItem ->
                                                                                                        val type6 =
                                                                                                            when (val type =
                                                                                                                extractType(
                                                                                                                    VContItem["type"] as String
                                                                                                                )) {
                                                                                                                Pair(
                                                                                                                    "text",
                                                                                                                    "title"
                                                                                                                ) -> "TitleText"

                                                                                                                Pair(
                                                                                                                    "text",
                                                                                                                    "body"
                                                                                                                ) -> "SubtitleText"

                                                                                                                Pair(
                                                                                                                    "text",
                                                                                                                    "Subtitle"
                                                                                                                ) -> "SubtitleText"

                                                                                                                else -> type.first
                                                                                                            }
                                                                                                        when (type6) {
                                                                                                            "horizontalContainer" -> {
                                                                                                                val HcontainerItem =
                                                                                                                    VContItem["items"] as List<Map<*, *>>
                                                                                                                HorizontalContainer(
                                                                                                                    modifier = Modifier,
                                                                                                                    content = {
                                                                                                                        HcontainerItem.forEach { HContItem ->
                                                                                                                            val containertype =
                                                                                                                                when (val type =
                                                                                                                                    extractType(
                                                                                                                                        HContItem["type"] as String
                                                                                                                                    )) {
                                                                                                                                    Pair(
                                                                                                                                        "text",
                                                                                                                                        "title"
                                                                                                                                    ) -> "TitleText"

                                                                                                                                    Pair(
                                                                                                                                        "text",
                                                                                                                                        "body"
                                                                                                                                    ) -> "SubtitleText"

                                                                                                                                    Pair(
                                                                                                                                        "text",
                                                                                                                                        "Subtitle"
                                                                                                                                    ) -> "SubtitleText"

                                                                                                                                    else -> type.first
                                                                                                                                }
                                                                                                                            when (containertype) {
                                                                                                                                "button" -> {
                                                                                                                                    val buttonType =
                                                                                                                                        HContItem["subtype"]
                                                                                                                                            ?: "elevated"
                                                                                                                                    val buttonPadding =
                                                                                                                                        paddingValues(
                                                                                                                                            path = HContItem["margins"]
                                                                                                                                        )
                                                                                                                                    val action =
                                                                                                                                        HContItem["action"] as Map<*, *>
                                                                                                                                    val onclick: () -> Unit =
                                                                                                                                        {
                                                                                                                                            action.let { action ->
                                                                                                                                                when (action["type"]) {
                                                                                                                                                    "toast" -> {
                                                                                                                                                        val message =
                                                                                                                                                            action["message"] as String
                                                                                                                                                        val duration =
                                                                                                                                                            action["duration"] as String

                                                                                                                                                        ActionType_SnackBar(
                                                                                                                                                            context = context,
                                                                                                                                                            message = message,
                                                                                                                                                            duration = duration
                                                                                                                                                        )
                                                                                                                                                    }

                                                                                                                                                    "navigate" -> {
                                                                                                                                                        val screenName =
                                                                                                                                                            action["destination"] as String


                                                                                                                                                        CoroutineScope(
                                                                                                                                                            Dispatchers.IO
                                                                                                                                                        ).launch {
                                                                                                                                                            withContext(
                                                                                                                                                                Dispatchers.Main
                                                                                                                                                            ) {
                                                                                                                                                                NavController.navigate(
                                                                                                                                                                    route = screenName
                                                                                                                                                                )
                                                                                                                                                            }
                                                                                                                                                        }


                                                                                                                                                    }

                                                                                                                                                    "set_value" -> {
                                                                                                                                                        expanded.value =
                                                                                                                                                            !expanded.value
                                                                                                                                                    }

                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }


                                                                                                                                    when (buttonType) {
                                                                                                                                        "icon" -> {
                                                                                                                                            var label =
                                                                                                                                                HContItem["label"] as String
                                                                                                                                            var icon =
                                                                                                                                                HContItem["icon"] as String
                                                                                                                                            if (icon == "arrow") {
                                                                                                                                                if (expanded.value) {
                                                                                                                                                    icon =
                                                                                                                                                        "arrow_up"
                                                                                                                                                } else {
                                                                                                                                                    icon =
                                                                                                                                                        "arrow_down"
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            ButtonIcon(
                                                                                                                                                onclick = onclick,
                                                                                                                                                icon = icon,
                                                                                                                                                label = label,
                                                                                                                                                modifier = Modifier.then(
                                                                                                                                                    buttonPadding
                                                                                                                                                )
                                                                                                                                            )
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                )
                                                                                                            }

                                                                                                            "divider" -> {
                                                                                                                val paddings =
                                                                                                                    paddingValues(
                                                                                                                        path = VContItem["margins"]
                                                                                                                    )
                                                                                                                HorizontalDivider(
                                                                                                                    color = Color.Gray,
                                                                                                                    thickness = 1.dp,
                                                                                                                    modifier = Modifier.then(
                                                                                                                        paddings
                                                                                                                    )
                                                                                                                )
                                                                                                            }

                                                                                                            "abcd" -> {
                                                                                                                TextBlock(
                                                                                                                    text = organizationList.value[index1].toString()
                                                                                                                )
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            )
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }

                                                            }
                                                        )

                                                    }

                                                    "horizontal" -> {

                                                    }
                                                }
                                            }

                                            "TitleText" -> {
                                                val font_size =
                                                    templateItem["font_size"].toString()
                                                        .toFloat().toInt()
                                                val font_weight =
                                                    fontWeightMap[templateItem["font_weight"]]
                                                        ?: FontWeight.Black
                                                val paddings =
                                                    paddingValues(path = templateItem["margins"])
                                                val contentId = templateItem["#text"] as String


                                                val text =
                                                    if (contentItem[contentId].toString() == "null") {
                                                        val idx = (getDataFromDB(
                                                            context = context,
                                                            key = Stored_index
                                                        ).collectAsState(initial = 0).value).toString()
                                                            .toInt()
//                                                        organizationList.value[idx]["name"].toString()
                                                        if (organizationList.value.isEmpty()) {
                                                            idx.toString()
                                                        } else {
                                                            organizationList.value[idx]["name"].toString()
                                                        }
                                                    } else {
                                                        contentItem[contentId].toString()
                                                    }

                                                TitleText(
                                                    text = text,
                                                    fontWeight = font_weight,
                                                    fontSize = font_size,
                                                    modifier = Modifier.then(paddings)
                                                )
                                            }

                                            "SubtitleText" -> {
                                                val font_size =
                                                    templateItem["font_size"].toString()
                                                        .toFloat().toInt()
                                                val font_weight =
                                                    fontWeightMap[templateItem["font_weight"]]
                                                        ?: FontWeight.Black
                                                val paddings =
                                                    paddingValues(path = templateItem["margins"])

                                                val contentId = templateItem["#text"] as String

                                                val text =
                                                    if (contentItem[contentId].toString() == "null") {
                                                        val idx = (getDataFromDB(
                                                            context = context,
                                                            key = Stored_index
                                                        ).collectAsState(initial = 0).value).toString()
                                                            .toInt()
                                                        if (organizationList.value.isEmpty()) {
                                                            idx.toString()
                                                        } else {
                                                            organizationList.value[idx].filter { it.key != "name" }
                                                                .map { "${it.key} : ${it.value}" }
                                                                .joinToString("\n\n")
                                                        }
                                                    } else {
                                                        contentItem[contentId].toString()
                                                    }
                                                SubtitleText(
                                                    text = text,
                                                    fontSize = font_size,
                                                    fontWeight = font_weight,
                                                    modifier = Modifier.then(paddings)
                                                )
                                            }

                                            "inputText" -> {
                                                when ((templateItem["maxLines"] as? Number)?.toInt()
                                                    ?: 1) {
                                                    1 -> {
                                                        val keyboardType =
                                                            mapToKeyboardType[templateItem["keyboardType"]]
                                                        val font_size =
                                                            (templateItem["font_size"] as Number).toInt()
                                                        val font_weight =
                                                            fontWeightMap[templateItem["font_weight"]]
                                                        val paddings =
                                                            paddingValues(path = templateItem["margins"])
                                                        val suffixIcon =
                                                            templateItem["suffixIcon"]
                                                        val contentId =
                                                            templateItem["#text"] as String

                                                        val text =
                                                            contentItem[contentId].toString()

                                                        val existingValue =
                                                            textFieldValues.find { it.first == contentId }?.second
                                                                ?: ""

                                                        SingleLineInputText(
                                                            modifier = Modifier
                                                                .then(
                                                                    paddings
                                                                )
                                                                .fillMaxWidth(),
                                                            keyboardType = keyboardType
                                                                ?: KeyboardType.Text,
                                                            font_size = font_size,
                                                            fontWeight = font_weight,
                                                            suffixIcon = suffixIcon.toString(),
                                                            hintText = text,
                                                            value = existingValue,
                                                            isRequired = false,
                                                            onValueChange = { newValue ->
                                                                val index =
                                                                    textFieldValues.indexOfFirst { it.first == contentId }
                                                                if (index != -1) {
                                                                    textFieldValues[index] =
                                                                        textFieldValues[index].copy(
                                                                            second = newValue
                                                                        )
                                                                } else {
                                                                    textFieldValues.add(
                                                                        contentId to newValue
                                                                    )
                                                                }
                                                            }
                                                        )
                                                    }

                                                    else -> {
                                                        MultiLineInputText()
                                                    }
                                                }
                                            }

                                            "button" -> {
                                                val buttonType =
                                                    templateItem["subtype"] ?: "elevated"

                                                val font_size =
                                                    (templateItem["font_size"] as Number).toInt()
                                                val font_weight =
                                                    fontWeightMap[templateItem["font_weight"]]
                                                        ?: FontWeight.Normal
                                                val buttonPadding =
                                                    paddingValues(path = templateItem["margins"])
                                                val bgColor =
                                                    (templateItem["backgroundColor"] as? String)
                                                        ?: ""

                                                val contentId = templateItem["#text"] as String


                                                val action =
                                                    templateItem["action"] as Map<*, *>
                                                val onclick: () -> Unit = {
                                                    action.let {
                                                        when (it["type"]) {
                                                            "toast" -> {
                                                                val message =
                                                                    it["message"] as String
                                                                val duration =
                                                                    it["duration"] as String

                                                                ActionType_SnackBar(
                                                                    context = context,
                                                                    message = message,
                                                                    duration = duration
                                                                )
                                                            }

                                                            "navigate" -> {
                                                                val screenName =
                                                                    it["destination"] as String
                                                                val actionType =
                                                                    it["actionType"] as String

                                                                if (actionType == "authenticate") {
                                                                    if (AuthenticateUser(
                                                                            userName = textFieldValues.find { it.first == "usernameText" }?.second
                                                                                ?: "",
                                                                            password = textFieldValues.find { it.first == "passwordText" }?.second
                                                                                ?: ""
                                                                        )
                                                                    ) {
                                                                        NavController.navigate(
                                                                            screenName
                                                                        )
                                                                        textFieldValues.clear()
                                                                    } else {
                                                                        ActionType_SnackBar(
                                                                            context = context,
                                                                            message = "Invalid Credentials",
                                                                            duration = "short"
                                                                        )
                                                                    }
                                                                } else {
                                                                    NavController.navigate(
                                                                        screenName
                                                                    )
                                                                }
                                                            }

                                                            "dialog" -> {
                                                                val subtype =
                                                                    it["subtype"] as String
                                                                when (subtype) {
                                                                    "alert" -> {
                                                                        dialogTitle =
                                                                            it["title"] as String
                                                                        dialogMessage =
                                                                            it["message"] as String
                                                                        if (textFieldValues.find { it.first == "usernameText" }?.second == "12345" && textFieldValues.find { it.first == "passwordText" }?.second == "12345") {
                                                                            showalertdialog =
                                                                                true
                                                                        }
                                                                    }

                                                                    "simple" -> {
                                                                        dialogTitle =
                                                                            it["title"] as String
                                                                        dialogMessage =
                                                                            it["message"] as String
                                                                        if (textFieldValues.find { it.first == "usernameText" }?.second == "12345" && textFieldValues.find { it.first == "passwordText" }?.second == "12345") {
                                                                            showsimpledialog =
                                                                                true
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            "url" -> {
                                                                val url_address =
                                                                    it["url_address"] as String
                                                                ActionType_Url(
                                                                    url = url_address,
                                                                    context = context,
                                                                )
                                                            }
                                                        }
                                                    }
                                                }

                                                if (showalertdialog) {
                                                    ActionType_Alert(
                                                        onDismissRequest = {
                                                            showalertdialog = false
                                                        },
                                                        onConfirmation = {
                                                            showalertdialog = true
                                                        },
                                                        dialogTitle = dialogTitle,
                                                        dialogText = dialogMessage,
                                                    )
                                                }

                                                if (showsimpledialog) {
                                                    ActionType_SimpleDialog(
                                                        onDismissRequest = {
                                                            showsimpledialog = false
                                                        },
                                                        dialogTitle = dialogTitle,
                                                        dialogText = dialogMessage
                                                    )
                                                }


                                                val text = contentItem[contentId].toString()


                                                when (buttonType) {
                                                    "elevated" -> {
                                                        ButtonElevated(
                                                            text = text,
                                                            bgColor = bgColor,
                                                            textColor = "#fcfdff",
                                                            fontSize = font_size,
                                                            fontWeight = font_weight,
                                                            onclick = onclick,
                                                            modifier = Modifier
                                                                .then(buttonPadding),
                                                        )
                                                    }

                                                    "text" -> {
                                                        ButtonText(
                                                            text = text,
                                                            textColor = "#0f0f0f",
                                                            fontSize = font_size,
                                                            fontWeight = font_weight,
                                                            onclick = onclick,
                                                            modifier = Modifier
                                                                .then(buttonPadding),
                                                        )
                                                    }

                                                    "filled" -> {
                                                        ButtonFilled(
                                                            text = text,
                                                            textColor = "#0f0f0f",
                                                            fontSize = font_size,
                                                            fontWeight = font_weight,
                                                            onclick = onclick,
                                                            modifier = Modifier
                                                                .then(buttonPadding),
                                                        )
                                                    }

                                                    "icon" -> {
                                                        val icon =
                                                            templateItem["icon"] as String
                                                        ButtonIcon(
                                                            onclick = onclick,
                                                            icon = icon
                                                        )
                                                    }

                                                    "floatingAction" -> {
                                                        val icon =
                                                            templateItem["icon"] as String
                                                        ButtonFloatingAction(
                                                            onclick = onclick,
                                                            icon = icon,
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                },
                            )
                        }

                        "horizontal" -> {

                        }
                    }
                }

                else -> {
                    val type = contentItem["type"] as String
                    when (type) {
                        "button" -> {
                            val buttonType =
                                contentItem["subtype"] ?: "elevated"

                            val font_size =
                                (contentItem["font_size"] as Number).toInt()
                            val font_weight =
                                fontWeightMap[contentItem["font_weight"]]
                                    ?: FontWeight.Normal
                            val buttonPadding =
                                paddingValues(path = contentItem["margins"])
                            val bgColor =
                                (contentItem["backgroundColor"] as? String) ?: "#FF694ced"
                            val action =
                                contentItem["action"] as Map<*, *>
                            val onclick: () -> Unit = {
                                action.let {
                                    when (it["type"]) {
                                        "toast" -> {
                                            val message =
                                                it["message"] as String
                                            val duration =
                                                it["duration"] as String

                                            ActionType_SnackBar(
                                                context = context,
                                                message = message,
                                                duration = duration
                                            )
                                        }

                                        "navigate" -> {
                                            val screenName =
                                                it["destination"] as String

                                            if (textFieldValues.find { it.first == "usernameText" }?.second == "12345" && textFieldValues.find { it.first == "passwordText" }?.second == "12345") {

                                            } else {
                                                ActionType_SnackBar(
                                                    modifier = Modifier,
                                                    context = context,
                                                    message = "Invalid Credentials",
                                                    duration = "short"
                                                )
                                            }
                                        }

                                        "dialog" -> {
                                            val subtype =
                                                it["subtype"] as String
                                            when (subtype) {
                                                "alert" -> {
                                                    dialogTitle =
                                                        it["title"] as String
                                                    dialogMessage =
                                                        it["message"] as String
                                                    if (textFieldValues.find { it.first == "usernameText" }?.second == "12345" && textFieldValues.find { it.first == "passwordText" }?.second == "12345") {
                                                        showalertdialog =
                                                            true
                                                    }
                                                }

                                                "simple" -> {
                                                    dialogTitle =
                                                        it["title"] as String
                                                    dialogMessage = newUserData.toString()
                                                    showalertdialog = true
                                                }
                                            }
                                        }

                                        "url" -> {
                                            val url_address =
                                                it["url_address"] as String
                                            ActionType_Url(
                                                url = url_address,
                                                context = context,
                                            )
                                        }
                                    }
                                }
                            }

                            if (showalertdialog) {
                                ActionType_Alert(
                                    onDismissRequest = {
                                        showalertdialog = false
                                    },
                                    onConfirmation = {
                                        showalertdialog = true
                                    },
                                    dialogTitle = dialogTitle,
                                    dialogText = dialogMessage,
                                )
                            }

                            if (showsimpledialog) {
                                ActionType_SimpleDialog(
                                    onDismissRequest = {
                                        showsimpledialog = false
                                    },
                                    dialogTitle = dialogTitle,
                                    dialogText = dialogMessage
                                )
                            }

                            val text = contentItem["#text"] as String


                            when (buttonType) {
                                "elevated" -> {
                                    ButtonElevated(
                                        text = text,
                                        bgColor = bgColor,
                                        textColor = "#fcfdff",
                                        fontSize = font_size,
                                        fontWeight = font_weight,
                                        onclick = onclick,
                                        modifier = Modifier
                                            .then(buttonPadding),
                                    )
                                }

                                "text" -> {
                                    ButtonText(
                                        text = text,
                                        textColor = "#0f0f0f",
                                        fontSize = font_size,
                                        fontWeight = font_weight,
                                        onclick = onclick,
                                        modifier = Modifier
                                            .then(buttonPadding),
                                    )
                                }

                                "filled" -> {
                                    ButtonFilled(
                                        text = text,
                                        textColor = "#0f0f0f",
                                        fontSize = font_size,
                                        fontWeight = font_weight,
                                        onclick = onclick,
                                        modifier = Modifier
                                            .then(buttonPadding),
                                    )
                                }

                                "icon" -> {
                                    val icon =
                                        contentItem["icon"] as String
                                    ButtonIcon(
                                        onclick = onclick,
                                        icon = icon,
                                        modifier = Modifier
                                    )
                                }

                                "floatingAction" -> {
                                    val icon =
                                        contentItem["icon"] as String
                                    ButtonFloatingAction(
                                        onclick = onclick,
                                        icon = icon,
                                        modifier = Modifier
                                    )
                                }
                            }
                        }

                        "image" -> {
                            val image_url = contentItem["image_url"] as String
                            AsyncImage(
                                model = image_url,
                                contentDescription = null,
                                Modifier
                                    .padding(top = 20.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}