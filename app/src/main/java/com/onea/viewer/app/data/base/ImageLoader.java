package com.onea.viewer.app.data.base;

import com.onea.viewer.app.Viewer;

import java.util.ArrayList;

public abstract class ImageLoader implements IImageAddressLoader {

    private static final String LOG_TAG = ImageLoader.class.getName();

    public ImageLoader() {
        onCreate();
        loadImages();
    }

    @Override
    public void onCreate() {
        imageList = new ArrayList<Image>();
        Viewer.Log(LOG_TAG, "onCreate");
    }

    private int imageIndex = 0;

    protected ArrayList<Image> imageList;

    @Override
    public Image getNextImage() {
        if (imageIndex == getImageCount()) {
            imageIndex = 0;
        }
        return imageList.get(imageIndex++);
    }

    @Override
    public int getImageCount() {
        return imageList.size();
    }

    public abstract void loadImages();

}
