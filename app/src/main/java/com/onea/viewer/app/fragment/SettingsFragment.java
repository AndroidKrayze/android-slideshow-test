package com.onea.viewer.app.fragment;

import android.os.Bundle;
import com.onea.viewer.app.R;
import org.holoeverywhere.preference.Preference;
import org.holoeverywhere.preference.Preference.OnPreferenceChangeListener;
import org.holoeverywhere.preference.PreferenceFragment;
import org.holoeverywhere.preference.SeekBarPreference;

public final class SettingsFragment extends PreferenceFragment {

    private static final String LOG_TAG = SettingsFragment.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        final SeekBarPreference seekBar =
                (SeekBarPreference) findPreference(R.id.preference_delay_seek_bar);
        final int value = getDefaultSharedPreferences().getInt(R.id.preference_delay_seek_bar, 25);
        seekBar.setTitle(String.format("Value: %d seconds", value));

        seekBar.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.valueOf(String.valueOf(newValue));
                preference.setTitle(String.format("Value: %d seconds", progress));
                return true;
            }
        });

        final int appStartTime = getDefaultSharedPreferences().getInt("app_start_time", 0);
        final int appStopTime = getDefaultSharedPreferences().getInt("app_stop_time", 0);

        findPreference(R.id.preference_app_start_time).setSummary(String.valueOf(appStartTime));
        if (appStopTime != 0) {
            findPreference(R.id.preference_app_stop_time).setSummary(String.valueOf(appStopTime));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setSubtitle("Settings");
    }

}
