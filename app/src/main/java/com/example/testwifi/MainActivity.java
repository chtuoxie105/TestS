package com.example.testwifi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "WIFII_TEST";
    private WifiManager mWidiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkSelfPermission();
        findViewById(R.id.start_btn).setOnClickListener(this);
        mWidiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        checkCurWifiStatus();
    }

    private void checkSelfPermission() {
        String[] list = new String[4];
        list[0] = Manifest.permission.ACCESS_WIFI_STATE;
        list[1] = Manifest.permission.CHANGE_WIFI_STATE;
        list[2] = Manifest.permission.CHANGE_WIFI_MULTICAST_STATE;
        list[3] = Manifest.permission.ACCESS_FINE_LOCATION;
        ActivityCompat.requestPermissions(this, list, 20);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "授权成功" + permissions[i]);
            } else {
                Log.e(TAG, "授权失败" + permissions[i]);
            }
        }
    }

    private void checkCurWifiStatus() {
        switch (mWidiManager.getWifiState()) {
            case 0:
                Log.e(TAG, "Wifi正在关闭");
                break;
            case 1:
                Log.e(TAG, "Wifi已经关闭");
                break;
            case 2:
                Log.e(TAG, "Wifi正在开启");
                break;
            case 3:
                Log.e(TAG, "Wifi已经开启");
                break;
            case 4:
                Log.e(TAG, "没有获取到WiFi状态");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.start_btn) {
//            logNetWork();
            logScan();
        }
    }

    private void logNetWork() {
        List<WifiConfiguration> l = mWidiManager.getConfiguredNetworks();
        for (int i = 0; i < l.size(); i++) {
            WifiConfiguration w = l.get(i);
            Log.e("11", "ww.." + w.toString());
        }

    }

    private void logScan() {
        List<ScanResult> l = mWidiManager.getScanResults();
        for (int i = 0; i < l.size(); i++) {
            ScanResult s= l.get(i);
            Log.e("11", "ww.." + s.toString());
        }
    }
}
