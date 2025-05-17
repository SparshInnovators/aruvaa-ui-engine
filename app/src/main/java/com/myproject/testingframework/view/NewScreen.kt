package com.myproject.testingframework.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.CallLog
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.myproject.composeflow.Actions.Button_Actions.ActionType_Alert
import com.myproject.composeflow.Actions.Button_Actions.ActionType_SimpleDialog
import com.myproject.composeflow.Actions.Button_Actions.ActionType_SnackBar
import com.myproject.composeflow.Actions.Button_Actions.ActionType_Url
import com.myproject.composeflow.ComponentRender.Render_Button
import com.myproject.composeflow.ComponentRender.Render_DropDown
import com.myproject.composeflow.ComponentRender.Render_Image
import com.myproject.composeflow.ComponentRender.Render_SingleLineInputText
import com.myproject.composeflow.ComponentRender.Render_Text
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
import com.myproject.composeflow.Components.Tabs.HorizontalTabs
import com.myproject.composeflow.Components.Text.SingleLineInputText
import com.myproject.composeflow.Utils.JsonParsing.ParseJsonString
import com.myproject.composeflow.Utils.Padding.paddingValues
import com.myproject.composeflow.Utils.mapFontWeight
import com.myproject.composeflow.Utils.mapKeyBoardType
import com.myproject.composeflow.Utils.searchData
import com.myproject.testingframework.Navigation
import com.myproject.testingframework.model.DataManager.DataManager
import com.myproject.testingframework.viewmodel.BaseViewModel
import com.myproject.testingframework.viewmodel.MyViewModel
import kotlinx.coroutines.launch
import kotlin.collections.get
import kotlin.math.log
import kotlin.text.get

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("PermissionLaunchedDuringComposition", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DynamicLayoutScreen(
    modifier: Modifier = Modifier,
    id: String,
    NavController: NavController,
    myVm: MyViewModel,
) {
    //context
    val context = LocalContext.current

    //viewmodel
//    val myVm: MyViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
    LaunchedEffect(id) {
        myVm.fetchJsonData(screenId = id)
    }

    //Screen Data
    val data = myVm.fetchedJsonData.observeAsState()
    val uiData = data.value?.jsonData?.takeIf { it.isNotEmpty() }?.let { json ->
        ParseJsonString(json)
    } ?: emptyMap<Any, Any>()

    val name = uiData["ScreenName"] as? String ?: "Unknown Screen"
    val screenId = uiData["ScreenId"] as? String ?: "Unknown ID"
    val layoutData = uiData["layout"] as? Map<*, *> ?: emptyMap<Any, Any>()

    val layoutType = layoutData["type"] as? String ?: "Unknown Type"


    //Dialog
    var showAlertDialog by remember { mutableStateOf(false) }
    var showSimpleDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }

    //screen padding
    val screenPadding = paddingValues(path = layoutData["padding"])

    //layoutChildren
    val layoutChildren = (layoutData["children"] as? List<*>) ?: emptyList<Any>()

    //coroutine Scope and drawerState
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    //expandable item
    var expanded = remember { mutableStateMapOf<Int, Boolean>() }


    when (layoutType) {
        "SingleColumnLayout" -> {

            SingleColumnLayout(modifier = Modifier.then(screenPadding)) {

                layoutChildren.forEach { item1 ->
                    val item1Data = item1 as Map<*, *>
                    val item1Type = item1Data["type"] as String
                    when (item1Type) {
                        "image" -> {
                            Render_Image(
                                dataMap = item1Data,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        "text" -> {
                            Render_Text(
                                modifier = Modifier,
                                dataMap = item1Data,
                                searchData = searchData(
                                    key = (item1Data["\$text"] as? String ?: "")
                                )
                            )
                        }

                        "SingleLineInputText" -> {
                            val inputId = item1Data["id"] as String
                            val userInputFields by myVm.formData.collectAsState()
                            Render_SingleLineInputText(
                                modifier = Modifier,
                                dataMap = item1Data,
                                value = userInputFields[screenId]?.get(inputId) ?: "",
                                onValueChange = { newValue ->
                                    myVm.updateField(
                                        screenId = screenId,
                                        fieldId = inputId,
                                        value = newValue
                                    )
                                }
                            )
                        }

                        "button" -> {
                            Render_Button(
                                modifier = Modifier,
                                dataMap = item1Data,
                                context = context,
                                NavController = NavController,
                                navigationFunc = {
                                    myVm.saveFormData(screenId)
                                },
                                alertFunc = { title, message ->
                                    showAlertDialog = true
                                    dialogTitle = title
                                    dialogMessage = message
                                },
                            )
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
                        }
                    }
                }
            }
        }

        "ScaffoldLayout" -> {
            val topBar = layoutData["topBar"] as Map<*, *>
            val topBarType = topBar["type"] as? String ?: ""
            var dynamicTopBarContent: (@Composable () -> Unit)? = null
            var topBarContent: Map<*, *>? = null
            val bottomBarContent =
                layoutData["bottomBar"] as? Map<*, *> ?: emptyMap<String, String>()

            if (topBarType == "DynamicTopBar") {
                val dynamicTopBar = topBar["content"] as Map<*, *>
                dynamicTopBarContent = {
                    val dynamicTopBarType = dynamicTopBar["type"] as String
                    when (dynamicTopBarType) {
                        "VerticalContainer" -> {
                            val dynamicTopBarItems = dynamicTopBar["children"] as List<*>
                            val wrapHeight = dynamicTopBar["wrapContentHeight"] == true
                            VerticalContainer(
                                wrapContentHeight = wrapHeight,
                                contents = {
                                    dynamicTopBarItems.forEach {
                                        val itemData = it as Map<*, *>
                                        val itemType = itemData["type"] as String
                                        when (itemType) {
                                            "BoxContainer" -> {
                                                val boxPadding = paddingValues(itemData["padding"])
                                                val wrapHeight =
                                                    itemData["wrapContentHeight"] == true
                                                val bgColor =
                                                    (itemData["backgroundColor"] as? String
                                                        ?: "#f5f5f5").toColorInt()
                                                BoxContainer(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(Color(bgColor))
                                                        .then(boxPadding),
                                                    wrapContentHeight = wrapHeight,
                                                ) {
                                                    val ContainerItem =
                                                        itemData["children"] as List<*>
                                                    ContainerItem.forEach {
                                                        val eachItem = it as Map<*, *>
                                                        val eachItemType =
                                                            eachItem["type"] as String

                                                        when (eachItemType) {
                                                            "VerticalContainer" -> {
                                                                val containerItems =
                                                                    eachItem["children"] as List<*>
                                                                val containerPadding =
                                                                    paddingValues(path = eachItem["padding"])

                                                                VerticalContainer(
                                                                    modifier = Modifier.then(
                                                                        containerPadding
                                                                    ),
                                                                    wrapContentHeight = true,
                                                                    contents = {
                                                                        containerItems.forEach {
                                                                            val containerItemData =
                                                                                it as Map<*, *>
                                                                            val containerItemType =
                                                                                containerItemData["type"] as String
                                                                            when (containerItemType) {
                                                                                "button" -> {
                                                                                    Render_Button(
                                                                                        modifier = Modifier.padding(
                                                                                            start = 10.dp
                                                                                        ),
                                                                                        dataMap = containerItemData,
                                                                                        context = context,
                                                                                        NavController = NavController,
                                                                                        navigationFunc = {
                                                                                            myVm.saveFormData(
                                                                                                screenId
                                                                                            )
                                                                                        },
                                                                                        alertFunc = { title, message ->
                                                                                            showAlertDialog =
                                                                                                true
                                                                                            dialogTitle =
                                                                                                title
                                                                                            dialogMessage =
                                                                                                message
                                                                                        },
                                                                                    )
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
                                                                                }

                                                                                "SingleRowLayout" -> {
                                                                                    val rowPadding =
                                                                                        paddingValues(
                                                                                            containerItemData["padding"]
                                                                                        )
                                                                                    val rowItems =
                                                                                        containerItemData["children"] as List<*>
                                                                                    SingleRowLayout(
                                                                                        modifier = Modifier.then(
                                                                                            rowPadding
                                                                                        ),
                                                                                        content = {
                                                                                            rowItems.forEach {
                                                                                                val rowItemData =
                                                                                                    it as Map<*, *>
                                                                                                val rowItemType =
                                                                                                    rowItemData["type"] as String
                                                                                                when (rowItemType) {
                                                                                                    "image" -> {
                                                                                                        Render_Image(
                                                                                                            modifier = Modifier,
                                                                                                            dataMap = rowItemData
                                                                                                        )
                                                                                                    }

                                                                                                    "SingleColumnLayout" -> {
                                                                                                        val columnItems =
                                                                                                            rowItemData["children"] as List<*>
                                                                                                        SingleColumnLayout(
                                                                                                            modifier = Modifier.padding(
                                                                                                                start = 10.dp
                                                                                                            )
                                                                                                        ) {
                                                                                                            columnItems.forEach {
                                                                                                                val columnItemData =
                                                                                                                    it as Map<*, *>
                                                                                                                val columnItemType =
                                                                                                                    columnItemData["type"] as String
                                                                                                                when (columnItemType) {
                                                                                                                    "text" -> {
                                                                                                                        Render_Text(
                                                                                                                            modifier = Modifier,
                                                                                                                            dataMap = columnItemData,
                                                                                                                            searchData = if ((myVm.organizationList.value[myVm.storedIndex][columnItemData["\$text"]]
                                                                                                                                    ?: "Incorrect Variable name") == ""
                                                                                                                            ) {
                                                                                                                                "N/A"
                                                                                                                            } else {
                                                                                                                                (myVm.organizationList.value[myVm.storedIndex][columnItemData["\$text"]]
                                                                                                                                    ?: "Incorrect Variable name")
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
                                                                            }
                                                                        }
                                                                    }
                                                                )
                                                            }

                                                            "SingleRowLayout" -> {
                                                                val rowPadding =
                                                                    paddingValues(eachItem["padding"])
                                                                val rowItems =
                                                                    eachItem["children"] as List<*>
                                                                SingleRowLayout(
                                                                    modifier = Modifier.then(
                                                                        rowPadding
                                                                    ),
                                                                    spacedBy = 7,
                                                                    isCentered = true,
                                                                    content = {
                                                                        Spacer(Modifier.weight(1f))
                                                                        rowItems.forEach {
                                                                            val rowItemData =
                                                                                it as Map<*, *>
                                                                            val rowItemType =
                                                                                rowItemData["type"] as String
                                                                            when (rowItemType) {
                                                                                "button" -> {
                                                                                    Render_Button(
                                                                                        modifier = Modifier
                                                                                            .offset(
                                                                                                x = 0.dp,
                                                                                                y = (-25).dp
                                                                                            )
                                                                                            .clip(
                                                                                                shape = CircleShape
                                                                                            )
                                                                                            .background(
                                                                                                Color.White
                                                                                            )
                                                                                            .padding(
                                                                                                10.dp
                                                                                            ),
                                                                                        dataMap = rowItemData,
                                                                                        context = context,
                                                                                        NavController = NavController,
                                                                                        navigationFunc = {},
                                                                                        alertFunc = { title, message ->
                                                                                            showAlertDialog =
                                                                                                true
                                                                                            dialogTitle =
                                                                                                title
                                                                                            dialogMessage =
                                                                                                message
                                                                                        },
                                                                                    )
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
                    }
                }
            } else {
                topBarContent = topBar
            }

            ScaffoldLayout(
                NavController = NavController,
                modifier = Modifier.then(screenPadding),
                drawerState = drawerState,
                topBarContent = topBarContent,
                dynamicTopBarContent = dynamicTopBarContent,
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
                                                        val item3Type =
                                                            item3Data["type"] as String
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
                                                                                    Render_Text(
                                                                                        modifier = Modifier,
                                                                                        dataMap = vItemData,
                                                                                        searchData = searchData(
                                                                                            key = (item1Data["\$text"] as? String
                                                                                                ?: "")
                                                                                        )
                                                                                    )
                                                                                }

                                                                                "SingleLineInputText" -> {
                                                                                    val inputId =
                                                                                        vItemData["id"] as String
                                                                                    val userInputFields by myVm.formData.collectAsState()
                                                                                    Render_SingleLineInputText(
                                                                                        modifier = Modifier,
                                                                                        dataMap = vItemData,
                                                                                        value = userInputFields[screenId]?.get(
                                                                                            inputId
                                                                                        ) ?: "",
                                                                                        onValueChange = { newValue ->
                                                                                            myVm.updateField(
                                                                                                screenId = screenId,
                                                                                                fieldId = inputId,
                                                                                                value = newValue
                                                                                            )
                                                                                        }
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
                                                                                                        val inputId =
                                                                                                            rItemData["id"] as String
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
                                                                                                        val userInputFields by myVm.formData.collectAsState()

                                                                                                        SingleLineInputText(
                                                                                                            keyboardType = keyBoardType,
                                                                                                            fontWeight = fontWeight,
                                                                                                            hintText = hintText,
                                                                                                            value = userInputFields[screenId]?.get(
                                                                                                                inputId
                                                                                                            )
                                                                                                                ?: "",
                                                                                                            onValueChange = {
                                                                                                                myVm.updateField(
                                                                                                                    screenId = screenId,
                                                                                                                    fieldId = inputId,
                                                                                                                    value = it
                                                                                                                )
                                                                                                            },
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
                                                                                    vItemData
                                                                                    Render_DropDown(
                                                                                        modifier = Modifier,
                                                                                        dataMap = vItemData
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

                                            "text" -> {
                                                Text("Sample Screen", fontSize = 45.sp)
                                            }

                                            "abcd" -> {
                                                val address =
                                                    remember { mutableStateOf("Fetching Address...") }
                                                val fusedLocationClient = remember {
                                                    LocationServices.getFusedLocationProviderClient(
                                                        context
                                                    )
                                                }

                                                val locationPermissionState =
                                                    rememberMultiplePermissionsState(
                                                        listOf(
                                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                                            Manifest.permission.ACCESS_COARSE_LOCATION
                                                        )
                                                    )

                                                LaunchedEffect(locationPermissionState) {
                                                    locationPermissionState.launchMultiplePermissionRequest()
                                                }

                                                if (locationPermissionState.allPermissionsGranted) {
                                                    scope.launch {
                                                        if (ContextCompat.checkSelfPermission(
                                                                context,
                                                                Manifest.permission.ACCESS_COARSE_LOCATION
                                                            ) == PackageManager.PERMISSION_GRANTED
                                                        ) {
                                                            val location =
                                                                fusedLocationClient.getCurrentLocation(
                                                                    Priority.PRIORITY_HIGH_ACCURACY,
                                                                    null
                                                                )
                                                            location.addOnSuccessListener {
                                                                address.value =
                                                                    "Longitude = ${it.latitude} \n Latitude = ${it.longitude}"
                                                            }

                                                        }
                                                    }
                                                }

                                                Text(address.value)
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
                                    count = myVm.organizationList.value.size,
                                    headerContent = {
                                        headerComponents.forEach { hItem ->
                                            val hItemType = hItem["type"] as String
                                            when (hItemType) {
                                                "text" -> {
                                                    Render_Text(
                                                        modifier = Modifier,
                                                        dataMap = hItem,
                                                        searchData = searchData(
                                                            key = (item1Data["\$text"] as? String
                                                                ?: "")
                                                        )
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
                                                    Render_Text(
                                                        modifier = Modifier,
                                                        dataMap = fItem,
                                                        searchData = searchData(
                                                            key = (item1Data["\$text"] as? String
                                                                ?: "")
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    },
                                    content = { index ->
                                        val items = listItems["children"] as List<*>
                                        items.forEach {
                                            val itemData = it as Map<*, *>
                                            val itemType = itemData["type"] as String

                                            when (itemType) {
                                                "SingleRowLayout" -> {
                                                    val rowItems =
                                                        itemData["children"] as List<*>
                                                    SingleRowLayout(
                                                        modifier = Modifier
                                                            .then(rowPadding)
                                                            .clickable {
                                                                expanded[index] =
                                                                    !(expanded[index] ?: false)
                                                            },
                                                        content = {
                                                            rowItems.forEach { rItem ->
                                                                val rItemData =
                                                                    rItem as Map<*, *>
                                                                val rItemType =
                                                                    rItemData["type"] as String
                                                                when (rItemType) {

                                                                    "image" -> {
                                                                        Render_Image(
                                                                            dataMap = rItemData,
                                                                            modifier = Modifier
                                                                        )
                                                                    }

                                                                    "VerticalContainer" -> {
                                                                        val containerPadding =
                                                                            paddingValues(path = rItemData["padding"])
                                                                        val contItem =
                                                                            rItemData["children"] as List<*>

                                                                        VerticalContainer(
                                                                            modifier = Modifier
                                                                                .then(
                                                                                    containerPadding
                                                                                )
                                                                                .weight(1f),
                                                                            contents = {
                                                                                contItem.forEach { CItem ->
                                                                                    val CItemData =
                                                                                        CItem as Map<*, *>
                                                                                    val CItemType =
                                                                                        CItemData["type"] as String
                                                                                    when (CItemType) {
                                                                                        "text" -> {
                                                                                            Render_Text(
                                                                                                modifier = Modifier,
                                                                                                dataMap = CItemData,
                                                                                                searchData = (myVm.organizationList.value[index][CItemData["\$text"]]
                                                                                                    ?: "Incorrect Variable name")
                                                                                            )
                                                                                        }
                                                                                    }
                                                                                }
                                                                            },
                                                                        )
                                                                    }

                                                                    "button" -> {
                                                                        Render_Button(
                                                                            modifier = Modifier,
                                                                            dataMap = rItemData,
                                                                            context = context,
                                                                            NavController = NavController,
                                                                            navigationFunc = {
                                                                                myVm.storedIndex =
                                                                                    index
                                                                            },
                                                                            alertFunc = { title, message ->
                                                                                showAlertDialog =
                                                                                    true
                                                                                dialogTitle =
                                                                                    title
                                                                                dialogMessage =
                                                                                    message
                                                                            },
                                                                        )
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
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    )
                                                }

                                                "expandable" -> {

                                                    val expandedItem =
                                                        itemData["children"] as List<*>

                                                    expandedItem.forEach {
                                                        AnimatedVisibility(
                                                            visible = expanded[index] == true,
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
                                                            val expandedItemData =
                                                                it as Map<*, *>
                                                            val expandedItemType =
                                                                expandedItemData["type"] as String

                                                            when (expandedItemType) {
                                                                "verticalContainer" -> {
                                                                    val ContainerItems =
                                                                        expandedItemData["children"] as List<*>
                                                                    VerticalContainer(
                                                                        modifier = Modifier,
                                                                        contents = {
                                                                            ContainerItems.forEach {
                                                                                val containerItemData =
                                                                                    it as Map<*, *>
                                                                                val containerItemType =
                                                                                    containerItemData["type"] as String
                                                                                when (containerItemType) {
                                                                                    "SingleRowLayout" -> {
                                                                                        val rowItems =
                                                                                            containerItemData["children"] as List<*>
                                                                                        SingleRowLayout(
                                                                                            modifier = Modifier.fillMaxWidth(),
                                                                                            isCentered = true,
                                                                                            spacedBy = 10,
                                                                                            content = {
                                                                                                rowItems.forEach {
                                                                                                    val rowItemData =
                                                                                                        it as Map<*, *>
                                                                                                    val rowItemType =
                                                                                                        rowItemData["type"] as String
                                                                                                    when (rowItemType) {
                                                                                                        "button" -> {
                                                                                                            Render_Button(
                                                                                                                modifier = Modifier,
                                                                                                                dataMap = rowItemData,
                                                                                                                context = context,
                                                                                                                NavController = NavController,
                                                                                                                navigationFunc = {},
                                                                                                                alertFunc = { title, message ->
                                                                                                                    showAlertDialog =
                                                                                                                        true
                                                                                                                    dialogTitle =
                                                                                                                        title
                                                                                                                    dialogMessage =
                                                                                                                        message
                                                                                                                },
                                                                                                            )
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
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        )
                                                                                    }

                                                                                    "divider" -> {
                                                                                        HorizontalDivider(
                                                                                            modifier = Modifier.padding(
                                                                                                bottom = 10.dp
                                                                                            )
                                                                                        )
                                                                                    }

                                                                                    "abcd" -> {
                                                                                        Column(
                                                                                            modifier = Modifier.clickable {
                                                                                                myVm.storedIndex =
                                                                                                    index
                                                                                                NavController.navigate(
                                                                                                    "aruvaa/04_detail"
                                                                                                )
                                                                                            }) {
                                                                                            myVm.organizationList.value[index].forEach {
                                                                                                Text(
                                                                                                    "${it.key.replaceFirstChar { it.uppercase() }} : ${it.value}",
                                                                                                    modifier = Modifier
                                                                                                        .padding(
                                                                                                            horizontal = 13.dp
                                                                                                        )
                                                                                                        .fillMaxWidth(),
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
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    },
                                )
                            }

                            "horizontalTabs" -> {
                                val newNavController = rememberNavController()
                                var selectedIndex by remember { mutableIntStateOf(0) }

                                val tabsContent =
                                    item1Data["tabs"] as? List<*> ?: emptyList<String>()
                                val tabs: List<Pair<String, String>> = tabsContent.mapNotNull {
                                    val map = it as? Map<*, *>
                                    val title = map?.get("title") as? String
                                    val onclick = map?.get("onclick") as? String
                                    if (title != null && onclick != null) Pair(
                                        title,
                                        onclick
                                    ) else null
                                }

                                LaunchedEffect(Unit) {
                                    if (tabs.isNotEmpty()) {
                                        newNavController.navigate(tabs[0].second) {
                                            popUpTo(0) { inclusive = true }
                                            launchSingleTop = true
                                        }
                                    }
                                }

                                Column {
                                    HorizontalTabs(
                                        modifier = Modifier,
                                        selectedIndex = selectedIndex,
                                        tabsTitle = tabs.map { it.first },
                                        onTabSelected = { index ->
                                            val route = tabs[index].second
                                            newNavController.navigate(route) {
                                                popUpTo(newNavController.graph.startDestinationId) {
                                                    inclusive = true
                                                }
                                                launchSingleTop = true
                                            }
                                            selectedIndex = index
                                        }
                                    )
                                    NavHost(
                                        modifier = modifier,
                                        navController = newNavController,
                                        startDestination = "aruvaa/tabs/0/{screenName}",
                                        builder = {
                                            composable("aruvaa/tabs/{index}/{screenName}") {
                                                val index =
                                                    it.arguments?.getString("index")?.toInt() ?: 0
                                                val screenName =
                                                    it.arguments?.getString("screenName")
                                                        ?.toString() ?: ""
                                                NestedScreen(
                                                    modifier = Modifier,
                                                    id = screenName,
                                                    myVm = myVm,
                                                    navController = newNavController
                                                )
                                            }
                                        }
                                    )
                                }
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
                                Render_Text(
                                    modifier = Modifier,
                                    dataMap = hItem,
                                    searchData = searchData(
                                        key = (hItem["\$text"] as? String ?: "")
                                    )
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
                                Render_Text(
                                    modifier = Modifier,
                                    dataMap = fItem,
                                    searchData = searchData(
                                        key = (fItem["\$text"] as? String ?: "")
                                    )
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
                                                Render_Image(
                                                    modifier = Modifier,
                                                    dataMap = rItemData
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
                                                                    Render_Text(
                                                                        modifier = Modifier,
                                                                        dataMap = CItemData,
                                                                        searchData = ""
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    },
                                                )
                                            }

                                            "button" -> {
                                                Render_Button(
                                                    modifier = Modifier,
                                                    dataMap = rItemData,
                                                    context = context,
                                                    NavController = NavController,
                                                    navigationFunc = {
                                                        myVm.saveFormData(screenId)
                                                    },
                                                    alertFunc = { title, message ->
                                                        showAlertDialog = true
                                                        dialogTitle = title
                                                        dialogMessage = message
                                                    },
                                                )
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
            val callLogs = remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
            val call_log_permission = rememberPermissionState(Manifest.permission.READ_CALL_LOG)
            val notification_permission =
                rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)

            LaunchedEffect(Unit) {
                call_log_permission.launchPermissionRequest()
                notification_permission.launchPermissionRequest()
                if (call_log_permission.status.isGranted) {
                    callLogs.value = getcallLogs(context = context)
                }
            }

            if (callLogs.value.isNotEmpty()) {
                SingleColumnLayout {
                    repeat(callLogs.value.size) {
                        Text(callLogs.value[it].toString(), fontSize = 18.sp)
                    }
                    ElevatedButton(onClick = {
                        myVm.startForegroundService()
                    }) { Text(("Notify")) }

                }
            } else {
                Text("No Call Logs")
            }
        }
    }
}

fun getcallLogs(context: Context): List<Map<String, Any>> {
    val callLogs = mutableListOf<Map<String, Any>>()
    val projection = arrayOf(
        CallLog.Calls.NUMBER,
        CallLog.Calls.DATE,
        CallLog.Calls.DURATION,
        CallLog.Calls.TYPE,
    )

    context.contentResolver.query(
        CallLog.Calls.CONTENT_URI,
        projection,
        null,
        null,
        CallLog.Calls.DEFAULT_SORT_ORDER
    )?.use { cursor ->
        while (cursor.moveToNext()) {
            callLogs.add(
                mapOf(
                    "number" to cursor.getString(0),
                    "date" to cursor.getString(1),
                    "duration" to cursor.getLong(2),
                    "type" to cursor.getInt(3)
                )
            )
        }
    }
    return callLogs
}
