package com.example.testwifi.l;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.testwifi.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class ClientActivity extends AppCompatActivity implements ServiceConnection {
    private CommonProvider commonProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(getPackageName(), PluginProviderService.class.getName()));
        bindService(intent, this, BIND_AUTO_CREATE);
        findViewById(R.id.btn_send_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonProvider != null) {
                    commonProvider.getDataByCommonFace("123456");
                    commonProvider.setDataToMain(123);
                }
            }
        });


    }

    private DexClassLoader readDexFile(Context context, String apkPath, String outDexPath) {
        DexClassLoader dexClassLoader = null;
        dexClassLoader = new DexClassLoader(apkPath, outDexPath,
                context.getApplicationInfo().nativeLibraryDir, context.getClassLoader());
        return dexClassLoader;
    }

    public Resources readApkRes(Context context, String apkPath) {
        Resources resources1 = null;
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, apkPath);
            Resources resources = context.getResources();
            resources1= new Resources(assetManager,resources.getDisplayMetrics(),resources.getConfiguration());

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return resources1;
    }

    @Override
    protected void onDestroy() {
        unbindService(this);
        super.onDestroy();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        commonProvider = CommonProxy.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        commonProvider = null;
    }
}
