package com.onea.viewer.app.activity;

import android.os.Bundle;
import com.onea.viewer.app.R;
import com.onea.viewer.app.Viewer;
import com.onea.viewer.app.fragment.SettingsFragment;
import com.onea.viewer.app.fragment.ViewerFragment;
import org.holoeverywhere.addon.AddonSlider;
import org.holoeverywhere.addon.Addons;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.slider.SliderMenu;

import java.util.Date;

@Addons(AddonSlider.class)
public final class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getName();

    public AddonSlider.AddonSliderA addonSlider() {
        return addon(AddonSlider.class);
    }

    @Override
    protected void onStop() {
        getDefaultSharedPreferences().
                edit().
                putLong("app_stop_time", new Date().getTime()).
                commit();
        Viewer.Log(LOG_TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        getDefaultSharedPreferences().
                edit().
                putLong("app_stop_time", new Date().getTime()).
                commit();
        Viewer.Log(LOG_TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SliderMenu sliderMenu = addonSlider().obtainDefaultSliderMenu();

        getSupportActionBar().setTitle("Viewer");

        sliderMenu.add("Viewer", ViewerFragment.class, SliderMenu.BLUE);
        sliderMenu.add("Settings", SettingsFragment.class, SliderMenu.ORANGE);

        final SharedPreferences sharedPreferences = getDefaultSharedPreferences();

        sharedPreferences.
                edit().
                putLong("app_start_time", new Date().getTime()).
                commit();
    }

}
