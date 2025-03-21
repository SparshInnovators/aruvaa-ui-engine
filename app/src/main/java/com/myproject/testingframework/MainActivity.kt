package com.myproject.testingframework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myproject.testingframework.mvvm_Arc.view.DynamicLayoutScreen
import com.myproject.testingframework.mvvm_Arc.view.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

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
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "splash Screen",
        builder = {

            composable("splash Screen") {
                SplashScreen(
                    navController = navController
                )
            }

            composable("aruvaa/{screen}") { backStackEntry ->

                val screen = backStackEntry.arguments?.getString("screen") ?: ""

                DynamicLayoutScreen(
                    id = screen,
                    NavController = navController
                )
            }
        }
    )
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





