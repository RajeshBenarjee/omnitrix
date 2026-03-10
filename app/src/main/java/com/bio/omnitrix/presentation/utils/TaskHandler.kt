package com.bio.omnitrix.presentation.utils

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.bio.omnitrix.presentation.model.AlienTask

private const val TAG = "OmnitrixTaskHandler"

fun executeAlienTask(task: AlienTask, context: Context) {
    Log.d(TAG, "Executing task: $task")
    when (task) {
        AlienTask.HEART_RATE -> {
            showTaskToast(context, "Scanning Heart Rate...")
        }
        AlienTask.STEP_COUNTER -> {
            showTaskToast(context, "Steps: 4,521")
        }
        AlienTask.TIMER -> {
            // Using a more reliable intent for Wear OS clocks/timers
            val intent = Intent(AlarmClock.ACTION_SET_TIMER)
            if (intent.resolveActivity(context.packageManager) != null) {
                tryLaunch(context, intent, "Timer")
            } else {
                // Fallback to date settings if timer activity isn't found
                tryLaunch(context, Intent(Settings.ACTION_DATE_SETTINGS), "Date Settings")
            }
        }
        AlienTask.FLASHLIGHT -> {
            showTaskToast(context, "Flashlight Activated")
        }
        AlienTask.WORKOUT -> {
            showTaskToast(context, "Starting Workout...")
        }
        AlienTask.COMPASS -> {
            showTaskToast(context, "Compass Active")
        }
        AlienTask.SETTINGS -> {
            val intent = Intent(Settings.ACTION_SETTINGS)
            tryLaunch(context, intent, "Settings")
        }
        AlienTask.DND -> {
            toggleDoNotDisturb(context)
        }
    }
}

private fun toggleDoNotDisturb(context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        try {
            if (!notificationManager.isNotificationPolicyAccessGranted) {
                Log.w(TAG, "DND Permission not granted. Opening settings.")
                val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                showTaskToast(context, "Grant 'Omnitrix' access to toggle DND")
            } else {
                val currentFilter = notificationManager.currentInterruptionFilter
                val newFilter = if (currentFilter == NotificationManager.INTERRUPTION_FILTER_ALL) {
                    NotificationManager.INTERRUPTION_FILTER_NONE
                } else {
                    NotificationManager.INTERRUPTION_FILTER_ALL
                }
                notificationManager.setInterruptionFilter(newFilter)
                
                val status = if (newFilter == NotificationManager.INTERRUPTION_FILTER_NONE) "ON" else "OFF"
                showTaskToast(context, "Ghostfreak: DND $status")
                Log.d(TAG, "DND Toggled to $status")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error toggling DND", e)
            showTaskToast(context, "DND Toggle Failed")
        }
    } else {
        showTaskToast(context, "DND not supported")
    }
}

private fun tryLaunch(context: Context, intent: Intent, name: String) {
    try {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        Log.d(TAG, "Launched $name")
    } catch (e: Exception) {
        Log.e(TAG, "Failed to launch $name", e)
        showTaskToast(context, "Cannot open $name")
    }
}

private fun showTaskToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

// Mocking AlarmClock constants if not found in some SDKs
object AlarmClock {
    const val ACTION_SET_TIMER = "android.intent.action.SET_TIMER"
}
