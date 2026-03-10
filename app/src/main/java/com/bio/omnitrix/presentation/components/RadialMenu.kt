package com.bio.omnitrix.presentation.components

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.bio.omnitrix.presentation.model.Alien
import com.bio.omnitrix.presentation.theme.OmnitrixGreen
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RadialMenu(
    aliens: List<Alien>,
    onAlienSelected: (Alien) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val vibrator = remember { context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    
    var rotationAngle by remember { mutableStateOf(0f) }
    var lastSelectedIndex by remember { mutableIntStateOf(-1) }
    
    val animatedRotation by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = tween(200),
        label = "rotation"
    )

    val alienCount = aliens.size
    val angleStep = (2 * PI) / alienCount
    
    val painters = aliens.map { rememberVectorPainter(ImageVector.vectorResource(id = it.iconResId)) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val sensitivity = 0.015f 
                    rotationAngle += dragAmount.x * sensitivity
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasCenter = Offset(size.width / 2, size.height / 2)
            val minDimension = minOf(size.width, size.height)
            val radius = 0.65f * (minDimension / 2)
            val iconSizePx = 32.dp.toPx()

            // 1. Draw Alien Icons using Polar Coordinates
            aliens.forEachIndexed { index, _ ->
                val angle = (index * angleStep) + animatedRotation - (PI / 2)
                
                val x = canvasCenter.x + radius * cos(angle).toFloat()
                val y = canvasCenter.y + radius * sin(angle).toFloat()

                translate(x - iconSizePx / 2, y - iconSizePx / 2) {
                    with(painters[index]) {
                        draw(size = Size(iconSizePx, iconSizePx))
                    }
                }
            }

            // 2. Fixed Selection Ring at 12 o'clock
            val selectorAngle = -PI / 2
            val selectorX = canvasCenter.x + radius * cos(selectorAngle).toFloat()
            val selectorY = canvasCenter.y + radius * sin(selectorAngle).toFloat()

            drawCircle(
                color = OmnitrixGreen,
                radius = (iconSizePx / 2) + 6.dp.toPx(),
                center = Offset(selectorX, selectorY),
                style = Stroke(width = 3.dp.toPx())
            )
        }
    }
    
    // Haptic feedback and selection logic
    LaunchedEffect(rotationAngle) {
        val normalizedRotation = ((-rotationAngle % (2 * PI)) + (2 * PI)) % (2 * PI)
        val selectedIndex = ((normalizedRotation / angleStep) + 0.5).toInt() % alienCount
        
        if (selectedIndex != lastSelectedIndex) {
            lastSelectedIndex = selectedIndex
            // Safe vibration check
            try {
                if (context.checkCallingOrSelfPermission(android.Manifest.permission.VIBRATE) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE))
                }
            } catch (e: SecurityException) {
                // Silently ignore if permission is still missing to prevent crash
            }
            onAlienSelected(aliens[selectedIndex])
        }
    }
}
