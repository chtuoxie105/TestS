package com.imagepickerlibrary;

import android.net.Uri;

import java.util.ArrayList;

public interface PhotoLoadListener {
    void onLoadComplete(ArrayList<Uri> photoUris);

    void onLoadError();
}
