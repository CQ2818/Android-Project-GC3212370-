package com.griffith.mydiary.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material.icons.outlined.SentimentNeutral
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griffith.mydiary.data.Mood
import com.griffith.mydiary.data.style

/**
 * Maps each mood to its matching icon.
 */
fun Mood.icon(): ImageVector = when (this) {
    Mood.HAPPY -> Icons.Outlined.SentimentSatisfied
    Mood.PEACE -> Icons.Outlined.SentimentNeutral
    Mood.SAD -> Icons.Outlined.SentimentDissatisfied
}

/**
 * A custom composable that displays a mood with its icon and label.
 * IT is styled with the mood's colors in a rounded pill shape.
 */
@Composable
fun MoodChip(mood: Mood, modifier: Modifier = Modifier) {
    val moodStyle = mood.style()
    Row(
        modifier = modifier
            .background(moodStyle.background, RoundedCornerShape(percent = 50))
            .padding(horizontal = 9.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = mood.icon(),
            contentDescription = null,
            tint = moodStyle.foreground,
            modifier = Modifier.size(12.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = mood.label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = moodStyle.foreground
        )
    }
}
