package com.orion.settings.about

import android.content.Context
import android.os.SystemProperties
import android.util.AttributeSet
import android.widget.TextView
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.android.settings.R

class BrandBannerPreference(context: Context, attrs: AttributeSet) :
    Preference(context, attrs) {

    init {
        isSelectable = false
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)

        holder.itemView.findViewById<TextView>(R.id.banner_title)?.text =
            context.getString(
                R.string.banner_title_format,
                SystemProperties.get("ro.orion.version", "")
            )

        holder.itemView.findViewById<TextView>(R.id.banner_subtitle)?.text =
            SystemProperties.get("ro.modversion", "")
    }
}