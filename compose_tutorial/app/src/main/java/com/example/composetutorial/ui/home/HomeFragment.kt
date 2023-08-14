package com.example.composetutorial.ui.home

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.Window

@Composable
fun HomeFragment(
    modifier: Modifier = Modifier,
    onClickToDetailScreen: (Int) -> Unit = {},
    context: Context
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        HomeScreen(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            onClickToDetailScreen =  onClickToDetailScreen,
            context = context
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeFragmentPreview(){
//    HomeFragment()
//}

