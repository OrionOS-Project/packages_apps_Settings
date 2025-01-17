/*
 * Copyright (C) 2025 OrionOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.deviceinfo.firmwareversion;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.PreferenceScreen;
import androidx.cardview.widget.CardView;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.widget.LayoutPreference;

public class OrionVersionHeaderController extends BasePreferenceController implements View.OnClickListener {

    private static final String KEY_ORION_VERSION = "ro.modversion";
    private static final String KEY_ORION_BUILD_TYPE = "ro.orion.build.type";

    private static final String KEY_DEVICE_MAINTAINER_NAME = "ro.orion.maintainer";
    private static final String KEY_DEVICE_MAINTAINER_LINK = "ro.orion.maintainer_link";

    private static final Uri MT_URI_DATA = Uri.parse(SystemProperties.get(KEY_DEVICE_MAINTAINER_LINK));

    private LayoutPreference mLayoutPreference;
    private TextView mOrionVersion, mMaintainerName;
    private CardView mOfficialVersion, mMaintainerSection;

    public OrionVersionHeaderController(Context context, String key) {
        super(context, key);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        mLayoutPreference = screen.findPreference(getPreferenceKey());
        mOrionVersion = mLayoutPreference.findViewById(R.id.orion_version);
        mMaintainerName = mLayoutPreference.findViewById(R.id.maintainer_name);
        mOfficialVersion = mLayoutPreference.findViewById(R.id.official_version_logo);
        mMaintainerSection = mLayoutPreference.findViewById(R.id.maintainer_section);

        String mOrionBuildType = SystemProperties.get(KEY_ORION_BUILD_TYPE);

        mOrionVersion.setText(SystemProperties.get(KEY_ORION_VERSION));
        mMaintainerName.setText(SystemProperties.get(KEY_DEVICE_MAINTAINER_NAME));

        mMaintainerSection.setOnClickListener(this);

        if ("official".equalsIgnoreCase(mOrionBuildType)) {
            mOfficialVersion.setVisibility(View.VISIBLE);
        } else {
            mOfficialVersion.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mMaintainerSection) {
            final Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(MT_URI_DATA);
            mContext.startActivity(intent);
        }
    }
}
