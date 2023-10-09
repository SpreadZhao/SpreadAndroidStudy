package com.spread.activitytest1

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.spread.activitytest1.ui.theme.ActivityTest1Theme

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate: $this")
        setContent {
            ActivityTest1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
                    LaunchModeTest()
                }
            }
        }
    }

    override fun onPause() {
        Log.e(TAG, "onPause: $this")
        super.onPause()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.e(TAG, "onRestoreInstanceState: $this")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.e(TAG, "onSaveInstance: $this")
        super.onSaveInstanceState(outState)
    }


    override fun onStop() {
        Log.e(TAG, "onStop: $this")
        if (isFinishing) {
            Log.e(TAG, "Activity is finishing")
        } else {
            Log.e(TAG, "Activity is not finishing")
        }
        super.onStop()
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy: $this")
        super.onDestroy()
    }
}

@Composable
fun LaunchModeTest() {
    val context = LocalContext.current

    Column {
        Button(onClick = {
            context.startActivity(
                Intent().setComponent(
                    ComponentName("com.example.activitytest2", "com.example.activitytest2.MainActivity")
                )
            )
        }) {
            Text(text = "Start Other App's Activity")
        }
        Button(onClick = {
            val intent = Intent("com.example.activitytest2.ACTION_TEST").apply {
                putExtra("name", "Spread")
            }
            context.startActivity(intent)
        }) {
            Text(text = "Start EditActivity2")
        }
        Button(onClick = {
            if (context is Activity) {
                context.finish()
            }
        }) {
            Text(text = "finish")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ActivityTest1Theme {
        Greeting("Android")
    }
}