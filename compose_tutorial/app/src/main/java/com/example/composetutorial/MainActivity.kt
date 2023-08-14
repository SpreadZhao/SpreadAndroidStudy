package com.example.composetutorial

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composetutorial.ui.Window
import com.example.composetutorial.ui.detail.DetailFragment
import com.example.composetutorial.ui.home.HomeFragment
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import com.example.composetutorial.util.navi.Route

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, 0)
        }
        setContent {
            ComposeTutorialTheme {
                ComposeTutorialAppScreen(this)
            }
        }
    }
}

@Composable
fun ComposeTutorialAppScreen(
    context: Context
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ){
        composable(route = Route.Home.route) {
            HomeFragment(
                onClickToDetailScreen = { gamesId ->
                    navController.navigate(Route.Detail.createRoute(gamesId))
                },
                context = context
            )
        }
        composable(
            route = Route.Detail.route,
            arguments = listOf(
                navArgument("gamesId"){
                    type = NavType.IntType
                }
            )
        ) { backstackEntry ->
            val gamesId = backstackEntry.arguments?.getInt("gamesId")
            requireNotNull(gamesId){
                "gamesId param unset."
            }
            DetailFragment(Modifier, gamesId)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ComposeTutorialTheme {
//        ComposeTutorialAppScreen()
//    }
//}