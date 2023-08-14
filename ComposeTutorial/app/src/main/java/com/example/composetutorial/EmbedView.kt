package com.example.composetutorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CustomView() {
//    var selectedItem by remember { mutableStateOf(0) }

    // Adds view to Compose
    AndroidView(
        modifier = Modifier.size(25.dp), // Occupy the max size in the Compose UI tree
        factory = { context ->
            // Creates view
            BubbleView(context)
        },
        update = { view ->
            /*
                比如我在View里面自定义了一些属性，当view更新的时候，这些属性会变化。View自己是知道的，
                但是如何返回给外面的Compose组件呢？在这里就可以做到了。
             */
        }
    )
}

@Composable
fun ContentExample() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Look at this CustomView!")
        CustomView()
        Text(text = "Look at me!")
    }
}