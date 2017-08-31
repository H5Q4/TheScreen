package com.github.jupittar.thescreen.screen.settings;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.github.jupittar.commlib.rxjava.rxbus.RxBus;
import com.github.jupittar.commlib.util.AppUtils;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.util.Constants;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragment {

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener
            = (preference, value) -> {
        String stringValue = value.toString();

        boolean isFirstShow;

        if (preference instanceof ListPreference) {
            isFirstShow = TextUtils.isEmpty(preference.getSummary());
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list.
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);

            // Set the summary to reflect the new value.
            preference.setSummary(
                    index >= 0
                            ? listPreference.getEntries()[index]
                            : null);

            if (preference.getKey().equals(preference.getContext().getString(R.string.pref_key_region))
                    && !isFirstShow) {
                Locale locale = Locale.getDefault();
                if (locale.getCountry().equals(stringValue)) {
                    return true;
                }
                switch (stringValue) {
                    case "US":
                        locale = Locale.US;
                        break;
                    case "CN":
                        locale = Locale.CHINA;
                        break;

                }
                AppUtils.initAppLanguage(preference.getContext(), locale);
                RxBus.getDefault().publish(Constants.EVENT_TAG_REGION_CHANGED, "");
            }

        } else {
            // For all other preferences, set the summary to the value's
            // simple string representation.
            preference.setSummary(stringValue);
        }

        return true;
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);

        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_region)));
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }
}
