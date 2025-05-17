package com.myproject.composeflow.Components.Layouts

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DriveFileMoveRtl
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Window
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.myproject.composeflow.Components.Button.ButtonElevated
import com.myproject.composeflow.Components.Button.ButtonText
import com.myproject.composeflow.Components.Image.Image
import com.myproject.composeflow.Components.Text.TitleText
import com.myproject.composeflow.Utils.mapIcon
import com.myproject.composeflow.Utils.mapTextAlign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ScaffoldLayout(
    modifier: Modifier = Modifier,
    topBarContent: Map<*, *>? = null,
    dynamicTopBarContent: (@Composable () -> Unit)? = null,
    mainContent: @Composable () -> Unit,
    bottomBarContent: @Composable () -> Unit = {},
    NavController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope,
) {


    val topBar: @Composable () -> Unit = {
        when {
            topBarContent != null -> {
                val suffixIcons = topBarContent["suffixIcon"] as? List<*>
                val hasMoreIcon = suffixIcons?.contains("menu") == true
                TopAppBar(
                    modifier = Modifier.then(
                        if (topBarContent["height"] != null) Modifier.height(
                            topBarContent["height"].toString().toInt().dp
                        ) else Modifier
                    ),
                    elevation = 0.dp,
                    title = {
                        Text(
                            text = topBarContent["title"]?.toString() ?: "No AppBar",
                            style = TextStyle(
                                fontSize = (topBarContent["font_size"]?.toString()?.toInt()
                                    ?: 23).sp,
                                color = Color(
                                    topBarContent["textColor"]?.toString()?.toColorInt()
                                        ?: "#0c0d0c".toColorInt()
                                ),
                                textAlign = mapTextAlign(
                                    topBarContent["textAlign"] as? String ?: ""
                                )
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    backgroundColor = if (topBarContent["bgColor"] != "" && topBarContent["bgColor"] != null) Color(
                        topBarContent["bgColor"].toString().toColorInt()
                    ) else MaterialTheme.colors.surface,
                    navigationIcon = {
                        val suffixIcons =
                            topBarContent["suffixIcon"] as? List<*> ?: emptyList<String>()
                        suffixIcons.forEach { icon ->
                            IconButton(onClick = {
                                when (icon) {
                                    "arrow_back" -> NavController.popBackStack()
                                    "menu" -> scope.launch { drawerState.open() }
                                }
                            }) {
                                Icon(mapIcon(icon.toString()), contentDescription = null)
                            }
                        }
                    },
                    actions = {
                        (topBarContent["prefixButton"] as? List<*>)?.forEach { btn ->
                            val type = btn as Map<*, *>
                            val key = type.keys.first() as String
                            val value = type.values.first() as String
                            when (key) {
                                "icon" -> {
                                    IconButton(onClick = {
                                        if (value == "user") {

                                        }
                                    }) {
                                        Icon(
                                            mapIcon(icon = value),
                                            contentDescription = "",
                                            tint = Color.White,
                                            modifier = Modifier
                                                .clip(shape = CircleShape)
                                                .background(Color("#1668E3".toColorInt()))
                                                .padding(3.dp)
                                        )
                                    }
                                }

                                "elevated" -> ButtonElevated(
                                    wrapContent = true,
                                    text = value,
                                    textColor = "#fcfdff",
                                    fontWeight = FontWeight.Normal,
                                    onclick = {},
                                )

                                "text" -> ButtonText(
                                    text = value,
                                    fontWeight = FontWeight.Normal,
                                    onclick = {}
                                )
                            }
                        }
                    }
                )
            }

            dynamicTopBarContent != null -> dynamicTopBarContent.invoke()
            else -> Log.d("Ankit Raj", "inside Else")
        }
    }

    val suffixIcons =
        if (topBarContent != null) topBarContent["suffixIcon"] as? List<*> else null
    val hasMoreIcon = suffixIcons?.contains("menu") == true

    if (hasMoreIcon) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerShape = RectangleShape,
                    drawerContainerColor = Color("#3438CD".toColorInt())
                ) {
                    DrawerContent(NavController, drawerState, scope)
                }
            }
        ) {
            Scaffold(
                topBar = topBar,
                bottomBar = bottomBarContent,
                content = {
                    Box(
                        modifier
                            .padding(it)
                            .fillMaxSize()
                    ) { mainContent() }
                }
            )
        }
    } else {
        Scaffold(
            topBar = topBar,
            bottomBar = bottomBarContent,
            content = {
                Box(
                    modifier
                        .padding(it)
                        .fillMaxSize()
                ) { mainContent() }
            }
        )
    }
}


@Composable
fun DrawerContent(
    NavController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {

    var selectedItemIndex by remember { mutableStateOf(0) }
    val selectedSubItems = remember { mutableStateMapOf<Int, Int>() }

    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .background(Color("#3438CD".toColorInt()))
                .padding(vertical = 15.dp),
        ) {
            Spacer(Modifier.height(30.dp))

            mainItems.forEachIndexed { index, item ->
                IconButton(
                    onClick = { selectedItemIndex = index },
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .clip(
                            RoundedCornerShape(
//                                topStart = 20.dp,
//                                bottomStart = 20.dp
                                10.dp
                            )
                        )
                        .background(if (selectedItemIndex == index) Color("#676AD9".toColorInt()) else Color.Transparent)
                        .wrapContentWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp)

                ) {
                    Icon(
                        imageVector = item.first,
                        contentDescription = null,
                        modifier = Modifier.size(78.dp),
                        tint = Color.White
                    )
                }
            }

        }
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxHeight()
                .width(1.dp)
                .background(Color.Gray)
        )
        Column(
            modifier = Modifier
                .padding(top = 15.dp)
        ) {
            TitleText(
                text = mainItems[selectedItemIndex].second,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 20.dp
                    )
            )

            Column(
                modifier = Modifier
                    .background(Color("#3438CD".toColorInt()))
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {

                mainItems[selectedItemIndex].third.forEachIndexed { subIndex, subTitle ->
                    Text(
                        text = subTitle,
                        fontSize = 18.sp,
                        color = if (selectedSubItems[selectedItemIndex] == subIndex) Color.White else Color.Black,
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth()
                            .background(if (selectedSubItems[selectedItemIndex] == subIndex) Color.Gray else Color.Transparent)
                            .clickable {
                                selectedSubItems[selectedItemIndex] = subIndex
                                scope.launch {
                                    drawerState.close()
                                }
                                NavController.navigate("aruvaa/05_sample")
                            }
                            .padding(10.dp)

                    )
                }
            }
        }
    }
}

val mainItems = listOf(
    Triple(Icons.Default.Window, "Product 1", List(3) { "Menu Option ${it + 1}" }),
    Triple(Icons.Default.Window, "Product 2", List(5) { "Menu Item ${it + 1}" }),
    Triple(Icons.Default.Window, "Product 3", List(4) { "Menu Item ${it + 1}" }),
    Triple(Icons.Default.Window, "Product 4", List(30) { "Menu Option ${it + 1}" })
)

