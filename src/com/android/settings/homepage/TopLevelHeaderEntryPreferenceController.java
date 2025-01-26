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

package com.android.settings.homepage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.android.settingslib.widget.LayoutPreference;

import java.util.Map;
import java.util.HashMap;

public class TopLevelHeaderEntryPreferenceController extends BasePreferenceController {

    private static final String KEY_MENU_HEADER = "top_level_header_layout";

    public TopLevelHeaderEntryPreferenceController(Context context, String key) {
        super(context, key);
        
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);

        LayoutPreference menuHeader = (LayoutPreference) screen.findPreference(KEY_MENU_HEADER);
        if (menuHeader != null) {
            Map<Integer, Intent> clickMap = new HashMap<>();
            clickMap.put(R.id.homepage_molecular, new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$MolecularSettingsActivity")));
            clickMap.put(R.id.homepage_about, new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$MyDeviceInfoActivity")));

            for (Map.Entry<Integer, Intent> entry : clickMap.entrySet()) {
                View view = menuHeader.findViewById(entry.getKey());
                if (view != null) {
                    view.setOnClickListener(view -> mContext.startActivity(entry.getValue()));
                }
            }    
        }
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return KEY_MENU_HEADER;
    }
}