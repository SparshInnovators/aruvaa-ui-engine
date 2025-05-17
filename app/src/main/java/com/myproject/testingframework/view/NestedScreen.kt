package com.myproject.testingframework.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.myproject.composeflow.Actions.Button_Actions.ActionType_Alert
import com.myproject.composeflow.Actions.Button_Actions.ActionType_SimpleDialog
import com.myproject.composeflow.ComponentRender.Render_Button
import com.myproject.composeflow.ComponentRender.Render_Image
import com.myproject.composeflow.ComponentRender.Render_Text
import com.myproject.composeflow.Components.Container.BoxContainer
import com.myproject.composeflow.Components.Container.VerticalContainer
import com.myproject.composeflow.Components.Layouts.ItemLayout
import com.myproject.composeflow.Components.Layouts.SingleColumnLayout
import com.myproject.composeflow.Components.Layouts.SingleRowLayout
import com.myproject.composeflow.Utils.JsonParsing.ParseJsonString
import com.myproject.composeflow.Utils.Padding.paddingValues
import com.myproject.composeflow.Utils.searchData
import com.myproject.testingframework.model.DataManager.DataManager
import com.myproject.testingframework.viewmodel.MyViewModel
import kotlin.collections.get

@Composable
fun NestedScreen(
    modifier: Modifier = Modifier,
    id: String,
    myVm: MyViewModel,
    navController: NavController
) {
    //context
    val context = LocalContext.current

    //fetch screen json data
    LaunchedEffect(id) {
        myVm.fetchSecondaryJsonData(screenId = id)
    }
    val data = myVm.secondaryJsonData

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


    //data
    val listdata = listOf(
        mapOf<String, List<String>>(
            "activity" to listOf("Cases", "Calls", "Meetings", "Tasks", "Notes")
        ),
        mapOf<String, List<String>>(
            "related" to listOf(
                "Product Users",
                "Contacts",
                "Leads",
                "GSTR",
                "Quote",
                "Opportunities",
                "Receipt"
            )
        )
    )


    when (layoutType) {
        "SingleColumnLayout" -> {
            SingleColumnLayout(modifier = Modifier.then(screenPadding)) {

                layoutChildren.forEach { item1 ->
                    val item1Data = item1 as Map<*, *>
                    val item1Type = item1Data["type"] as String
                    when (item1Type) {
                        "BoxContainer" -> {
                            val boxPadding = paddingValues(item1Data["padding"])
                            val wrapHeight =
                                item1Data["wrapContentHeight"] == true
                            val bgColor =
                                (item1Data["backgroundColor"] as? String
                                    ?: "#f5f5f5").toColorInt()
                            BoxContainer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(bgColor))
                                    .then(boxPadding),
                                wrapContentHeight = wrapHeight,
                            ) {
                                val ContainerItem =
                                    item1Data["children"] as List<*>
                                ContainerItem.forEach {
                                    val eachItem = it as Map<*, *>
                                    val eachItemType =
                                        eachItem["type"] as String

                                    when (eachItemType) {
                                        "VerticalContainer" -> {
                                            val containerItems =
                                                eachItem["children"] as List<*>

                                            VerticalContainer(
                                                modifier = Modifier,
                                                spacedBy = 10,
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
                                                                    NavController = navController,
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
                                                                    spacedBy = 5,
                                                                    content = {
                                                                        rowItems.forEach {
                                                                            val rowItemData =
                                                                                it as Map<*, *>
                                                                            val rowItemType =
                                                                                rowItemData["type"] as String
                                                                            when (rowItemType) {
                                                                                "text" -> {
                                                                                    Render_Text(
                                                                                        modifier = Modifier,
                                                                                        dataMap = rowItemData,
                                                                                        searchData = (myVm.organizationList.value[myVm.storedIndex][rowItemData["\$text"]]
                                                                                            ?: "Incorrect Variable name")
                                                                                    )
                                                                                }

                                                                                "spacer" -> {
                                                                                    Spacer(
                                                                                        Modifier.weight(
                                                                                            1f
                                                                                        )
                                                                                    )
                                                                                }

                                                                                "button" -> {
                                                                                    Render_Button(
                                                                                        modifier = Modifier,
                                                                                        dataMap = rowItemData,
                                                                                        context = context,
                                                                                        NavController = navController,
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
                                                                            }
                                                                        }
                                                                    }
                                                                )

                                                            }

                                                            "divider" -> {
                                                                HorizontalDivider()
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
            val items = layoutChildren.map { it as Map<*, *> }

            val headerComponents = items.takeWhile { it["type"] != "list" }
            val listItems = items.filter { it["type"] == "list" }[0]
            val footerComponents =
                items.dropWhile { it["type"] != "list" }.drop(1)

            val itemCount =
                (listItems["itemCount"] as? Number)?.toFloat()?.toInt() ?: 0
            val rowPadding = paddingValues(path = listItems["padding"])

            ItemLayout(
                modifier = Modifier.then(screenPadding),
                count = itemCount,
                headerContent = {
                    headerComponents.forEach { hItem ->
                        val hItemType = hItem["type"] as String
                        when (hItemType) {
                            "text" -> {
                                Render_Text(
                                    modifier = Modifier,
                                    dataMap = hItem,
                                    searchData = searchData(
                                        key = (hItem["\$text"] as? String
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
                                        key = (fItem["\$text"] as? String
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
                                    isCentered = true,
                                    modifier = Modifier
                                        .then(rowPadding),
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
                                                        NavController = navController,
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

                                                "Box" -> {
                                                    val boxPadding =
                                                        paddingValues(path = rItemData["padding"])
                                                    val boxSize =
                                                        (rItemData["size"] ?: 0).toString()
                                                            .toFloat().toInt()
                                                    val boxColor =
                                                        if (rItemData["bgColor"] != null) Color((rItemData["bgColor"] as String).toColorInt()) else Color.Transparent
                                                    val shape = (rItemData["shape"] as? String)
                                                    Box(
                                                        modifier = Modifier
                                                            .then(boxPadding)
                                                            .clip(shape = CircleShape)
                                                            .size(boxSize.dp)
                                                            .background(boxColor)
                                                    )
                                                }

                                                "text" -> {
                                                    Render_Text(
                                                        modifier = Modifier,
                                                        dataMap = rItemData,
                                                        searchData = listdata
                                                            .firstOrNull { it.containsKey(rItemData["\$text"]) }
                                                            ?.get(rItemData["\$text"])
                                                            ?.getOrNull(index) ?: "N/A"
                                                    )
                                                }

                                                "spacer" -> {
                                                    Spacer(
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                )
                            }

                        }
                    }
                },
            )
        }
    }

}