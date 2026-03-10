package com.bio.omnitrix.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.material.*
import com.bio.omnitrix.presentation.components.OmnitrixWatchFace
import com.bio.omnitrix.presentation.components.TransformationAnimation
import com.bio.omnitrix.presentation.model.Alien
import com.bio.omnitrix.presentation.model.AlienList
import com.bio.omnitrix.presentation.screens.AlienModeScreen
import com.bio.omnitrix.presentation.screens.AlienSelectorScreen
import com.bio.omnitrix.presentation.theme.OmnitrixBlack
import com.bio.omnitrix.presentation.theme.OmnitrixTheme
import com.bio.omnitrix.presentation.utils.executeAlienTask
import kotlinx.coroutines.delay

private const val TAG = "OmnitrixApp"

enum class OmnitrixState {
    BOOT, IDLE, SELECTOR, TRANSFORMING, ALIEN_MODE
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OmnitrixApp()
        }
    }
}

@Composable
fun OmnitrixApp() {
    val context = LocalContext.current
    OmnitrixTheme {
        var currentState by rememberSaveable { mutableStateOf(OmnitrixState.BOOT) }
        var selectedAlienId by rememberSaveable { mutableStateOf<String?>(null) }
        
        val selectedAlien = remember(selectedAlienId) {
            AlienList.find { it.id == selectedAlienId }
        }

        LaunchedEffect(currentState) {
            Log.d(TAG, "Current State: $currentState")
            if (currentState == OmnitrixState.BOOT) {
                delay(1500)
                currentState = OmnitrixState.IDLE
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize().background(OmnitrixBlack)
        ) {
            Crossfade(targetState = currentState, label = "screenTransition") { state ->
                when (state) {
                    OmnitrixState.BOOT -> BootAnimation()
                    
                    OmnitrixState.IDLE -> OmnitrixWatchFace(
                        onDialClick = { 
                            Log.d(TAG, "Watch face clicked -> switching to SELECTOR")
                            currentState = OmnitrixState.SELECTOR 
                        }
                    )
                    
                    OmnitrixState.SELECTOR -> AlienSelectorScreen(
                        onTransform = { alien ->
                            Log.d(TAG, "Alien selected: ${alien.name} -> TRANSFORMING")
                            selectedAlienId = alien.id
                            currentState = OmnitrixState.TRANSFORMING
                        },
                        onBack = { 
                            Log.d(TAG, "Back from selector -> IDLE")
                            currentState = OmnitrixState.IDLE 
                        }
                    )
                    
                    OmnitrixState.TRANSFORMING -> TransformationAnimation(
                        onComplete = {
                            Log.d(TAG, "Transformation complete -> ALIEN_MODE")
                            selectedAlien?.let { alien ->
                                executeAlienTask(alien.task, context)
                            }
                            currentState = OmnitrixState.ALIEN_MODE
                        }
                    )
                    
                    OmnitrixState.ALIEN_MODE -> selectedAlien?.let { alien ->
                        AlienModeScreen(
                            alien = alien,
                            onTimeout = { 
                                Log.d(TAG, "Alien mode timeout -> IDLE")
                                currentState = OmnitrixState.IDLE 
                                selectedAlienId = null
                            }
                        )
                    } ?: run {
                        currentState = OmnitrixState.IDLE
                    }
                }
            }
        }
    }
}

@Composable
fun BootAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "boot")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bootAlpha"
    )

    Box(
        modifier = Modifier.fillMaxSize().background(OmnitrixBlack),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "OMNITRIX",
                color = Color(0xFF39FF14).copy(alpha = alpha),
                style = MaterialTheme.typography.title1
            )
            Text(
                text = "SYSTEM ACTIVE",
                color = Color(0xFF39FF14).copy(alpha = 0.6f),
                style = MaterialTheme.typography.caption2
            )
        }
    }
}
