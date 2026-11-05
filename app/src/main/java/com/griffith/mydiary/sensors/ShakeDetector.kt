package com.griffith.mydiary.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import kotlin.math.sqrt

/**
 * Accelerometer based "shake to create a new entry".
 * Registered only while the hosting screen is composed, and unregistered on dispose.
 */
@Composable
fun ShakeToCreate(enabled: Boolean = true, onShake: () -> Unit) {
    val context = LocalContext.current
    val callback by rememberUpdatedState(onShake)

    DisposableEffect(context, enabled) {
        if (!enabled) {
            return@DisposableEffect onDispose { }
        }
        val manager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        var lastShakeTime = 0L

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                val magnitude = sqrt((x * x + y * y + z * z).toDouble()) - SensorManager.GRAVITY_EARTH
                if (magnitude > SHAKE_THRESHOLD) {
                    val now = System.currentTimeMillis()
                    if (now - lastShakeTime > SHAKE_COOLDOWN_MS) {
                        lastShakeTime = now
                        callback()
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }

        if (accelerometer != null) {
            manager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_UI)
        }

        onDispose { manager.unregisterListener(listener) }
    }
}

private const val SHAKE_THRESHOLD = 15.0
private const val SHAKE_COOLDOWN_MS = 1500L
