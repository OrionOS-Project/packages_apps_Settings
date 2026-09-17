package com.orion.settings.utils;

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.os.SystemProperties
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import kotlin.math.roundToInt
import java.io.File

object SpecUtils {
    private const val TAG = "SpecUtils"
    private const val POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile"

    private const val PROP_SCREEN_RESOLUTION = "ro.screen_resolution"
    private const val PROP_BUILD_STATUS = "ro.orion.build.status"

    fun getTotalInternalMemorySize(): String {
        val stat = StatFs(Environment.getDataDirectory().path)
        val totalGb = stat.blockCountLong * stat.blockSizeLong / 1073741824.0
        return when (totalGb.roundToInt()) {
            in 0..16 -> "16"
            in 17..32 -> "32"
            in 33..64 -> "64"
            in 65..128 -> "128"
            in 129..256 -> "256"
            in 257..512 -> "512"
            else -> "512+"
        }
    }

    fun getTotalRAM(context: Context): Int {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val info = ActivityManager.MemoryInfo().also { am.getMemoryInfo(it) }
        return (info.totalMem / 1073741824.0).roundToInt()
    }

    fun getBatteryCapacity(context: Context): Int {
        return try {
            val clazz = Class.forName(POWER_PROFILE_CLASS)
            val profile = clazz.getConstructor(Context::class.java).newInstance(context)
            val capacity = clazz.getMethod("getAveragePower", String::class.java)
                .invoke(profile, "battery.capacity") as? Double ?: return 0
            if (capacity > 0) capacity.roundToInt() else 0
        } catch (e: Exception) {
            Log.w(TAG, "Battery capacity not available", e)
            0
        }
    }

    fun getScreenResolution(context: Context): String {
        SystemProperties.get(PROP_SCREEN_RESOLUTION)
            .takeIf { it.isNotBlank() }
            ?.let { return it.replace("x", " x ").replace(Regex("\\s+"), " ").trim() }

        return try {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val b = wm.currentWindowMetrics.bounds
                "${b.width()} x ${b.height()}"
            } else {
                @Suppress("DEPRECATION")
                val dm = DisplayMetrics().also { wm.defaultDisplay.getRealMetrics(it) }
                "${dm.widthPixels} x ${dm.heightPixels}"
            }
        } catch (e: Exception) {
            Log.w(TAG, "Screen resolution not available", e)
            "Unknown"
        }
    }

    fun getCpuModel(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Build.SOC_MODEL?.takeIf { it.isNotBlank() }?.let { return it }
        }
        SystemProperties.get("ro.soc.model").takeIf { it.isNotBlank() }?.let { return it }
        SystemProperties.get("ro.hardware.chipname").takeIf { it.isNotBlank() }?.let { return it }
        cpuFromProc()?.let { return it }
        return Build.HARDWARE.ifBlank { "Unknown" }
    }

    private fun cpuFromProc(): String? = try {
        File("/proc/cpuinfo").readLines().firstNotNullOfOrNull { line ->
            val key = line.substringBefore(':').trim()
            if (key in listOf("Hardware", "model name", "Processor")) {
                line.substringAfter(':').trim().takeIf { it.isNotEmpty() }
            } else null
        }
    } catch (e: Exception) {
        Log.w(TAG, "Error reading /proc/cpuinfo", e)
        null
    }

    fun getBuildStatus(): String {
        val status = SystemProperties.get(PROP_BUILD_STATUS, "Unofficial").trim()
        return status.replaceFirstChar { it.uppercase() }
    }
}