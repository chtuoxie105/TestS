package com.example.testwifi.task;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.testwifi.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScrollActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ScrollAdapter scrollAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        recyclerView = findViewById(R.id.av_test_scroll_view);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        scrollAdapter = new ScrollAdapter(recyclerView);
        recyclerView.setAdapter(scrollAdapter);
        setInitData();
//        scrollAdapter.startScroll();
        findViewById(R.id.btn_test_retrofit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getRetrofit();
                fast();
            }
        });

        try {
            ExifInterface exifInterface = new ExifInterface("sdsa.png");
            String lon = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String lat = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String lonRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
            String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            exifInterface.hasThumbnail();

        } catch (IOException e) {
            e.printStackTrace();
        }
        EditText editText = null;
//        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());

        final ImageView imageView = findViewById(R.id.test_img_scale);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.img_scale);
        imageView.startAnimation(animation);
    }

    private void fast() {

        int[] arr = new int[]{2, 1, 5, 6, 3, 4, 9, 7};
        for (int i = 0; i < arr.length-1; i++) {
            int m = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] > arr[m]) {
                    m = j;
                }
            }

            int n = arr[m];
            arr[m] = arr[i];
            arr[i] = n;
        }

        for (int i = 0; i <arr.length ; i++) {
            Log.e("11","arr...:"+arr[i]);
        }
    }


    private void setInitData() {
        ArrayList<String> l = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            l.add("第--" + i + "--个item");
        }

        scrollAdapter.setList(l);
        scrollAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        scrollAdapter.onStop();
        super.onDestroy();
    }

    private void getRetrofit() {
        int a = 5, b = 3;
        int sb = (a &= b);
        Log.e("11", "sb.." + sb);

        String pro = System.getProperty("http.proxyHost");
        Log.e("11", "pro.,.." + pro);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(API.HOST)
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
        ITestRetrofit testRetrofit = retrofit.create(ITestRetrofit.class);
        Call<AboutVersionBean> callback = testRetrofit.getAbout();
        HttpUrl httpUrl = callback.request().url();
        callback.enqueue(new Callback<AboutVersionBean>() {
            @Override
            public void onResponse(Call<AboutVersionBean> call, Response<AboutVersionBean> response) {
                AboutVersionBean ab = response.body();
                Log.e("11", "bena.,.." + ab.toString());
            }

            @Override
            public void onFailure(Call<AboutVersionBean> call, Throwable t) {

            }
        });

    }
}
