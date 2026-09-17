package com.orion.settings.about;

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.SystemProperties
import android.util.Log
import androidx.preference.Preference
import com.android.settings.R
import com.android.settings.core.BasePreferenceController

class MaintainerPreferenceController(context: Context, key: String) :
    BasePreferenceController(context, key) {

    companion object {
        private const val TAG = "MaintainerPreferenceController"
        private const val URL_TEAM = "https://www.orionos-project.com/developers"
        private const val ORION_MAINTAINER = "ro.orion.maintainer"
    }

    private val maintainer: String = SystemProperties.get(
        ORION_MAINTAINER,
        mContext.getString(R.string.maintainer_info_default)
    )

    override fun getAvailabilityStatus(): Int =
        if (maintainer.isNotBlank()) AVAILABLE else CONDITIONALLY_UNAVAILABLE

    override fun getSummary(): CharSequence = maintainer

    override fun handlePreferenceTreeClick(preference: Preference): Boolean {
        if (preference.key != preferenceKey) return false

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_TEAM))
        try {
            mContext.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.w(TAG, "No app available to open $URL_TEAM", e)
        }
        return true
    }
}