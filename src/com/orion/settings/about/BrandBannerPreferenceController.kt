package com.orion.settings.about;

import android.content.Context;

import com.android.settings.core.BasePreferenceController;

public class BrandBannerPreferenceController extends BasePreferenceController {

    public BrandBannerPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }
}