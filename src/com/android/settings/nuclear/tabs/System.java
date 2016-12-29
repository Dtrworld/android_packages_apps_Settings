package com.android.settings.nuclear.tabs;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v7.preference.Preference.OnPreferenceClickListener;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.PreferenceScreen;

import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class System extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "System";
    private static final String SHOW_CPU_INFO_KEY = "show_cpu_info";

    private SwitchPreference mShowCpuInfo;

    private final ArrayList<Preference> mAllPrefs = new ArrayList<Preference>();

    private final ArrayList<SwitchPreference> mResetSwitchPrefs
            = new ArrayList<SwitchPreference>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.system);

        mShowCpuInfo = findAndInitSwitchPref(SHOW_CPU_INFO_KEY);
    }

    private SwitchPreference findAndInitSwitchPref(String key) {
        SwitchPreference pref = (SwitchPreference) findPreference(key);
        if (pref == null) {
            throw new IllegalArgumentException("Cannot find preference with key = " + key);
        }
        mAllPrefs.add(pref);
        mResetSwitchPrefs.add(pref);
        return pref;
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

    void updateSwitchPreference(SwitchPreference switchPreference, boolean value) {
        switchPreference.setChecked(value);
    }

    private void updateAllOptions() {
        final Context context = getActivity();
        final ContentResolver cr = context.getContentResolver();
            updateCpuInfoOptions();
  }

    private void updateCpuInfoOptions() {
        updateSwitchPreference(mShowCpuInfo, Settings.Global.getInt(getActivity().getContentResolver(),
                Settings.Global.SHOW_CPU, 0) != 0);
    }

    private void writeCpuInfoOptions() {
        boolean value = mShowCpuInfo.isChecked();
        Settings.Global.putInt(getActivity().getContentResolver(),
                Settings.Global.SHOW_CPU, value ? 1 : 0);
        Intent service = (new Intent())
                .setClassName("com.android.systemui", "com.android.systemui.CPUInfoService");
        if (value) {
            getActivity().startService(service);
        } else {
            getActivity().stopService(service);
        }
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        final String key = preference.getKey();
        return true;
    }

   @Override
    public boolean onPreferenceTreeClick(Preference preference) {
       if (preference == mShowCpuInfo) {
            writeCpuInfoOptions();

        } else {
            return super.onPreferenceTreeClick(preference);
        }

        return false;
    }


}
