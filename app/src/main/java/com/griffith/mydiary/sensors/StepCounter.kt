package com.griffith.mydiary.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

/**
 * Step counter for "today". TYPE_STEP_COUNTER reports the number of steps
 * since the last device reboot, so the first reading is stored as a baseline
 * and every later reading is the difference from it.
 */
@Composable
fun rememberTodayStepCount(): Int {
    val context = LocalContext.current
    var steps by remember { mutableIntStateOf(0) }

    DisposableEffect(context) {
        val manager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var baseline = prefs.getLong(KEY_BASELINE, -1L)

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val current = event.values[0].toLong()
                if (baseline < 0L) {
                    baseline = current
                    prefs.edit().putLong(KEY_BASELINE, baseline).apply()
                }
                steps = (current - baseline).coerceAtLeast(0L).toInt()
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }

        if (sensor != null) {
            manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        onDispose { manager.unregisterListener(listener) }
    }

    return steps
}

private const val PREFS_NAME = "my_diary_steps"
private const val KEY_BASELINE = "baseline"
