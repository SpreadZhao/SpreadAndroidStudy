package com.example.composetutorial.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color as ComposeColor

@Composable
fun BubbleView() {
    var bubblePosition by remember { mutableStateOf(BubblePosition(0f, 0f)) }
    var bubbleSize by remember { mutableStateOf(BubbleSize(100f, 100f)) }
    var bubbleState by remember { mutableStateOf(BubbleState.Default) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, gestureZoom, _ ->
                    when (bubbleState) {
                        BubbleState.Default -> {
                            bubblePosition = BubblePosition(
                                bubblePosition.x + pan.x,
                                bubblePosition.y + pan.y
                            )
                            if (gestureZoom < 1f) {
                                bubbleState = BubbleState.Dismiss
                            }
                        }
                        BubbleState.Connect -> {
                            if (gestureZoom > 1f) {
                                bubbleState = BubbleState.Default
                            }
                        }
                        BubbleState.Dismiss -> {
                            if (gestureZoom > 1f) {
                                bubbleState = BubbleState.Default
                            }
                        }
                    }
                }
            }
    ) {
        if (bubbleState != BubbleState.Dismiss) {
            // Draw connecting line when connected
            if (bubbleState == BubbleState.Connect) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawLine(
                        ComposeColor.Red,
                        start = Offset(bubbleSize.width / 2f, bubbleSize.height / 2f),
                        end = Offset(bubblePosition.x + bubbleSize.width / 2f, bubblePosition.y + bubbleSize.height / 2f),
                        strokeWidth = 8f
                    )
                }
            }
            // Draw the bubble
            Box(
                modifier = Modifier
                    .graphicsLayer(
                        translationX = bubblePosition.x,
                        translationY = bubblePosition.y,
                        scaleX = if (bubbleState == BubbleState.Default) 1f else 0.8f,
                        scaleY = if (bubbleState == BubbleState.Default) 1f else 0.8f
                    )
            ) {
                Box(
                    modifier = Modifier
                        .size(bubbleSize.width.dp, bubbleSize.height.dp)
                        .background(ComposeColor.Red, shape = CircleShape)
                        .pointerInput(Unit) {
                            detectTransformGestures { _, _, gestureZoom, gestureRotation ->
                                if (bubbleState == BubbleState.Default) {
                                    if (gestureZoom > 1.2f) {
                                        bubbleState = BubbleState.Connect
                                    } else if (gestureZoom < 0.8f) {
                                        bubbleState = BubbleState.Dismiss
                                    }
                                }
                            }
                        }
                ) {
                    // Draw text on the bubble
                }
            }
        }
    }
}

data class BubblePosition(val x: Float, val y: Float)
data class BubbleSize(val width: Float, val height: Float)

enum class BubbleState {
    Default, // The bubble is in the default state
    Connect, // The bubble is connected to another bubble
    Dismiss // The bubble is dismissed
}
