package com.orion.settings.about;

import android.content.Context;
import android.os.SystemProperties;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

public class BrandBannerPreference extends Preference {

    public BrandBannerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSelectable(false);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        TextView title = (TextView) holder.findViewById(R.id.banner_title);
        TextView subtitle = (TextView) holder.findViewById(R.id.banner_subtitle);

        String version = SystemProperties.get("ro.orion.version", "");
        String codename = SystemProperties.get("ro.modversion", "");

        if (title != null) {
            title.setText(getContext().getString(R.string.banner_title_format, version));
        }
        if (subtitle != null) {
            subtitle.setText(codename);
        }
    }
}