package com.android.settings.deviceinfo.firmwareversion;

import android.content.Context;
import android.os.SystemProperties;
import android.text.TextUtils;

import androidx.annotation.VisibleForTesting;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

public class OrionVersionPreferenceController extends BasePreferenceController {

    @VisibleForTesting
    static final String ORION_VERSION_PROPERTY = "ro.modversion";
    private static final String ORION_BASE_VERSION_PROPERTY = "ro.orion.version";
    private static final String ORION_STATUS_TYPE_PROPERTY = "ro.orion.build.status";

    public OrionVersionPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        String modVersion = SystemProperties.get(ORION_VERSION_PROPERTY);
        return !TextUtils.isEmpty(modVersion) ? AVAILABLE : UNSUPPORTED_ON_DEVICE;
    }

    @Override
    public CharSequence getSummary() {
        String baseVersion = SystemProperties.get(ORION_BASE_VERSION_PROPERTY, "");
        String modVersion = SystemProperties.get(ORION_VERSION_PROPERTY, "");
        String statusType = SystemProperties.get(ORION_STATUS_TYPE_PROPERTY, "");

        if (TextUtils.isEmpty(baseVersion) && TextUtils.isEmpty(modVersion) && TextUtils.isEmpty(statusType)) {
            return mContext.getString(R.string.device_info_default);
        }

        StringBuilder summary = new StringBuilder();

        if (!TextUtils.isEmpty(baseVersion)) {
            summary.append(baseVersion);
        }

        if (!TextUtils.isEmpty(modVersion)) {
            if (summary.length() > 0) summary.append(" | ");
            summary.append(modVersion);
        }

        if (!TextUtils.isEmpty(statusType)) {
            if (summary.length() > 0) summary.append(" | ");
            summary.append(statusType.toUpperCase());
        }

        return summary.toString();
    }
}
