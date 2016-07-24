package com.wl.util;

import android.os.Environment;

import com.wl.constant.Config;

import java.io.File;

public class FileUtils {
    /**
     * sdk/xytg
     *
     * @return
     */
    public static String getAppPath() {
        String appPath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            appPath = Environment.getExternalStorageDirectory() + File.separator + Config.ROOT_CACHE;
        } else {
            appPath = Environment.getDataDirectory() + File.separator + Config.SYSTEM_CACHE;
        }
        File file = new File(appPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return appPath;
    }

    /**
     * sdk/xytg/image
     *
     * @return
     */
    public static File getImageCache() {

        String imageCache = getAppPath() + File.separator + Config.IMAGE_CACHE;
        File imageFile = new File(imageCache);
        if (!imageFile.exists()) {
            imageFile.mkdirs();
        }
        return  imageFile;

    }
}
