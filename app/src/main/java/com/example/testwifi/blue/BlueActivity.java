package com.example.testwifi.blue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.testwifi.R;

public class BlueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);

    }

    private void regisRecever() {
        IntentFilter intentFilter = new IntentFilter();
        //发现设备
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        //设备连接状态改变
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        //蓝牙设备状态改变
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) return;
            String action = intent.getAction();
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    getBlueDevice(bluetoothDevice);
                    break;
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    break;
            }
        }
    };

    private void getBlueDevice(BluetoothDevice scanDevice) {
        if (scanDevice == null || TextUtils.isEmpty(scanDevice.getName())) return;
        Log.d("11", "name=" + scanDevice.getName() + "address=" + scanDevice.getAddress());
    }
}
