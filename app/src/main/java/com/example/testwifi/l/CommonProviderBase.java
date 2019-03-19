package com.example.testwifi.l;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class CommonProviderBase extends Binder implements CommonProvider {
    public static final String DESCRIPTOR = "com.example.testwifi.l.CommonProvider";
    public static final int ACTION_1 = IBinder.FIRST_CALL_TRANSACTION;

    public CommonProviderBase() {
        attachInterface(this, DESCRIPTOR);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }



    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
               return true;
            case ACTION_1:
                data.enforceInterface(DESCRIPTOR);
                String parm = data.readString();
                String jsonData = getDataByCommonFace(parm)+"解析完成";
                reply.writeNoException();
                reply.writeString(jsonData);
                return true;
        }

        return super.onTransact(code, data, reply, flags);
    }
}
