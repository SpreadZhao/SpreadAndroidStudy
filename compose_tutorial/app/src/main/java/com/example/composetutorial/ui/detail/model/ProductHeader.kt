package com.example.composetutorial.ui.detail.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composetutorial.R

@Composable
fun ProductHeader(
    modifier: Modifier = Modifier
){
    ConstraintLayout(
        modifier = modifier
    ){
        val(
            photoAvatar,
            nameText,
            titleText
        ) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colors.secondary, CircleShape)
                .constrainAs(photoAvatar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = "hehe",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(nameText) {
                    start.linkTo(photoAvatar.end, 16.dp)
                    top.linkTo(parent.top)
                }
        )




    }
}

@Preview(showBackground = true)
@Composable
fun ProductHeaderPreview(){
    ProductHeader()
}