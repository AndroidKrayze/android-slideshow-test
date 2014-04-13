package com.onea.viewer.app.data.builtin;

import android.net.Uri;
import android.os.Environment;
import com.onea.viewer.app.data.base.Image;
import com.onea.viewer.app.data.base.ImageLoader;

import java.io.File;
import java.util.ArrayList;

public final class BuiltInMemoryImageLoader extends ImageLoader {

    private static final String LOG_TAG = BuiltInMemoryImageLoader.class.getName();

    @Override
    public void loadImages() {
        final File directory = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DCIM);
        if (directory.exists()) {
            imageList.addAll(listFilesWithSubFolders(directory));
        }
    }

    public ArrayList<Image> listFilesWithSubFolders(File dir) {
        final ArrayList<Image> files = new ArrayList<Image>();
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                files.addAll(listFilesWithSubFolders(file));
            } else {
                final Image image = new Image();
                image.address = Uri.fromFile(file);
                files.add(image);
            }
        }
        return files;
    }

}
