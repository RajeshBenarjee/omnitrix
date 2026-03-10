package com.bio.omnitrix.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.bio.omnitrix.presentation.theme.OmnitrixGreen
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun OmnitrixWatchFace(
    modifier: Modifier = Modifier,
    onDialClick: () -> Unit
) {
    var currentTime by remember { mutableStateOf(LocalTime.now()) }
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            kotlinx.coroutines.delay(1000)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(CircleShape)
            .background(Color.Black)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { 
                    android.util.Log.d("Omnitrix", "Watch face clicked!")
                    onDialClick() 
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val density = LocalDensity.current
            val size = minOf(maxWidth, maxHeight)
            val sizePx = with(density) { size.toPx() }
            
            // Layer 1: Background Canvas Graphics
            Canvas(modifier = Modifier.size(size)) {
                // Background Glow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(OmnitrixGreen.copy(alpha = 0.3f), Color.Transparent),
                        center = center,
                        radius = sizePx / 2
                    ),
                    radius = sizePx / 2 * pulseScale
                )

                // Rotating Rings
                rotate(rotation) {
                    drawArc(
                        color = OmnitrixGreen,
                        startAngle = 0f,
                        sweepAngle = 90f,
                        useCenter = false,
                        style = Stroke(width = 4.dp.toPx())
                    )
                    drawArc(
                        color = OmnitrixGreen,
                        startAngle = 180f,
                        sweepAngle = 90f,
                        useCenter = false,
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
                rotate(-rotation * 0.5f) {
                    drawArc(
                        color = OmnitrixGreen.copy(alpha = 0.5f),
                        startAngle = 45f,
                        sweepAngle = 60f,
                        useCenter = false,
                        style = Stroke(width = 2.dp.toPx())
                    )
                    drawArc(
                        color = OmnitrixGreen.copy(alpha = 0.5f),
                        startAngle = 225f,
                        sweepAngle = 60f,
                        useCenter = false,
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
            }

            // Layer 2: Visual Symbol
            OmnitrixSymbol(
                modifier = Modifier.size(size * 0.7f)
            )

            // Layer 3: Central Time Display
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.display1.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Composable
fun OmnitrixSymbol(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        
        // Hourglass shape
        val path = Path().apply {
            moveTo(w * 0.2f, h * 0.2f)
            lineTo(w * 0.8f, h * 0.2f)
            lineTo(w * 0.2f, h * 0.8f)
            lineTo(w * 0.8f, h * 0.8f)
            close()
        }
        
        drawPath(
            path = path,
            color = OmnitrixGreen
        )
    }
}
