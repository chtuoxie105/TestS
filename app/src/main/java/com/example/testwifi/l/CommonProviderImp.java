package com.example.testwifi.l;


import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class CommonProviderImp extends CommonProviderBase{

    @Override
    public String getDataByCommonFace(String s) {
        Log.e("11","CommonProviderImp..."+s);
        return s+"..result ..事件";
    }

    @Override
    public void setDataToMain(int type) {
        Log.e("11","CommonProviderImp..."+type);
    }
}
