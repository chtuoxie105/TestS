package com.imagepickerlibrary.imageloader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.imagepickerlibrary.ImageLoader;
import com.imagepickerlibrary.R;

/**
 * Created by Martin on 2017/1/18.
 */

public class GlideImageLoader implements ImageLoader {
    private Context context;

    public GlideImageLoader(Context context) {
        this.context = context;
    }

    @Override
    public void bindImage(ImageView imageView, Uri uri, int width, int height) {
        Glide.with(context).load(uri).placeholder(R.drawable.bg_default_placeholder)
                .error(R.drawable.bg_default_error).override(width, height).dontAnimate().into(imageView);
    }

    @Override
    public void bindImage(ImageView imageView, Uri uri) {
        Glide.with(context).load(uri).placeholder(R.drawable.bg_default_placeholder)
                .error(R.drawable.bg_default_error).dontAnimate().into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        this.context = context;
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public ImageView createFakeImageView(Context context) {
        return new ImageView(context);
    }
}
