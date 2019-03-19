package com.example.testwifi.blue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import java.util.Set;

public class BlueUtils {
    private BluetoothAdapter mBluetoothAdapter;
    private Context mContext;

    public BlueUtils(Context mContext) {
        this.mContext = mContext;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        logMsg();
    }

    private void logMsg() {
        String name = mBluetoothAdapter.getName();
        String address = mBluetoothAdapter.getAddress();
        Log.e("11", "name..." + name + "..address.." + address);
        //获取已配对蓝牙设备
        Set<BluetoothDevice> list = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice b : list) {
            Log.e("11", "已配对蓝牙设备..name..." + b.getName() + "..address.." + b.getAddress());
        }
    }

    public boolean isOpen() {
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled();
    }

    public boolean startSearchDevice() {
        return mBluetoothAdapter != null && mBluetoothAdapter.startDiscovery();
    }

    public boolean cancelSearchDevice() {
        return mBluetoothAdapter != null && mBluetoothAdapter.cancelDiscovery();
    }
}
