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

package com.android.settings.deviceinfo.aboutphone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.widget.LayoutPreference;

import com.orion.support.utils.DeviceUtils;

public class AboutPhoneHeaderController extends BasePreferenceController {
    
    private static final String KEY_DEVICE_CODENAME = "ro.product.device";
    private static final String KEY_DEVICE_BRAND = "ro.product.manufacturer";
    
    private static final String KEY_DEVICE_CHIPSET = "ro.board.platform";

    private LayoutPreference mLayoutPreference;
    private TextView mDeviceCodename, mDeviceBrand, mDeviceChipset, mDeviceRam;

    public AboutPhoneHeaderController(Context context, String key) {
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
        mDeviceCodename = mLayoutPreference.findViewById(mContext.getResources().getIdentifier("id/device_codename", null, mContext.getPackageName()));;
        mDeviceBrand = mLayoutPreference.findViewById(mContext.getResources().getIdentifier("id/device_brand", null, mContext.getPackageName()));;
        mDeviceChipset = mLayoutPreference.findViewById(mContext.getResources().getIdentifier("id/device_chipset", null, mContext.getPackageName()));;
        mDeviceRam = mLayoutPreference.findViewById(mContext.getResources().getIdentifier("id/device_ram", null, mContext.getPackageName()));;

        String deviceCodename = SystemProperties.get(KEY_DEVICE_CODENAME);
        mDeviceCodename.setText(deviceCodename != null ? deviceCodename : "Unknown");

        String deviceBrand = SystemProperties.get(KEY_DEVICE_BRAND);
        mDeviceBrand.setText(deviceBrand != null ? deviceBrand : "Unknown");

        String deviceChipset = SystemProperties.get(KEY_DEVICE_CHIPSET);
        mDeviceChipset.setText(deviceChipset != null ? deviceChipset : "Unknown");

        String deviceRam = DeviceUtils.getMemoryInfo();
        mDeviceRam.setText(deviceRam != null ? deviceRam : "Unknown");
    }
}
