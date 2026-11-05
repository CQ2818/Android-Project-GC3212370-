package com.griffith.mydiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

/**
 * The app's entry point and its only activity.
 * It uses setContent to host the root composable DiaryApp.
 * */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiaryApp()
        }
    }
}
