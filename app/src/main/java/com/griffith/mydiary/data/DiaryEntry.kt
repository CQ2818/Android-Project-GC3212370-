package com.griffith.mydiary.data

import androidx.compose.ui.graphics.Color
import com.griffith.mydiary.ui.theme.CalmBg
import com.griffith.mydiary.ui.theme.CalmGray
import com.griffith.mydiary.ui.theme.HappyBg
import com.griffith.mydiary.ui.theme.HappyGreen
import com.griffith.mydiary.ui.theme.SadBg
import com.griffith.mydiary.ui.theme.SadRed

/**
 * Represents the user's mood when writing an entry.
 * It has three options, each with a display label.
 */
enum class Mood(val label: String) {
    HAPPY("Happy"),
    PEACE("Peace"),
    SAD("Sad")
}

/**
 * A color pair used to display a mood, holding a background and a foreground color.
 */
data class MoodStyle(val background: Color, val foreground: Color)

/**
 * Maps each mood to its matching color style.
 */
fun Mood.style(): MoodStyle = when (this) {
    Mood.HAPPY -> MoodStyle(HappyBg, HappyGreen)
    Mood.PEACE -> MoodStyle(CalmBg, CalmGray)
    Mood.SAD -> MoodStyle(SadBg, SadRed)
}

/**
 * The data model for a single diary entry.
 * It stores the title, content, mood, location, timestamp and step count.
 */
data class DiaryEntry(
    val id: Long,
    val title: String,
    val content: String,
    val mood: Mood,
    val location: String,
    val createdAt: Long,
    val steps: Int = 0
)
