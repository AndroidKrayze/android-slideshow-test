package com.onea.viewer.app;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.utils.StorageUtils;
import org.holoeverywhere.HoloEverywhere;
import org.holoeverywhere.HoloEverywhere.PreferenceImpl;
import org.holoeverywhere.ThemeManager;

import java.io.File;

public final class Application extends org.holoeverywhere.app.Application {

    private static final String LOG_TAG = Application.class.getName();

    static {
        HoloEverywhere.DEBUG = false;

        HoloEverywhere.PREFERENCE_IMPL = PreferenceImpl.JSON;

        Viewer.DEBUG = true;

        ThemeManager.setDefaultTheme(ThemeManager.MIXED);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final File cacheDir = StorageUtils.getOwnCacheDirectory(getSupportApplication(), ".cache");
        final ImageLoaderConfiguration configuration = new Builder(getSupportApplication())
                .denyCacheImageMultipleSizesInMemory() // ban on storing multiple copies of the image
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
