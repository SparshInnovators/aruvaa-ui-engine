package com.myproject.composeflow.Components.Layouts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.primarySurface
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.myproject.composeflow.Components.Button.ButtonElevated
import com.myproject.composeflow.Components.Button.ButtonText
import com.myproject.composeflow.Components.Image.Image
import com.myproject.composeflow.Utils.mapIcon
import com.myproject.composeflow.Utils.mapTextAlign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScaffoldLayout(
    modifier: Modifier = Modifier,
    topBarContent: Map<*, *>,
    mainContent: @Composable () -> Unit,
    bottomBarContent: @Composable () -> Unit = {},
    NavController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val suffixIcons = topBarContent["suffixIcon"] as? List<*>
    val hasMoreIcon = suffixIcons?.contains("menu") == true

    if (hasMoreIcon) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent()
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier,
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
                                        align = topBarContent["textAlign"] as? String ?: ""
                                    )
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        backgroundColor = if (topBarContent["bgColor"] != "" && topBarContent["bgColor"] != null) Color(
                            topBarContent["bgColor"].toString().toColorInt()
                        ) else MaterialTheme.colors.primarySurface,
                        navigationIcon = {
                            val suffixIcons =
                                topBarContent["suffixIcon"] as? List<*> ?: emptyList<String>()
                            suffixIcons.forEach { icon ->
                                IconButton(
                                    onClick = {
                                        if (icon == "arrow_back") {
                                            NavController.popBackStack()
                                        } else if (icon == "menu") {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        mapIcon(icon = icon as String),
                                        contentDescription = "",
                                        tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                                    )
                                }
                            }
                        },
                        actions = {
                            val button =
                                topBarContent["prefixButton"] as? List<*> ?: emptyList<String>()
                            button.forEach { btn ->
                                val type = btn as Map<*, *>
                                val key = type.entries.first().key as String
                                val value = type.entries.first().value as String
                                when (key) {
                                    "icon" -> {
                                        IconButton(onClick = {}) {
                                            Icon(
                                                mapIcon(icon = value),
                                                contentDescription = "",
                                                tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                                            )
                                        }
                                    }

                                    "elevated" -> {
                                        ButtonElevated(
                                            wrapContent = true,
                                            text = value,
                                            textColor = "#fcfdff",
                                            fontWeight = FontWeight.Normal,
                                            onclick = {},
                                        )
                                    }

                                    "text" -> {
                                        ButtonText(
                                            text = value,
                                            fontWeight = FontWeight.Normal,
                                            onclick = {}
                                        )
                                    }
                                }
                            }
                        }
                    )
                },
                content = { innerPadding ->
                    Box(
                        modifier = modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        mainContent()
                    }
                },
                bottomBar = {
                    bottomBarContent()
                }
            )
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier,
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
                                    align = topBarContent["textAlign"] as? String ?: ""
                                )
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    backgroundColor = if (topBarContent["bgColor"] != "" && topBarContent["bgColor"] != null) Color(
                        topBarContent["bgColor"].toString().toColorInt()
                    ) else MaterialTheme.colors.primarySurface,
                    navigationIcon = {
                        val suffixIcons =
                            topBarContent["suffixIcon"] as? List<*> ?: emptyList<String>()
                        suffixIcons.forEach { icon ->
                            IconButton(
                                onClick = {
                                    if (icon == "arrow_back") {
                                        NavController.popBackStack()
                                    }
                                }
                            ) {
                                Icon(
                                    mapIcon(icon = icon as String),
                                    contentDescription = "",
                                    tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                                )
                            }
                        }
                    },
                    actions = {
                        val button =
                            topBarContent["prefixButton"] as? List<*> ?: emptyList<String>()
                        button.forEach { btn ->
                            val type = btn as Map<*, *>
                            val key = type.entries.first().key as String
                            val value = type.entries.first().value as String
                            when (key) {
                                "icon" -> {
                                    IconButton(onClick = {}) {
                                        Icon(
                                            mapIcon(icon = value),
                                            contentDescription = "",
                                            tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                                        )
                                    }
                                }

                                "elevated" -> {
                                    ButtonElevated(
                                        wrapContent = true,
                                        text = value,
                                        textColor = "#fcfdff",
                                        fontWeight = FontWeight.Normal,
                                        onclick = {},
                                    )
                                }

                                "text" -> {
                                    ButtonText(
                                        text = value,
                                        fontWeight = FontWeight.Normal,
                                        onclick = {}
                                    )
                                }
                            }
                        }
                    }
                )
            },
            content = { innerPadding ->
                Box(
                    modifier = modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    mainContent()
                }
            },
            bottomBar = {
                bottomBarContent()
            }
        )
    }
}

@Composable
fun DrawerContent(modifier: Modifier = Modifier) {
    ModalDrawerSheet(
        modifier = Modifier.wrapContentWidth(),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = "https://media.istockphoto.com/id/1185382671/vector/abstract-blurred-colorful-background.jpg?s=612x612&w=0&k=20&c=3YwJa7lCw-cQ-hviINULUokL9lYU4RuGjMP_E_0N8E4=",
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(70.dp)
                )
                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Text("UserName", style = TextStyle(fontSize = 25.sp))
                    Text(
                        "Email Address",
                        style = TextStyle(fontSize = 21.sp),
                        color = Color.LightGray
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 15.dp),
                color = Color.Gray
            )
            val items = listOf(
                mapOf("screenName" to "Home", "Icon" to "home"),
                mapOf("screenName" to "Profile", "Icon" to "person"),
                mapOf("screenName" to "Settings", "Icon" to "settings"),
                mapOf("screenName" to "Logout", "Icon" to "logout"),
            )
            items.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = mapIcon(item["Icon"].toString()),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .padding(start = 12.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = item["screenName"].toString(),
                        fontSize = 25.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    )
                }

            }
        }
    )
}

