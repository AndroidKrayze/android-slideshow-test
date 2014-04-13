package com.onea.viewer.app.data.base;

interface IImageAddressLoader {
    public void onCreate();
    public Image getNextImage();
    public int getImageCount();
    public void loadImages();
}