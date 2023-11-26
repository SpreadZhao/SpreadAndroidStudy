package com.example.gcglfz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gcglfz.ui.theme.GcglfzTheme

class ResultActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      GcglfzTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          Result()
        }
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Result() {
  LazyColumn {
    Events.game.finishedStudents.forEach {
      item {
        val finishedEvents = arrayListOf<String>().apply {
          for (ev in it.finishedEvent) {
            if (ev.value) add(Events.getName(ev.key))
          }
        }
        Column {
          FlowRow {
            Text(text = "id: ${it.id}")
            Text(text = "期望分数: ${it.expectedScore}")
            Text(text = "完成的项目: $finishedEvents")
            Text(text = "总分: ${it.currentScore}")
          }
          Text(text = "---------------------------------------")
        }
      }
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
  GcglfzTheme {
    Greeting("Android")
  }
}