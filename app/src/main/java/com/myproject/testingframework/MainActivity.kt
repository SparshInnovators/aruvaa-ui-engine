package com.myproject.testingframework

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Paint.Align
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.myproject.composeflow.Components.Container.BoxContainer
import com.myproject.composeflow.Components.Container.VerticalContainer
import com.myproject.composeflow.Components.Form.DropDownOption
import com.myproject.composeflow.Components.Layouts.Vertical.DynamicColumn
import com.myproject.composeflow.Components.Loading.LoadingScreen
import com.myproject.composeflow.Components.Text.TextBlock
import com.myproject.testingframework.Authentication.AuthenticationScreen
import com.myproject.testingframework.SignUpForm.SignupScreen
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
//            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                Navigation(navController = navController, modifier = Modifier.padding(innerPadding))
//            }
            Navigation(navController = navController)
//            MyScreen()
        }
    }
}


@Composable
fun Navigation(modifier: Modifier = Modifier, navController: NavHostController) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    NavHost(navController = navController, startDestination = screenList.first(), builder = {
        screenList.forEachIndexed { index, screen ->
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
                Screen(
                    id = jsonId[index],
                    NavController = navController
                )
            }
        }
    })
}

val screenList = listOf("formScreen", "SignUpPage", "mvvmPage", "screen2")
val jsonId = listOf(R.raw.login, R.raw.signup, R.raw.mvvm, R.raw.screen2)


@Composable
fun MyScreen(modifier: Modifier = Modifier) {
    Column {
        LazyColumn {
            item {
                TextBlock(text = "Heading 1")
            }
            items(50){
                TextBlock(text = "123456789")
            }
            item {
                TextBlock(text = "Heading 2")
            }
            item {
                TextBlock(text = "Heading 3")
            }
        }
    }
}





