package com.ciandt.championships.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// Fixed colors (not theme-derived) so the badge stays visually distinct in both
// light and dark mode: green marks a native (Kotlin/Compose) screen, purple marks
// a React Native screen once Lab 01 embeds one alongside this one.
object OriginBadgeColors {
    val Native = Color(0xFF14532D)
    val ReactNative = Color(0xFF4C1D95)
}

@Composable
fun OriginBadge(
    label: String = "NATIVE SCREEN",
    color: Color = OriginBadgeColors.Native,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxWidth(), color = color) {
        Text(
            text = label,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            textAlign = TextAlign.Center,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
        )
    }
}
