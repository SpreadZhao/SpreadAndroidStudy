package com.example.composetutorial.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DetailFragment(
    modifier: Modifier = Modifier,
    id: Int = 0,
){
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        DetailScreen(modifier = modifier, id = id)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailFragmentPreview(){
    DetailFragment()
}