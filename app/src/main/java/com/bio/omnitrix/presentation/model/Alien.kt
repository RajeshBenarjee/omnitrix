package com.bio.omnitrix.presentation.model

import androidx.compose.ui.graphics.Color
import com.bio.omnitrix.R

data class Alien(
    val id: String,
    val name: String,
    val color: Color,
    val iconResId: Int,
    val task: AlienTask
)

val AlienList = listOf(
    Alien("heatblast", "Heatblast", Color(0xFFFF4500), R.drawable.ic_alien_heatblast, AlienTask.FLASHLIGHT),
    Alien("fourarms", "Four Arms", Color(0xFFB22222), R.drawable.ic_alien_fourarms, AlienTask.WORKOUT),
    Alien("xlr8", "XLR8", Color(0xFF00CED1), R.drawable.ic_alien_xlr8, AlienTask.TIMER),
    Alien("diamondhead", "Diamondhead", Color(0xFFADFF2F), R.drawable.ic_alien_diamondhead, AlienTask.HEART_RATE),
    Alien("ghostfreak", "Ghostfreak", Color(0xFFD3D3D3), R.drawable.ic_alien_ghostfreak, AlienTask.DND),
    Alien("upgrade", "Upgrade", Color(0xFF32CD32), R.drawable.ic_alien_upgrade, AlienTask.SETTINGS),
    Alien("cannonbolt", "Cannonbolt", Color(0xFFF5F5DC), R.drawable.ic_alien_cannonbolt, AlienTask.STEP_COUNTER),
    Alien("wildmutt", "Wildmutt", Color(0xFFFFA500), R.drawable.ic_alien_wildmutt, AlienTask.COMPASS)
)
