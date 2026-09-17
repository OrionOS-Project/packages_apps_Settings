package com.orion.settings.about;

import android.content.Context
import android.os.Build
import android.widget.TextView
import androidx.preference.PreferenceScreen
import com.android.settings.R
import com.android.settings.orion.about.utils.SpecUtils
import com.android.settingslib.core.AbstractPreferenceController
import com.android.settingslib.widget.LayoutPreference

class AboutPreferenceController(context: Context) : AbstractPreferenceController(context) {

    companion object {
        private const val KEY_ORION_ABOUT_INFO = "about_phone_info_header"
    }

    override fun displayPreference(screen: PreferenceScreen) {
        super.displayPreference(screen)
        val layout = screen.findPreference<LayoutPreference>(KEY_ORION_ABOUT_INFO) ?: return

        layout.findViewById<TextView>(R.id.device_summary)?.text =
            Build.MODEL.ifBlank { mContext.getString(R.string.device_info_default) }

        layout.findViewById<TextView>(R.id.cpu_summary)?.text = SpecUtils.getCpuModel()

        layout.findViewById<TextView>(R.id.memory_storage_summary)?.text =
            mContext.getString(
                R.string.orion_memory_storage_summary,
                SpecUtils.getTotalInternalMemorySize(),
                SpecUtils.getTotalRAM(mContext)
            )

        layout.findViewById<TextView>(R.id.battery_size_summary)?.text =
            mContext.getString(R.string.orion_battery_summary, SpecUtils.getBatteryCapacity(mContext))

        layout.findViewById<TextView>(R.id.screen_res_summary)?.text =
            SpecUtils.getScreenResolution(mContext)

        layout.findViewById<TextView>(R.id.build_status_summary)?.text =
            SpecUtils.getBuildStatus()
    }

    override fun isAvailable(): Boolean = true
    override fun getPreferenceKey(): String = KEY_ORION_ABOUT_INFO
}