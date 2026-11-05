package com.griffith.mydiary.data

import androidx.compose.ui.graphics.Color
import com.griffith.mydiary.ui.theme.CalmBg
import com.griffith.mydiary.ui.theme.CalmGray
import com.griffith.mydiary.ui.theme.HappyBg
import com.griffith.mydiary.ui.theme.HappyGreen
import com.griffith.mydiary.ui.theme.SadBg
import com.griffith.mydiary.ui.theme.SadRed

enum class Mood(val label: String) {
    HAPPY("Happy"),
    PEACE("Peace"),
    SAD("Sad")
}

data class MoodStyle(val background: Color, val foreground: Color)

fun Mood.style(): MoodStyle = when (this) {
    Mood.HAPPY -> MoodStyle(HappyBg, HappyGreen)
    Mood.PEACE -> MoodStyle(CalmBg, CalmGray)
    Mood.SAD -> MoodStyle(SadBg, SadRed)
}

data class DiaryEntry(
    val id: Long,
    val title: String,
    val content: String,
    val mood: Mood,
    val location: String,
    val createdAt: Long,
    val steps: Int = 0
)
