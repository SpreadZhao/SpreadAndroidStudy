package com.example.composetutorial.ui.home

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composetutorial.MainActivity
import com.example.composetutorial.MyApplication
import com.example.composetutorial.R
import com.example.composetutorial.ui.Window
import com.example.composetutorial.ui.home.model.ProductCard
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickToDetailScreen: (Int) -> Unit = {},
    context: Context
){
    var text by remember{ mutableStateOf("") }


    Column {

        Button(onClick = {
            val window = Window(context)
            window.open()
        }) {
            Text(text = "open window")
        }
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = text,
            onValueChange = {text = it},
            label = { Text(text = "Param to pass") },
        )

        LazyVerticalGrid(
            modifier = Modifier
                .padding(16.dp),
//        columns = GridCells.Fixed(2),
            columns = GridCells.Adaptive(minSize = 96.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ){
//            items(60){
//                ProductCard(
//                    modifier = modifier
//                        .padding(top = 16.dp),
//                    onclickProduct = { onClickToDetailScreen.invoke(text.toInt()) }
//                )
//            }
            for(i in 1..60){
                item {
                    ProductCard(
                        modifier = modifier
                            .padding(top = 16.dp),
                        onclickProduct = {
                            val param = if(text == "") i
                                        else text.toInt()
                            onClickToDetailScreen.invoke(param)
                        },
                        name = i.toString()
                    )
                }
            }
        }
    }







}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview(){
//    ComposeTutorialTheme {
//        HomeScreen()
//    }
//}

