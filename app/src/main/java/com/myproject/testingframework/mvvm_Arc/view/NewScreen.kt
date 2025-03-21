package com.myproject.testingframework.mvvm_Arc.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
import com.myproject.composeflow.Components.Container.VerticalContainer
import com.myproject.composeflow.Components.Form.DropDownOption
import com.myproject.composeflow.Components.Image.Image
import com.myproject.composeflow.Components.Layouts.ItemLayout
import com.myproject.composeflow.Components.Layouts.ScaffoldLayout
import com.myproject.composeflow.Components.Layouts.SingleColumnLayout
import com.myproject.composeflow.Components.Layouts.SingleRowLayout
import com.myproject.composeflow.Components.Text.SingleLineInputText
import com.myproject.composeflow.Components.Text.SubtitleText
import com.myproject.composeflow.Components.Text.fontWeightMap
import com.myproject.composeflow.Components.Text.mapToKeyboardType
import com.myproject.composeflow.Utils.JsonParsing.ParseLocalJson
import com.myproject.composeflow.Utils.Padding.paddingValues
import com.myproject.composeflow.Utils.mapFontWeight
import com.myproject.composeflow.Utils.mapIcon
import com.myproject.composeflow.Utils.mapKeyBoardType
import com.myproject.composeflow.Utils.searchData
import com.myproject.composeflow.globalMap.textFieldValues
import com.myproject.testingframework.R
import com.myproject.testingframework.mvvm_Arc.model.DataManager.DataManager
import com.myproject.testingframework.mvvm_Arc.model.functions.AuthenticateUser
import com.myproject.testingframework.mvvm_Arc.viewmodel.MyViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
fun DynamicLayoutScreen(modifier: Modifier = Modifier, id: String, NavController: NavController) {
    //context
    val context = LocalContext.current

    var ResId = R.raw.new_login
    if (id == "01_login") {
        ResId = R.raw.new_login
    } else if (id == "02_signup") {
        ResId = R.raw.new_signup
    } else if (id == "03_home") {
        ResId = R.raw.new_home
    } else if (id == "04_detail") {
        ResId = R.raw.new_detail
    }

    //Screen Data
    val uiData = ParseLocalJson(context = context, id = ResId)

    val name = uiData["ScreenName"] as String
    val screenId = uiData["ScreenId"] as? String ?: ""
    val layoutData = uiData["layout"] as Map<*, *>

    val layoutType = layoutData["type"] as String


    //Dialog
    var showAlertDialog by remember { mutableStateOf(false) }
    var showSimpleDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }

    //screen padding
    val screenPadding = paddingValues(path = layoutData["padding"])

    //layoutChildren
    val layoutChildren = layoutData["children"] as List<*>

    //coroutine Scope and drawerState
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    when (layoutType) {
        "SingleColumnLayout" -> {

            SingleColumnLayout(modifier = Modifier.then(screenPadding)) {

                layoutChildren.forEach { item1 ->
                    val item1Data = item1 as Map<*, *>
                    val item1Type = item1Data["type"] as String
                    when (item1Type) {
                        "image" -> {
                            val imagePadding = paddingValues(path = item1Data["padding"])
                            val imageUrl = item1Data["image_url"] as String
                            val imageHeight = item1Data["height"] as? Int ?: 200
                            Image(
                                image_url = imageUrl,
                                modifier = Modifier
                                    .then(imagePadding)
                                    .fillMaxWidth()
                                    .height(imageHeight.dp)
                            )
                        }

                        "text" -> {
                            val textPadding = paddingValues(path = item1Data["padding"])
                            val fontSize =
                                (item1Data["font_size"] ?: 16).toString().toFloat().toInt()
                            val fontWeight =
                                mapFontWeight(fontWeight = item1Data["font_weight"] as? String)
                            val text = if (item1Data["text"] != null) {
                                item1Data["text"] as String
                            } else {
                                searchData(key = item1Data["\$text"] as String) as String
                            }
                            SubtitleText(
                                text = text,
                                fontSize = fontSize,
                                fontWeight = fontWeight,
                                modifier = Modifier.then(textPadding)
                            )
                        }

                        "SingleLineInputText" -> {
                            val inputPadding = paddingValues(path = item1Data["padding"])
                            val keyboardType =
                                mapKeyBoardType(keyboardType = item1Data["keyboardType"] as String)
                            val fontSize =
                                (item1Data["font_size"] ?: 16).toString().toFloat().toInt()
                            val fontWeight =
                                mapFontWeight(fontWeight = item1Data["font_weight"] as? String)
                            val suffixIcon = item1Data["suffixIcon"] as? String
                            val text = if (item1Data["text"] != null) {
                                item1Data["text"] as String
                            } else {
                                searchData(key = item1Data["\$text"] as String) as String
                            }

                            SingleLineInputText(
                                keyboardType = keyboardType,
                                fontWeight = fontWeight,
                                hintText = text,
                                value = "",
                                onValueChange = {},
                                isRequired = false,
                                font_size = fontSize,
                                suffixIcon = suffixIcon,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(inputPadding)
                            )
                        }

                        "button" -> {
                            val buttonType =
                                item1Data["subtype"] ?: "elevated"

                            val font_size =
                                (item1Data["font_size"] as? Number)?.toInt() ?: 0
                            val font_weight =
                                mapFontWeight(item1Data["font_weight"] as? String ?: "")
                            val buttonPadding =
                                paddingValues(path = item1Data["padding"])
                            val bgColor =
                                (item1Data["backgroundColor"] as? String)
                                    ?: ""

                            val action =
                                item1Data["action"] as Map<*, *>
                            val onclick: () -> Unit = {
                                action.let { act ->
                                    when (act["type"]) {
                                        "toast" -> {
                                            val message = act["message"] as? String ?: "No Message"
                                            val duration = act["duration"] as? String ?: "short"

                                            ActionType_SnackBar(
                                                context = context,
                                                message = message,
                                                duration = duration
                                            )
                                        }

                                        "navigate" -> {
                                            val destinationScreen =
                                                act["destination"] as? String ?: ""
                                            val actionType = act["actionType"] as? String ?: ""

                                            NavController.navigate(destinationScreen)
                                        }

                                        "dialog" -> {
                                            val subType = act["subtype"] as? String ?: "simple"
                                            when (subType) {
                                                "alert" -> {
                                                    val title =
                                                        act["title"] as? String ?: "No title"
                                                    val message =
                                                        act["message"] as? String ?: "No Message"
                                                    showAlertDialog = true
                                                    dialogTitle = title
                                                    dialogMessage = message
                                                }

                                                "simple" -> {
                                                    val title =
                                                        act["title"] as? String ?: "No title"
                                                    val message =
                                                        act["message"] as? String ?: "No Message"
                                                    showSimpleDialog = true
                                                    dialogTitle = title
                                                    dialogMessage = message
                                                }
                                            }
                                        }

                                        "url" -> {
                                            val url = act["url"] as? String ?: ""
                                            ActionType_Url(url = url, context = context)
                                        }
                                    }
                                }
                            }

                            if (showAlertDialog) {
                                ActionType_Alert(
                                    onDismissRequest = {
                                        showAlertDialog = false
                                    },
                                    onConfirmation = {
                                        showAlertDialog = true
                                    },
                                    dialogTitle = dialogTitle,
                                    dialogText = dialogMessage,
                                )
                            }

                            if (showSimpleDialog) {
                                ActionType_SimpleDialog(
                                    onDismissRequest = {
                                        showSimpleDialog = false
                                    },
                                    dialogTitle = dialogTitle,
                                    dialogText = dialogMessage
                                )
                            }


                            val text = if (item1Data["text"] != null) {
                                item1Data["text"] as? String ?: ""
                            } else {
                                searchData(key = item1Data["\$text"] as String)
                            }


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
                                        item1Data["icon"] as String
                                    ButtonIcon(
                                        onclick = onclick,
                                        icon = icon
                                    )
                                }

                                "floatingAction" -> {
                                    val icon =
                                        item1Data["icon"] as String
                                    ButtonFloatingAction(
                                        onclick = onclick,
                                        icon = icon,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        "ScaffoldLayout" -> {
            val topBarContent = layoutData["topBar"] as Map<*, *>
            val bottomBarContent = layoutData["bottomBar"] as Map<*, *>

            ScaffoldLayout(
                NavController = NavController,
                modifier = Modifier.then(screenPadding),
                drawerState = drawerState,
                topBarContent = topBarContent,
                bottomBarContent = {},
                scope = scope,
                mainContent = {
                    layoutChildren.forEach { item1 ->
                        val item1Data = item1 as Map<*, *>
                        val item1Type = item1Data["type"] as String

                        when (item1Type) {
                            "SingleColumnLayout" -> {
                                val layoutPadding = paddingValues(item1Data["padding"])
                                val items = item1Data["children"] as List<*>
                                SingleColumnLayout(modifier = Modifier.then(layoutPadding)) {
                                    items.forEach { item2 ->
                                        val item2Data = item2 as Map<*, *>
                                        val item2Type = item2Data["type"] as String
                                        when (item2Type) {
                                            "boxContainer" -> {
                                                val boxPadding = paddingValues(item2["padding"])
                                                val bgColor = (item2["bgColor"] as? String
                                                    ?: "#f5f5f5").toColorInt()
                                                val wrap = item2["contentHeight"] == "wrap"
                                                val boxItems = item2["children"] as List<*>
                                                BoxContainer(
                                                    modifier = Modifier
                                                        .then(boxPadding)
                                                        .clip(shape = RoundedCornerShape(10.dp))
                                                        .background(
                                                            Color(bgColor),
                                                            shape = RoundedCornerShape(5.dp)
                                                        ),
                                                    wrapContentHeight = wrap
                                                ) {
                                                    boxItems.forEach { item3 ->
                                                        val item3Data = item3 as Map<*, *>
                                                        val item3Type = item3Data["type"] as String
                                                        when (item3Type) {
                                                            "VerticalContainer" -> {
                                                                val containerPadding =
                                                                    paddingValues(path = item3Data["padding"])
                                                                val vContainerItem =
                                                                    item3Data["children"] as List<*>
                                                                VerticalContainer(
                                                                    modifier = Modifier.then(
                                                                        containerPadding
                                                                    ),
                                                                    contents = {
                                                                        vContainerItem.forEach { vitem ->
                                                                            val vItemData =
                                                                                vitem as Map<*, *>
                                                                            val vItemType =
                                                                                vItemData["type"] as String
                                                                            when (vItemType) {
                                                                                "text" -> {
                                                                                    val textPadding =
                                                                                        paddingValues(
                                                                                            path = vItemData["padding"]
                                                                                        )
                                                                                    val fontSize =
                                                                                        (vItemData["font_size"]
                                                                                            ?: 16).toString()
                                                                                            .toFloat()
                                                                                            .toInt()
                                                                                    val fontWeight =
                                                                                        mapFontWeight(
                                                                                            fontWeight = vItemData["font_weight"] as? String
                                                                                                ?: ""
                                                                                        )
                                                                                    val text =
                                                                                        if (vItemData["text"] != null) {
                                                                                            vItemData["text"] as String
                                                                                        } else {
                                                                                            searchData(
                                                                                                key = vItemData["\$text"] as String
                                                                                            )
                                                                                        }

                                                                                    SubtitleText(
                                                                                        text = text,
                                                                                        fontSize = fontSize,
                                                                                        fontWeight = fontWeight,
                                                                                        modifier = Modifier.then(
                                                                                            textPadding
                                                                                        )
                                                                                    )
                                                                                }

                                                                                "SingleLineInputText" -> {
                                                                                    val inputPadding =
                                                                                        paddingValues(
                                                                                            path = vItemData["padding"]
                                                                                        )
                                                                                    val keyBoardType =
                                                                                        mapKeyBoardType(
                                                                                            keyboardType = vItemData["keyboardType"] as? String
                                                                                                ?: ""
                                                                                        )
                                                                                    val suffixIcon =
                                                                                        if (vItemData["suffixIcon"] != null) {
                                                                                            vItemData["suffixIcon"] as String
                                                                                        } else {
                                                                                            null
                                                                                        }
                                                                                    val fontSize =
                                                                                        (vItemData["font_size"] as? Number
                                                                                            ?: 20).toFloat()
                                                                                            .toInt()
                                                                                    val fontWeight =
                                                                                        mapFontWeight(
                                                                                            fontWeight = vItemData["font_weight"] as? String
                                                                                                ?: ""
                                                                                        )
                                                                                    val isRequired =
                                                                                        vItemData["isRequired"] as? Boolean
                                                                                            ?: false
                                                                                    val hintText =
                                                                                        if (vItemData["text"] != null) {
                                                                                            vItemData["text"] as String
                                                                                        } else {
                                                                                            searchData(
                                                                                                key = vItemData["\$text"] as String
                                                                                            )
                                                                                        }

                                                                                    SingleLineInputText(
                                                                                        keyboardType = keyBoardType,
                                                                                        fontWeight = fontWeight,
                                                                                        hintText = hintText,
                                                                                        value = "",
                                                                                        onValueChange = {},
                                                                                        isRequired = isRequired,
                                                                                        font_size = fontSize,
                                                                                        suffixIcon = suffixIcon,
                                                                                        modifier = Modifier
                                                                                            .then(
                                                                                                inputPadding
                                                                                            )
                                                                                            .fillMaxWidth()
                                                                                    )

                                                                                }

                                                                                "SingleRowLayout" -> {
                                                                                    val rowItems =
                                                                                        vItemData["children"] as List<*>
                                                                                    SingleRowLayout(
                                                                                        modifier = Modifier.wrapContentSize(),
                                                                                        spacedBy = 0,
                                                                                        content = {
                                                                                            rowItems.forEach { rItem ->
                                                                                                val rItemData =
                                                                                                    rItem as Map<*, *>
                                                                                                val rItemType =
                                                                                                    rItemData["type"] as String
                                                                                                when (rItemType) {
                                                                                                    "SingleLineInputText" -> {
                                                                                                        val inputPadding =
                                                                                                            paddingValues(
                                                                                                                path = rItemData["padding"]
                                                                                                            )
                                                                                                        val keyBoardType =
                                                                                                            mapKeyBoardType(
                                                                                                                keyboardType = rItemData["keyboardType"] as? String
                                                                                                                    ?: ""
                                                                                                            )
                                                                                                        val suffixIcon =
                                                                                                            if (rItemData["suffixIcon"] != null) {
                                                                                                                rItemData["suffixIcon"] as String
                                                                                                            } else {
                                                                                                                null
                                                                                                            }
                                                                                                        val fontSize =
                                                                                                            (rItemData["font_size"] as? Number
                                                                                                                ?: 20).toFloat()
                                                                                                                .toInt()
                                                                                                        val fontWeight =
                                                                                                            mapFontWeight(
                                                                                                                fontWeight = rItemData["font_weight"] as? String
                                                                                                                    ?: ""
                                                                                                            )
                                                                                                        val isRequired =
                                                                                                            rItemData["isRequired"] as? Boolean
                                                                                                                ?: false
                                                                                                        val hintText =
                                                                                                            if (rItemData["text"] != null) {
                                                                                                                rItemData["text"] as String
                                                                                                            } else {
                                                                                                                searchData(
                                                                                                                    key = rItemData["\$text"] as String
                                                                                                                )
                                                                                                            }

                                                                                                        SingleLineInputText(
                                                                                                            keyboardType = keyBoardType,
                                                                                                            fontWeight = fontWeight,
                                                                                                            hintText = hintText,
                                                                                                            value = "",
                                                                                                            onValueChange = {},
                                                                                                            isRequired = isRequired,
                                                                                                            font_size = fontSize,
                                                                                                            suffixIcon = suffixIcon,
                                                                                                            modifier = Modifier
                                                                                                                .weight(
                                                                                                                    1f
                                                                                                                )
                                                                                                                .then(
                                                                                                                    inputPadding
                                                                                                                )
                                                                                                        )
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        },
                                                                                    )
                                                                                }

                                                                                "dropdown" -> {
                                                                                    val dropdownId =
                                                                                        vItemData["id"] as String
                                                                                    val dropdownPadding =
                                                                                        paddingValues(
                                                                                            path = vItemData["padding"]
                                                                                        )
                                                                                    val fontSize =
                                                                                        (vItemData["font_size"] as? Number
                                                                                            ?: 16).toString()
                                                                                            .toFloat()
                                                                                            .toInt()
                                                                                    val fontWeight =
                                                                                        mapFontWeight(
                                                                                            fontWeight = vItemData["font_weight"] as? String
                                                                                                ?: ""
                                                                                        )
                                                                                    val options =
                                                                                        vItemData["options"] as List<String>
                                                                                    val isRequired =
                                                                                        vItemData["isRequired"] as? Boolean
                                                                                            ?: false
                                                                                    val hintText =
                                                                                        if (vItemData["text"] != null) {
                                                                                            vItemData["text"] as String
                                                                                        } else {
                                                                                            searchData(
                                                                                                key = vItemData["\$text"] as String
                                                                                            )
                                                                                        }

                                                                                    var expand by remember(
                                                                                        dropdownId
                                                                                    ) {
                                                                                        mutableStateOf(
                                                                                            false
                                                                                        )
                                                                                    }

                                                                                    DropDownOption(
                                                                                        modifier = Modifier
                                                                                            .fillMaxWidth()
                                                                                            .then(
                                                                                                dropdownPadding
                                                                                            ),
                                                                                        fontWeight = fontWeight,
                                                                                        font_size = fontSize,
                                                                                        hintText = hintText,
                                                                                        options = options,
                                                                                        selectedOption = "",
                                                                                        onOptionSelected = {},
                                                                                        expanded = expand,
                                                                                        onExpandedChange = {
                                                                                            expand =
                                                                                                !expand
                                                                                        },
                                                                                        isRequired = isRequired
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
                            }

                            "ItemLayout" -> {
                                val children = item1Data["children"] as List<*>
                                val items = children.map { it as Map<*, *> }
                                val headerComponents = items.takeWhile { it["type"] != "list" }
                                val listItems = items.filter { it["type"] == "list" }[0]
                                val footerComponents =
                                    items.dropWhile { it["type"] != "list" }.drop(1)

                                val itemCount =
                                    (listItems["itemCount"] as? Number)?.toFloat()?.toInt() ?: 0
                                val rowPadding = paddingValues(path = listItems["padding"])

                                ItemLayout(
                                    modifier = Modifier.then(screenPadding),
                                    count = DataManager.organizationDataList.size,
                                    headerContent = {
                                        headerComponents.forEach { hItem ->
                                            val hItemType = hItem["type"] as String
                                            when (hItemType) {
                                                "text" -> {
                                                    val textPadding =
                                                        paddingValues(path = hItem["padding"])
                                                    val fontSize =
                                                        (hItem["font_size"] ?: 16).toString()
                                                            .toFloat().toInt()
                                                    val fontWeight =
                                                        mapFontWeight(fontWeight = hItem["font_weight"] as? String)
                                                    val text = if (hItem["text"] != null) {
                                                        hItem["text"] as String
                                                    } else {
                                                        searchData(key = hItem["\$text"] as String) as String
                                                    }
                                                    SubtitleText(
                                                        text = text,
                                                        fontSize = fontSize,
                                                        fontWeight = fontWeight,
                                                        modifier = Modifier.then(textPadding)
                                                    )
                                                }
                                            }
                                        }
                                    },
                                    footerContent = {
                                        footerComponents.forEach { fItem ->
                                            val fItemType = fItem["type"] as String
                                            when (fItemType) {
                                                "text" -> {
                                                    val textPadding =
                                                        paddingValues(path = fItem["padding"])
                                                    val fontSize =
                                                        (fItem["font_size"] ?: 16).toString()
                                                            .toFloat().toInt()
                                                    val fontWeight =
                                                        mapFontWeight(fontWeight = fItem["font_weight"] as? String)
                                                    val text = if (fItem["text"] != null) {
                                                        fItem["text"] as String
                                                    } else {
                                                        searchData(key = fItem["\$text"] as String) as String
                                                    }
                                                    SubtitleText(
                                                        text = text,
                                                        fontSize = fontSize,
                                                        fontWeight = fontWeight,
                                                        modifier = Modifier.then(textPadding)
                                                    )
                                                }
                                            }
                                        }
                                    },
                                    content = { index ->
                                        val items = listItems["children"] as Map<*, *>
                                        val itemType = items["type"] as String
                                        when (itemType) {
                                            "SingleRowLayout" -> {
                                                val rowItems = items["children"] as List<*>
                                                SingleRowLayout(
                                                    modifier = Modifier.then(rowPadding),
                                                    content = {
                                                        rowItems.forEach { rItem ->
                                                            val rItemData = rItem as Map<*, *>
                                                            val rItemType =
                                                                rItemData["type"] as String
                                                            when (rItemType) {

                                                                "image" -> {
                                                                    val imagePadding =
                                                                        paddingValues(path = rItemData["padding"])
                                                                    val imageUrl =
                                                                        rItemData["image_url"] as String
                                                                    val imageHeight =
                                                                        (rItemData["height"] as? Number
                                                                            ?: 50).toFloat()
                                                                            .toInt()
                                                                    Image(
                                                                        image_url = imageUrl,
                                                                        modifier = Modifier
                                                                            .height(imageHeight.dp)
                                                                            .then(imagePadding)
                                                                    )
                                                                }

                                                                "VerticalContainer" -> {
                                                                    val containerPadding =
                                                                        paddingValues(path = rItemData["padding"])
                                                                    val contItem =
                                                                        rItemData["children"] as List<*>

                                                                    VerticalContainer(
                                                                        modifier = Modifier
                                                                            .then(containerPadding)
                                                                            .weight(1f),
                                                                        contents = {
                                                                            contItem.forEach { CItem ->
                                                                                val CItemData =
                                                                                    CItem as Map<*, *>
                                                                                val CItemType =
                                                                                    CItemData["type"] as String
                                                                                when (CItemType) {
                                                                                    "text" -> {
                                                                                        val textPadding =
                                                                                            paddingValues(
                                                                                                path = CItemData["padding"]
                                                                                            )
                                                                                        val fontSize =
                                                                                            (CItemData["font_size"]
                                                                                                ?: 16).toString()
                                                                                                .toFloat()
                                                                                                .toInt()
                                                                                        val fontWeight =
                                                                                            mapFontWeight(
                                                                                                fontWeight = CItemData["font_weight"] as? String
                                                                                            )
                                                                                        val text =
                                                                                            if (CItemData["text"] != null) {
                                                                                                CItemData["text"] as String
                                                                                            } else {
//                                                                            searchData(key = CItemData["\$text"] as String)
                                                                                                (DataManager.organizationDataList[index][CItemData["\$text"]]
                                                                                                    ?: "Incorrect Variable name")
                                                                                            }

                                                                                        SubtitleText(
                                                                                            modifier = Modifier.then(
                                                                                                textPadding
                                                                                            ),
                                                                                            fontSize = fontSize,
                                                                                            fontWeight = fontWeight,
                                                                                            text = text
                                                                                        )
                                                                                    }
                                                                                }
                                                                            }
                                                                        },
                                                                    )
                                                                }

                                                                "button" -> {
                                                                    val buttonType =
                                                                        rItemData["subtype"]
                                                                            ?: "elevated"
                                                                    val buttonPadding =
                                                                        paddingValues(path = rItemData["padding"])

                                                                    val font_size =
                                                                        (rItemData["font_size"] as? Number)?.toInt()
                                                                            ?: 0
                                                                    val font_weight =
                                                                        mapFontWeight(
                                                                            rItemData["font_weight"] as? String
                                                                                ?: ""
                                                                        )
                                                                    val bgColor =
                                                                        (rItemData["backgroundColor"] as? String)
                                                                            ?: ""

                                                                    val action =
                                                                        rItemData["action"] as Map<*, *>
                                                                    val onclick: () -> Unit = {
                                                                        action.let { act ->
                                                                            when (act["type"]) {
                                                                                "toast" -> {
                                                                                    val message =
                                                                                        act["message"] as? String
                                                                                            ?: "No Message"
                                                                                    val duration =
                                                                                        act["duration"] as? String
                                                                                            ?: "short"

                                                                                    ActionType_SnackBar(
                                                                                        context = context,
                                                                                        message = message,
                                                                                        duration = duration
                                                                                    )
                                                                                }

                                                                                "navigate" -> {
                                                                                    val destinationScreen =
                                                                                        act["destination"] as? String
                                                                                            ?: ""
                                                                                    val actionType =
                                                                                        act["actionType"] as? String
                                                                                            ?: ""

                                                                                    NavController.navigate(
                                                                                        destinationScreen
                                                                                    )
                                                                                }

                                                                                "dialog" -> {
                                                                                    val subType =
                                                                                        act["subtype"] as? String
                                                                                            ?: "simple"
                                                                                    when (subType) {
                                                                                        "alert" -> {
                                                                                            val title =
                                                                                                act["title"] as? String
                                                                                                    ?: "No title"
                                                                                            val message =
                                                                                                act["message"] as? String
                                                                                                    ?: "No Message"
                                                                                            showAlertDialog =
                                                                                                true
                                                                                            dialogTitle =
                                                                                                title
                                                                                            dialogMessage =
                                                                                                message
                                                                                        }

                                                                                        "simple" -> {
                                                                                            val title =
                                                                                                act["title"] as? String
                                                                                                    ?: "No title"
                                                                                            val message =
                                                                                                act["message"] as? String
                                                                                                    ?: "No Message"
                                                                                            showSimpleDialog =
                                                                                                true
                                                                                            dialogTitle =
                                                                                                title
                                                                                            dialogMessage =
                                                                                                message
                                                                                        }
                                                                                    }
                                                                                }

                                                                                "url" -> {
                                                                                    val url =
                                                                                        act["url"] as? String
                                                                                            ?: ""
                                                                                    ActionType_Url(
                                                                                        url = url,
                                                                                        context = context
                                                                                    )
                                                                                }
                                                                            }
                                                                        }
                                                                    }

                                                                    if (showAlertDialog) {
                                                                        ActionType_Alert(
                                                                            onDismissRequest = {
                                                                                showAlertDialog =
                                                                                    false
                                                                            },
                                                                            onConfirmation = {
                                                                                showAlertDialog =
                                                                                    true
                                                                            },
                                                                            dialogTitle = dialogTitle,
                                                                            dialogText = dialogMessage,
                                                                        )
                                                                    }

                                                                    if (showSimpleDialog) {
                                                                        ActionType_SimpleDialog(
                                                                            onDismissRequest = {
                                                                                showSimpleDialog =
                                                                                    false
                                                                            },
                                                                            dialogTitle = dialogTitle,
                                                                            dialogText = dialogMessage
                                                                        )
                                                                    }


                                                                    when (buttonType) {
                                                                        "elevated" -> {

                                                                            val text =
                                                                                if (rItemData["text"] != null) {
                                                                                    rItemData["text"] as? String
                                                                                        ?: ""
                                                                                } else {
                                                                                    searchData(key = rItemData["\$text"] as String)
                                                                                }

                                                                            ButtonElevated(
                                                                                text = text,
                                                                                bgColor = bgColor,
                                                                                textColor = "#fcfdff",
                                                                                fontSize = font_size,
                                                                                fontWeight = font_weight,
                                                                                onclick = onclick,
                                                                                modifier = Modifier
                                                                                    .then(
                                                                                        buttonPadding
                                                                                    ),
                                                                            )
                                                                        }

                                                                        "text" -> {

                                                                            val text =
                                                                                if (rItemData["text"] != null) {
                                                                                    rItemData["text"] as? String
                                                                                        ?: ""
                                                                                } else {
                                                                                    searchData(key = rItemData["\$text"] as String)
                                                                                }

                                                                            ButtonText(
                                                                                text = text,
                                                                                textColor = "#0f0f0f",
                                                                                fontSize = font_size,
                                                                                fontWeight = font_weight,
                                                                                onclick = onclick,
                                                                                modifier = Modifier
                                                                                    .then(
                                                                                        buttonPadding
                                                                                    ),
                                                                            )
                                                                        }

                                                                        "filled" -> {

                                                                            val text =
                                                                                if (rItemData["text"] != null) {
                                                                                    rItemData["text"] as? String
                                                                                        ?: ""
                                                                                } else {
                                                                                    searchData(key = rItemData["\$text"] as String)
                                                                                }

                                                                            ButtonFilled(
                                                                                text = text,
                                                                                textColor = "#0f0f0f",
                                                                                fontSize = font_size,
                                                                                fontWeight = font_weight,
                                                                                onclick = onclick,
                                                                                modifier = Modifier
                                                                                    .then(
                                                                                        buttonPadding
                                                                                    ),
                                                                            )
                                                                        }

                                                                        "icon" -> {
                                                                            val icon =
                                                                                rItemData["icon"] as String
                                                                            val size =
                                                                                (rItemData["size"] as? Number
                                                                                    ?: 0).toFloat()
                                                                                    .toInt()
                                                                            ButtonIcon(
                                                                                modifier = Modifier
                                                                                    .then(
                                                                                        buttonPadding
                                                                                    )
                                                                                    .size(size.dp),
                                                                                onclick = onclick,
                                                                                icon = icon
                                                                            )
                                                                        }

                                                                        "floatingAction" -> {
                                                                            val icon =
                                                                                rItemData["icon"] as String
                                                                            ButtonFloatingAction(
                                                                                onclick = onclick,
                                                                                icon = icon,
                                                                            )
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                    },
                                )
                            }
                        }
                    }
                }
            )
        }

        "ItemLayout" -> {

            val items = layoutChildren.map { it as Map<*, *> }
            val headerComponents = items.takeWhile { it["type"] != "list" }
            val listItems = items.filter { it["type"] == "list" }[0]
            val footerComponents = items.dropWhile { it["type"] != "list" }.drop(1)

            val itemCount = (listItems["itemCount"] as? Number)?.toFloat()?.toInt() ?: 0
            val rowPadding = paddingValues(path = listItems["padding"])

            ItemLayout(
                modifier = Modifier.then(screenPadding),
                count = DataManager.organizationDataList.size,
                headerContent = {
                    headerComponents.forEach { hItem ->
                        val hItemType = hItem["type"] as String
                        when (hItemType) {
                            "text" -> {
                                val textPadding = paddingValues(path = hItem["padding"])
                                val fontSize =
                                    (hItem["font_size"] ?: 16).toString().toFloat().toInt()
                                val fontWeight =
                                    mapFontWeight(fontWeight = hItem["font_weight"] as? String)
                                val text = if (hItem["text"] != null) {
                                    hItem["text"] as String
                                } else {
                                    searchData(key = hItem["\$text"] as String) as String
                                }
                                SubtitleText(
                                    text = text,
                                    fontSize = fontSize,
                                    fontWeight = fontWeight,
                                    modifier = Modifier.then(textPadding)
                                )
                            }
                        }
                    }
                },
                footerContent = {
                    footerComponents.forEach { fItem ->
                        val fItemType = fItem["type"] as String
                        when (fItemType) {
                            "text" -> {
                                val textPadding = paddingValues(path = fItem["padding"])
                                val fontSize =
                                    (fItem["font_size"] ?: 16).toString().toFloat().toInt()
                                val fontWeight =
                                    mapFontWeight(fontWeight = fItem["font_weight"] as? String)
                                val text = if (fItem["text"] != null) {
                                    fItem["text"] as String
                                } else {
                                    searchData(key = fItem["\$text"] as String) as String
                                }
                                SubtitleText(
                                    text = text,
                                    fontSize = fontSize,
                                    fontWeight = fontWeight,
                                    modifier = Modifier.then(textPadding)
                                )
                            }
                        }
                    }
                },
                content = { index ->
                    val items = listItems["children"] as Map<*, *>
                    val itemType = items["type"] as String
                    when (itemType) {
                        "SingleRowLayout" -> {
                            val rowItems = items["children"] as List<*>
                            SingleRowLayout(
                                modifier = Modifier.then(rowPadding),
                                content = {
                                    rowItems.forEach { rItem ->
                                        val rItemData = rItem as Map<*, *>
                                        val rItemType = rItemData["type"] as String
                                        when (rItemType) {

                                            "image" -> {
                                                val imagePadding =
                                                    paddingValues(path = rItemData["padding"])
                                                val imageUrl = rItemData["image_url"] as String
                                                val imageHeight =
                                                    (rItemData["height"] as? Number ?: 50).toFloat()
                                                        .toInt()
                                                Image(
                                                    image_url = imageUrl,
                                                    modifier = Modifier
                                                        .height(imageHeight.dp)
                                                        .then(imagePadding)
                                                )
                                            }

                                            "VerticalContainer" -> {
                                                val containerPadding =
                                                    paddingValues(path = rItemData["padding"])
                                                val contItem = rItemData["children"] as List<*>

                                                VerticalContainer(
                                                    modifier = Modifier
                                                        .then(containerPadding)
                                                        .weight(1f),
                                                    contents = {
                                                        contItem.forEach { CItem ->
                                                            val CItemData = CItem as Map<*, *>
                                                            val CItemType =
                                                                CItemData["type"] as String
                                                            when (CItemType) {
                                                                "text" -> {
                                                                    val textPadding =
                                                                        paddingValues(path = CItemData["padding"])
                                                                    val fontSize =
                                                                        (CItemData["font_size"]
                                                                            ?: 16).toString()
                                                                            .toFloat().toInt()
                                                                    val fontWeight =
                                                                        mapFontWeight(fontWeight = CItemData["font_weight"] as? String)
                                                                    val text =
                                                                        if (CItemData["text"] != null) {
                                                                            CItemData["text"] as String
                                                                        } else {
//                                                                            searchData(key = CItemData["\$text"] as String)
                                                                            (DataManager.organizationDataList[index][CItemData["\$text"]]
                                                                                ?: "Incorrect Variable name")
                                                                        }

                                                                    SubtitleText(
                                                                        modifier = Modifier.then(
                                                                            textPadding
                                                                        ),
                                                                        fontSize = fontSize,
                                                                        fontWeight = fontWeight,
                                                                        text = text
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    },
                                                )
                                            }

                                            "button" -> {
                                                val buttonType =
                                                    rItemData["subtype"] ?: "elevated"
                                                val buttonPadding =
                                                    paddingValues(path = rItemData["padding"])

                                                val font_size =
                                                    (rItemData["font_size"] as? Number)?.toInt()
                                                        ?: 0
                                                val font_weight =
                                                    mapFontWeight(
                                                        rItemData["font_weight"] as? String ?: ""
                                                    )
                                                val bgColor =
                                                    (rItemData["backgroundColor"] as? String)
                                                        ?: ""

                                                val action =
                                                    rItemData["action"] as Map<*, *>
                                                val onclick: () -> Unit = {
                                                    action.let { act ->
                                                        when (act["type"]) {
                                                            "toast" -> {
                                                                val message =
                                                                    act["message"] as? String
                                                                        ?: "No Message"
                                                                val duration =
                                                                    act["duration"] as? String
                                                                        ?: "short"

                                                                ActionType_SnackBar(
                                                                    context = context,
                                                                    message = message,
                                                                    duration = duration
                                                                )
                                                            }

                                                            "navigate" -> {
                                                                val destinationScreen =
                                                                    act["destination"] as? String
                                                                        ?: ""
                                                                val actionType =
                                                                    act["actionType"] as? String
                                                                        ?: ""

                                                                NavController.navigate(
                                                                    destinationScreen
                                                                )
                                                            }

                                                            "dialog" -> {
                                                                val subType =
                                                                    act["subtype"] as? String
                                                                        ?: "simple"
                                                                when (subType) {
                                                                    "alert" -> {
                                                                        val title =
                                                                            act["title"] as? String
                                                                                ?: "No title"
                                                                        val message =
                                                                            act["message"] as? String
                                                                                ?: "No Message"
                                                                        showAlertDialog = true
                                                                        dialogTitle = title
                                                                        dialogMessage = message
                                                                    }

                                                                    "simple" -> {
                                                                        val title =
                                                                            act["title"] as? String
                                                                                ?: "No title"
                                                                        val message =
                                                                            act["message"] as? String
                                                                                ?: "No Message"
                                                                        showSimpleDialog = true
                                                                        dialogTitle = title
                                                                        dialogMessage = message
                                                                    }
                                                                }
                                                            }

                                                            "url" -> {
                                                                val url =
                                                                    act["url"] as? String ?: ""
                                                                ActionType_Url(
                                                                    url = url,
                                                                    context = context
                                                                )
                                                            }
                                                        }
                                                    }
                                                }

                                                if (showAlertDialog) {
                                                    ActionType_Alert(
                                                        onDismissRequest = {
                                                            showAlertDialog = false
                                                        },
                                                        onConfirmation = {
                                                            showAlertDialog = true
                                                        },
                                                        dialogTitle = dialogTitle,
                                                        dialogText = dialogMessage,
                                                    )
                                                }

                                                if (showSimpleDialog) {
                                                    ActionType_SimpleDialog(
                                                        onDismissRequest = {
                                                            showSimpleDialog = false
                                                        },
                                                        dialogTitle = dialogTitle,
                                                        dialogText = dialogMessage
                                                    )
                                                }


                                                when (buttonType) {
                                                    "elevated" -> {

                                                        val text = if (rItemData["text"] != null) {
                                                            rItemData["text"] as? String ?: ""
                                                        } else {
                                                            searchData(key = rItemData["\$text"] as String)
                                                        }

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

                                                        val text = if (rItemData["text"] != null) {
                                                            rItemData["text"] as? String ?: ""
                                                        } else {
                                                            searchData(key = rItemData["\$text"] as String)
                                                        }

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

                                                        val text = if (rItemData["text"] != null) {
                                                            rItemData["text"] as? String ?: ""
                                                        } else {
                                                            searchData(key = rItemData["\$text"] as String)
                                                        }

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
                                                            rItemData["icon"] as String
                                                        val size = (rItemData["size"] as? Number
                                                            ?: 0).toFloat().toInt()
                                                        ButtonIcon(
                                                            modifier = Modifier
                                                                .then(buttonPadding)
                                                                .size(size.dp),
                                                            onclick = onclick,
                                                            icon = icon
                                                        )
                                                    }

                                                    "floatingAction" -> {
                                                        val icon =
                                                            rItemData["icon"] as String
                                                        ButtonFloatingAction(
                                                            onclick = onclick,
                                                            icon = icon,
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                },
            )
        }

        "GridLayout" -> {
            Text("Detail Screen")
        }
    }
}