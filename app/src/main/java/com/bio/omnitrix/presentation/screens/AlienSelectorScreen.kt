package com.bio.omnitrix.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.bio.omnitrix.presentation.components.OmnitrixSymbol
import com.bio.omnitrix.presentation.components.RadialMenu
import com.bio.omnitrix.presentation.model.Alien
import com.bio.omnitrix.presentation.model.AlienList
import com.bio.omnitrix.presentation.theme.OmnitrixBlack
import com.bio.omnitrix.presentation.theme.OmnitrixGreen

@Composable
fun AlienSelectorScreen(
    onTransform: (Alien) -> Unit,
    onBack: () -> Unit
) {
    var selectedAlien by remember { mutableStateOf(AlienList[0]) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OmnitrixBlack),
        contentAlignment = Alignment.Center
    ) {
        // RadialMenu is on the bottom layer
        RadialMenu(
            aliens = AlienList,
            onAlienSelected = { selectedAlien = it }
        )

        // Center UI is on the top layer
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = selectedAlien.name.uppercase(),
                color = OmnitrixGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            // The Transform Button
            Button(
                onClick = { 
                    onTransform(selectedAlien) 
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = OmnitrixGreen,
                    contentColor = OmnitrixBlack
                ),
                modifier = Modifier.size(64.dp)
            ) {
                OmnitrixSymbol(modifier = Modifier.size(44.dp))
            }
        }
    }
}
