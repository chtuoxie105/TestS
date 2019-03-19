package com.example.testwifi.l;

import android.os.IInterface;

public interface CommonProvider extends IInterface{

    String getDataByCommonFace(String s);

    void  setDataToMain(int type);
}
