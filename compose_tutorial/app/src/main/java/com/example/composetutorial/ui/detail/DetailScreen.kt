package com.example.composetutorial.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.detail.model.ProductHeader
import com.example.composetutorial.ui.detail.model.ProductImageCarousel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    id: Int = 0
){
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState) // Make it scrollable
    ) {
        ProductHeader(
            modifier = Modifier.padding(16.dp)
        )
        ProductImageCarousel(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        val str = StringBuilder()
        repeat(1000){
            str.append("spread ")
        }
        Text(text = str.toString())
        Text(text = "param: $id")
    }
}