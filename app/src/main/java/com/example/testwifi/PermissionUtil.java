package com.example.testwifi;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {
    private Activity activity;
    private Fragment fragment;
    private List<String> mPermissionList = new ArrayList<>();
    private static final int mRequestCode = 10;
    private PermissionCallback permissionCallback;

    public PermissionUtil(Activity activity) {
        this.activity = activity;
    }

    public void PermissionUtil(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setPermissionCallback(PermissionCallback permissionCallback) {
        this.permissionCallback = permissionCallback;
    }

    public boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void add(String permission) {
        mPermissionList.add(permission);
    }

    public void add(List<String> l) {
        mPermissionList.addAll(l);
    }

    private Context getContext() {
        if (activity != null) return activity;
        if (fragment != null) return fragment.getContext();
        return null;
    }

    private void clean() {
        mPermissionList.clear();
    }

    private boolean checkAllPermission() {
        for (int i = 0; i < mPermissionList.size(); i++) {
            boolean temp = ActivityCompat.shouldShowRequestPermissionRationale(activity, mPermissionList.get(i));
            Log.e("11", "temmp.." + temp);
        }
        for (String per : mPermissionList) {
            if (!checkPermission(per)) {
                return false;
            }
        }
        return true;
    }

    public void requestPermission(PermissionCallback permissionCallback) {
        this.permissionCallback = permissionCallback;
        if (mPermissionList.size() == 0) return;
        if (checkAllPermission()) {
            if (permissionCallback != null) {
                permissionCallback.onPermissionAllow();
            }
        } else {
            String[] array = makeRequestPermissionArray();
            if (activity != null) {
                ActivityCompat.requestPermissions(activity, array, mRequestCode);
            } else {
                fragment.requestPermissions(array, mRequestCode);
            }
        }
    }

    private boolean showRequestPermissionRationale() {
        for (String permission : mPermissionList) {
            if (activity != null) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {

                }
            } else {
                if (fragment.shouldShowRequestPermissionRationale(permission)) {

                }
            }
        }
        return true;
    }

    private String[] makeRequestPermissionArray() {
        String[] array = null;
        if (mPermissionList != null) {
            array = new String[mPermissionList.size()];
            mPermissionList.toArray(array);
        }
        return array;
    }

    private List<String> verifyPermission(String[] permissions, int[] grantResults) {
        List<String> denidList = new ArrayList<>();
        if (grantResults.length == 0) {
            return denidList;
        }
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                denidList.add(permissions[i]);
            }
        }
        return denidList;
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        if (requestCode == mRequestCode) {
            for (int i = 0; i < permissions.length; i++) {
                boolean temp = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i]);
                Log.e("11", "temmp.." + temp);
            }

            List<String> denidList = verifyPermission(permissions, grantResults);
            if (denidList.size() == 0) {
                //全部给了
                if (permissionCallback != null) {
                    permissionCallback.onPermissionAllow();
                }
            } else {
                //有的没给
                if (permissionCallback != null) {
                    permissionCallback.onPermissionDenied(denidList);
                }
            }
        }
    }

    public interface PermissionCallback {
        void onPermissionAllow();

        void onPermissionDenied(List<String> deniedList);
    }
}
