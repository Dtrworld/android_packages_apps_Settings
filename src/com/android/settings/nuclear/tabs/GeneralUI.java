package com.android.settings.nuclear.tabs;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.PreferenceCategory;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;

import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.dashboard.DashboardSummary;
import com.android.settings.dashboard.SummaryLoader;

import com.android.settings.nuclear.Preferences.CustomSeekBarPreference;

public class GeneralUI extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "GeneralUI";

    private static final String KEY_DASHBOARD_PORTRAIT_COLUMNS = "dashboard_portrait_columns";
    private static final String KEY_DASHBOARD_LANDSCAPE_COLUMNS = "dashboard_landscape_columns";

    private CustomSeekBarPreference mDashboardPortraitColumns;
    private CustomSeekBarPreference mDashboardLandscapeColumns;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.generalui);      

        mDashboardPortraitColumns = (CustomSeekBarPreference) findPreference(KEY_DASHBOARD_PORTRAIT_COLUMNS);
        int columnsPortrait = Settings.System.getInt(resolver,
                Settings.System.DASHBOARD_PORTRAIT_COLUMNS, DashboardSummary.mNumColumns);
        mDashboardPortraitColumns.setValue(columnsPortrait / 1);
        mDashboardPortraitColumns.setOnPreferenceChangeListener(this);

        mDashboardLandscapeColumns = (CustomSeekBarPreference) findPreference(KEY_DASHBOARD_LANDSCAPE_COLUMNS);
        int columnsLandscape = Settings.System.getInt(resolver,
                Settings.System.DASHBOARD_LANDSCAPE_COLUMNS, 2);
        mDashboardLandscapeColumns.setValue(columnsLandscape / 1);
        mDashboardLandscapeColumns.setOnPreferenceChangeListener(this);
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.APPLICATION;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        final String key = preference.getKey();

        if (preference == mDashboardPortraitColumns) {
            int columnsPortrait = (Integer) objValue;
            Settings.System.putInt(getContentResolver(),
                    Settings.System.DASHBOARD_PORTRAIT_COLUMNS, columnsPortrait * 1);
            return true;
        }
        if (preference == mDashboardLandscapeColumns) {
            int columnsLandscape = (Integer) objValue;
            Settings.System.putInt(getContentResolver(),
                    Settings.System.DASHBOARD_LANDSCAPE_COLUMNS, columnsLandscape * 1);
            return true;
        }
        return true;
    }
} 
