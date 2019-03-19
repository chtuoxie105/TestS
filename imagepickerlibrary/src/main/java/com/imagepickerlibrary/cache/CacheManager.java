package com.imagepickerlibrary.cache;

import android.content.Context;

import com.imagepickerlibrary.util.SystemUtil;

import java.io.File;

/**
 * Created by Martin on 2017/1/17.
 */
public class CacheManager {

    public static final String ROOT_STORE = "PickerSample";
    private static CacheManager instance;
    private InnerCache imageInnerCache;
    private LocalCache imageCache;
    private Context context;

    private CacheManager(Context context) {
        this.context = context;
        init();
    }

    public static synchronized CacheManager getInstance(Context context) {
        if (instance == null) {
            instance = new CacheManager(context);
        }
        return instance;
    }


    private void init() {
        initRootCache(SystemUtil.getStoreDir(context));
    }

    private boolean initRootCache(File storage) {
        File cacheRoot = new File(storage, ROOT_STORE);
        boolean isMkRoot = true;
        if (!cacheRoot.exists()) {
            isMkRoot = cacheRoot.mkdirs();
        } else if (!cacheRoot.isDirectory()) {
            isMkRoot = false;
        }
        if (!isMkRoot) {
            return false;
        }
        return true;
    }

    public InnerCache getImageInnerCache() {
        if (imageInnerCache == null) {
            imageInnerCache = new InnerCache(context);

        }
        return imageInnerCache;
    }

    public LocalCache getImageCache() {
        if (imageCache == null) {
            imageCache = new LocalCache("Image", context);
        }
        return imageCache;
    }
}
