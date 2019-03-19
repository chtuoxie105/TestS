package com.example.testwifi.l;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class CommonProxy implements CommonProvider {
    private IBinder binder;

    public CommonProxy(IBinder binder) {
        this.binder = binder;
    }

    public static CommonProvider asInterface(IBinder iBinder) {
        if (iBinder == null)
            return null;
        IInterface iInterface = iBinder.queryLocalInterface(CommonProviderBase.DESCRIPTOR);
        if (iInterface != null && iInterface instanceof CommonProviderBase) {
            return (CommonProvider) iInterface;
        }
        return new CommonProxy(iBinder);
    }

    @Override
    public String getDataByCommonFace(String s) {
        Log.e("11","CommonProxy..."+s);
        Parcel parcel = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        String result = "";

        try {
            parcel.writeInterfaceToken(CommonProviderBase.DESCRIPTOR);
            parcel.writeString(s);
            binder.transact(CommonProviderBase.ACTION_1, parcel, reply, 0);
            reply.readException();
            result = reply.readString();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            parcel.recycle();
            reply.recycle();
        }
        return result;
    }

    @Override
    public void setDataToMain(int type) {
        Log.e("11","CommonProxy..."+type);
    }

    @Override
    public IBinder asBinder() {
        return binder;
    }
}
