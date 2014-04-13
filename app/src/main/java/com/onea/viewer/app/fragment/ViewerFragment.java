package com.onea.viewer.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.onea.viewer.app.R;
import com.onea.viewer.app.data.ImagesStorage;
import com.onea.viewer.app.data.builtin.BuiltInMemoryImageLoader;
import com.onea.viewer.app.data.sd.SDMemoryImageLoader;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public final class ViewerFragment extends Fragment {

    private static final String LOG_TAG = ViewerFragment.class.getName();

    private Timer timer = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_viewer);

        final ImageSwitcher imageSwitcher =
                (ImageSwitcher) view.findViewById(R.id.viewer_fragment_image_switcher);
        final SharedPreferences sharedPreferences = getDefaultSharedPreferences();

        if (!sharedPreferences.contains(R.id.preference_animation_names)) {
            Toast.makeText(getSupportApplication(), "Select the type of animation!", Toast.LENGTH_LONG).show();
            return view;
        } else {
            final String animationName = sharedPreferences.
                    getString(R.id.preference_animation_names, "");
            if (animationName.equalsIgnoreCase("Fade-in/Fade-out")) {
                imageSwitcher.setInAnimation(
                        AnimationUtils.loadAnimation(getSupportApplication(), android.R.anim.fade_in));
                imageSwitcher.setOutAnimation(
                        AnimationUtils.loadAnimation(getSupportApplication(), android.R.anim.fade_out));
            }
            if (animationName.equalsIgnoreCase("Slide-in/Slide-out")) {
                imageSwitcher.setInAnimation(
                        AnimationUtils.loadAnimation(getSupportApplication(), android.R.anim.slide_in_left));
                imageSwitcher.setOutAnimation(
                        AnimationUtils.loadAnimation(getSupportApplication(), android.R.anim.slide_out_right));
            }
        }

        if (!sharedPreferences.contains(R.id.preference_destinations_image)) {
            Toast.makeText(getSupportApplication(), "Select the destination to images!", Toast.LENGTH_LONG).show();
            return view;
        } else {
            final String destinationAddress = sharedPreferences.
                    getString(R.id.preference_destinations_image, "");

            if (destinationAddress.equalsIgnoreCase("Built-in memory")
                    && !ImagesStorage.destinationImageLoader.equalsIgnoreCase("Built-in memory")) {
                ImagesStorage.imageLoader = new BuiltInMemoryImageLoader();
                ImagesStorage.destinationImageLoader = "Built-in memory";
            }
            if (destinationAddress.equalsIgnoreCase("SD")
                    && !ImagesStorage.destinationImageLoader.equalsIgnoreCase("SD")) {
                ImagesStorage.imageLoader = new SDMemoryImageLoader();
                ImagesStorage.destinationImageLoader = "SD";
            }
        }

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSupportActionBar().isShowing()) {
                    getSupportActionBar().hide();
                } else {
                    getSupportActionBar().show();
                }
            }
        });

        imageSwitcher.addView(createImageViewAndFill());
        imageSwitcher.setBackgroundColor(org.holoeverywhere.R.color.background_holo_dark);

        final Handler handler = new Handler();
        final int delay = sharedPreferences.getInt("preference_delay_seek_bar", 1) * 1000;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageSwitcher.addView(createImageViewAndFill());
                        imageSwitcher.showNext();
                        imageSwitcher.removeViewAt(0);
                    }
                });
            }
        }, delay, delay);

        return view;
    }

    public ImageView createImageViewAndFill() {
        final ImageView imageView = new ImageView(getSupportApplication());
        final String address = ImagesStorage.imageLoader.getNextImage().address.toString();
        ImageLoader.getInstance().displayImage(address, imageView);
        return imageView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setSubtitle("Viewer");
    }

}
