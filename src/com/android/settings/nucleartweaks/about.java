package com.android.settings.nucleartweaks;

import android.app.Activity;
import android.app.ActivityManagerNative;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import com.android.internal.logging.MetricsLogger;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.IWindowManager;
import android.view.Display;
import android.view.Window;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.R;
import com.android.settings.Utils;
    
public class about extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
            
public static final String TAG = "about";
    
private static final String NS_ROM_SHARE = "share";
    
    Preference mSourceUrl;
    Preference mDonateUrl;
    Preference mSourcebaseUrl;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.nuclear_about);
        PreferenceScreen prefSet = getPreferenceScreen();
        ContentResolver resolver = getContentResolver();
        mSourceUrl = findPreference("ns_source");
        mDonateUrl = findPreference("ns_donate");
        mSourcebaseUrl = findPreference("ns_sourcebase");
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        return false;
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference == mSourceUrl) {
            launchUrl("https://github.com/OneRomOne");
        } else if (preference == mDonateUrl) {
            launchUrl("http://paypal.me/TeamNuclear");
        } else if (preference == mSourcebaseUrl) {
            launchUrl("https://github.com/CyanogenMod");
        }  else if (preference.getKey().equals(NS_ROM_SHARE)) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            //intent.putExtra(Intent.EXTRA_TEXT, String.format(
            //     getActivity().getString(R.string.share_message)));
            //startActivity(Intent.createChooser(intent, getActivity().getString(R.string.share_chooser_title)));
        }  else {
            // If not handled, let preferences handle it.
            return super.onPreferenceTreeClick(preference);
    }
         return true; 
    }
    private void launchUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent donate = new Intent(Intent.ACTION_VIEW, uriUrl);
        getActivity().startActivity(donate);
    }
   
   @Override
   protected int getMetricsCategory() {
   return 1;
   }
}