package com.bio.omnitrix.presentation.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.bio.omnitrix.presentation.model.Alien
import com.bio.omnitrix.presentation.theme.OmnitrixBlack
import com.bio.omnitrix.presentation.theme.OmnitrixGreen
import kotlinx.coroutines.delay

@Composable
fun AlienModeScreen(
    alien: Alien,
    onTimeout: () -> Unit
) {
    var timeLeft by remember { mutableStateOf(30) }
    val infiniteTransition = rememberInfiniteTransition(label = "background")
    
    val bgScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bgScale"
    )

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
        onTimeout()
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(OmnitrixBlack),
        contentAlignment = Alignment.Center
    ) {
        val density = LocalDensity.current
        val size = minOf(maxWidth, maxHeight)
        val sizePx = with(density) { size.toPx() }
        
        // Dynamic background effect based on alien color
        Canvas(modifier = Modifier.size(size)) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(alien.color.copy(alpha = 0.4f), Color.Transparent),
                    center = center,
                    radius = (sizePx / 2) * bgScale
                ),
                radius = sizePx / 2
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = alien.name.uppercase(),
                style = androidx.wear.compose.material.MaterialTheme.typography.title1.copy(
                    color = alien.color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )
            
            Spacer(modifier = Modifier.height(10.dp))

            // Progress bar (Timeout)
            Box(
                modifier = Modifier
                    .size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawArc(
                        color = if (timeLeft < 5) Color.Red else OmnitrixGreen,
                        startAngle = -90f,
                        sweepAngle = (timeLeft / 30f) * 360f,
                        useCenter = false,
                        style = Stroke(width = 6.dp.toPx())
                    )
                }
                Text(
                    text = "$timeLeft",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            
            Spacer(modifier = Modifier.height(10.dp))
            
            Text(
                text = "ACTIVE",
                color = OmnitrixGreen.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }
    }
}
