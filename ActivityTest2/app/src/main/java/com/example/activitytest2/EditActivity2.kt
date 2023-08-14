package com.example.activitytest2

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.activitytest2.ui.theme.ActivityTest2Theme

class EditActivity2 : ComponentActivity() {
    private val TAG = "EditActivity2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate: $this")
        setContent {
            ActivityTest2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContentInEditActivity2(context = this, name = TAG)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart: $this")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: $this")
    }

    override fun onPause() {
        Log.e(TAG, "onPause: $this")
        super.onPause()
    }

    override fun onStop() {
        Log.e(TAG, "onStop: $this")
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG, "onRestart: $this")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val newName = intent?.getStringExtra("name")
        setContent {
            ActivityTest2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContentInEditActivity2(context = this, name = newName ?: "")
                }
            }
        }
        Log.e(TAG, "onNewIntent: $this")
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy: $this")
        super.onDestroy()
    }
}

@Composable
fun ContentInEditActivity2(context: Context, name: String) {
    Column {
        Text(text = "I'm $name")
        Button(onClick = {
            context.startActivity(Intent(context, TestActivity::class.java))
        }) {
            Text(text = "Start TestActivity")
        }
        Button(onClick = {
            context.startActivity(Intent(context, EditActivity3::class.java))
        }) {
            Text(text = "Start EditActivity3")
        }
    }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    ActivityTest2Theme {
        Greeting3("Android")
    }
}