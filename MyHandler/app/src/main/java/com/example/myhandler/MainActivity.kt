package com.example.myhandler

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
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
import com.example.myhandler.ui.theme.MyHandlerTheme
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thread {
            Looper.prepare()
            val handler = Looper.myLooper()?.let { Handler(it) }
            if (handler != null) {
                Log.e("MainActivity", "Handler is not null!")
            }
        }
        setContent {
            MyHandlerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
                    HandlerTest()
                }
            }
        }
    }
}

@Composable
fun HandlerTest() {
    val context = LocalContext.current
    Column {
        Button(onClick = {
            thread {
                val handler = Handler(Looper.getMainLooper(), Handler.Callback {
                    Log.e("HandlerTest", "Callback!")
                    return@Callback true
                })
                handler.postDelayed(
                    {
                        Toast.makeText(
                            context,
                            "Toast in thread",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    1000
                )
            }
        }) {
            Text(text = "Toast in thread")
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
    MyHandlerTheme {
        Greeting("Android")
    }
}