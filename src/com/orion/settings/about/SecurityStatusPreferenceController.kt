package com.orion.settings.about;

import android.content.Context
import android.os.SELinux
import android.widget.TextView
import androidx.preference.PreferenceScreen
import com.android.settings.R
import com.android.settingslib.DeviceInfoUtils
import com.android.settingslib.core.AbstractPreferenceController
import com.android.settingslib.widget.LayoutPreference

class SecurityStatusPreferenceController(context: Context) :
    AbstractPreferenceController(context) {

    companion object {
        private const val KEY_ORION_ABOUT_FOOTER_INFO = "about_phone_info_footer"
    }

    override fun displayPreference(screen: PreferenceScreen) {
        super.displayPreference(screen)
        val layout = screen.findPreference<LayoutPreference>(KEY_ORION_ABOUT_FOOTER_INFO) ?: return

        layout.findViewById<TextView>(R.id.selinux_summary)?.text =
            mContext.getString(
                if (SELinux.isSELinuxEnforced()) R.string.selinux_status_enforcing
                else R.string.selinux_status_permissive
            )

        layout.findViewById<TextView>(R.id.secpatch_summary)?.text =
            DeviceInfoUtils.getSecurityPatch()
    }

    override fun isAvailable(): Boolean = true
    override fun getPreferenceKey(): String = KEY_ORION_ABOUT_FOOTER_INFO
}