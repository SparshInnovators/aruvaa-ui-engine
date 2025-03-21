package com.myproject.testingframework

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Paint.Align
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.compose.AsyncImage
import com.myproject.composeflow.Components.Container.BoxContainer
import com.myproject.composeflow.Components.Container.VerticalContainer
import com.myproject.composeflow.Components.Design.paddingValues
import com.myproject.composeflow.Components.Form.DropDownOption
import com.myproject.composeflow.Components.Layouts.Grid.GridItem
import com.myproject.composeflow.Components.Layouts.Grid.RegularGrid
import com.myproject.composeflow.Components.Layouts.Grid.WeightedGrid
import com.myproject.composeflow.Components.Layouts.GridLayout
import com.myproject.composeflow.Components.Layouts.ScaffoldLayout
import com.myproject.composeflow.Components.Layouts.SingleColumnLayout
import com.myproject.composeflow.Components.Layouts.SingleRowLayout
import com.myproject.composeflow.Components.Layouts.Vertical.DynamicColumn
import com.myproject.composeflow.Components.Loading.LoadingScreen
import com.myproject.composeflow.Components.Text.SingleLineInputText
import com.myproject.composeflow.Components.Text.TextBlock
import com.myproject.testingframework.Authentication.AuthenticationScreen
import com.myproject.testingframework.SignUpForm.SignupScreen
import com.myproject.testingframework.divkitSample.UiTemplate
import com.myproject.testingframework.mvvm_Arc.view.DynamicLayoutScreen
import com.myproject.testingframework.mvvm_Arc.view.Screen
import com.myproject.testingframework.screens.DynamicScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Navigation(navController = navController)
        }
    }
}


@Composable
fun Navigation(modifier: Modifier = Modifier, navController: NavHostController) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    NavHost(navController = navController, startDestination = screenListNew.first(), builder = {
        screenListNew.forEachIndexed { index, screen ->
            composable(screen) {
//                LaunchedEffect(Unit) {
//                    isLoading = true
//                    delay(500)
//                    isLoading = false
//                }
//                if (screen == screenList[2]) {
//                    BackHandler {
//                        (context as? Activity)?.finish()
//                    }
//                }
//                if (isLoading) {
//                    LoadingScreen()
//                } else {
//                    Screen(
//                        id = jsonId[index],
//                        NavController = navController
//                    )
//                }
//                Screen(
//                    id = jsonId[index],
//                    NavController = navController
//                )
                DynamicLayoutScreen(
                    id = jsonIdNew[index],
                    NavController = navController
                )
            }
        }
    })
}

val screenList = listOf("formScreen", "SignUpPage", "mvvmPage", "screen2")
val jsonId = listOf(R.raw.login, R.raw.signup, R.raw.mvvm, R.raw.screen2)

//new screen list and ids
val screenListNew = listOf("01_login", "02_signup", "03_home", "04_detail")
val jsonIdNew = listOf(R.raw.new_login, R.raw.new_signup, R.raw.new_home, R.raw.new_detail)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DayWiseListScreen(isSticky: Boolean) {
    val items: Map<String, List<String>> = mapOf(
        "Monday" to listOf("Task 1", "Task 2", "Task 3", "Task 4", "Task 5", "Task 6"),
        "Tuesday" to listOf("Meeting 1", "Workshop"),
        "Wednesday" to listOf("Task 3", "Task 4"),
        "Thursday" to listOf("Task 3", "Task 4"),
        "Friday" to listOf(
            "Task 1",
            "Task 2",
            "Task 3",
            "Task 4",
            "Task 1",
            "Task 2",
            "Task 3",
            "Task 4"
        ),
    )

    LazyColumn {
        items.forEach { (day, tasks) ->
            if (tasks.isNotEmpty()) {
                if (isSticky) {
                    stickyHeader {
                        Text(
                            text = day,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Gray)
                                .padding(8.dp),
                            color = Color.White,
                        )
                    }
                } else {
                    item {
                        Text(
                            text = day,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Gray)
                                .padding(8.dp),
                            color = Color.White,
                        )
                    }
                }
            }

            items(tasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Text(
                        text = task,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}





