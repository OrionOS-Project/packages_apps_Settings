package com.orion.settings.about;

import android.app.settings.SettingsEnums
import android.content.Context
import android.provider.SearchIndexableResource
import com.android.settings.R
import com.android.settings.dashboard.DashboardFragment
import com.android.settings.deviceinfo.firmwareversion.FirmwareVersionScreen
import com.android.settings.search.BaseSearchIndexProvider
import com.android.settingslib.core.AbstractPreferenceController
import com.android.settingslib.search.Indexable
import com.android.settingslib.search.SearchIndexable

@SearchIndexable
class AboutDeviceSettings : DashboardFragment() {

    override fun getPreferenceScreenBindingKey(context: Context): String =
        FirmwareVersionScreen.KEY

    override fun getPreferenceScreenResId(): Int = R.xml.orion_about_device

    override fun getLogTag(): String = TAG

    override fun getMetricsCategory(): Int = SettingsEnums.DIALOG_FIRMWARE_VERSION

    override fun createPreferenceControllers(context: Context): List<AbstractPreferenceController> =
        listOf(
            AboutPreferenceController(context),
            SecurityStatusPreferenceController(context)
        )

    companion object {
        private const val TAG = "AboutDeviceSettings"

        @JvmField
        val SEARCH_INDEX_DATA_PROVIDER: Indexable.SearchIndexProvider =
            object : BaseSearchIndexProvider() {
                override fun getXmlResourcesToIndex(
                    context: Context,
                    enabled: Boolean
                ): List<SearchIndexableResource> =
                    listOf(
                        SearchIndexableResource(context).apply {
                            xmlResId = R.xml.orion_about_device
                        }
                    )
            }
    }
}