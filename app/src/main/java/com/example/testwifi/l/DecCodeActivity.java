package com.example.testwifi.l;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaCodecInfo;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.testwifi.R;
import com.example.testwifi.screenrecorder.AudioEncodeConfig;
import com.example.testwifi.screenrecorder.MainTestActivity;
import com.example.testwifi.screenrecorder.ScreenRecorder;
import com.example.testwifi.screenrecorder.Utils;
import com.example.testwifi.screenrecorder.VideoEncodeConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DecCodeActivity extends AppCompatActivity {

    private static final int REQUEST_MEDIA_PROJECTION = 1;
    private static final int REQUEST_PERMISSIONS = 2;
    private MediaProjectionManager mMediaProjectionManager;
    private ScreenRecorder mRecorder;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
        setContentView(R.layout.activity_dec_code);
        initView();
        init();
    }

    private void initView() {
        CheckBox cb = findViewById(R.id.test_dec_start_stop_btn);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
                    startActivityForResult(captureIntent, REQUEST_MEDIA_PROJECTION);
                } else {
                    stopDec();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startDec() {
        mRecorder.start();
        registerReceiver(mStopActionReceiver, new IntentFilter(MainTestActivity.ACTION_STOP));
        moveTaskToBack(true);

        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
    }

    private void stopDec() {
        if (mRecorder != null) {
            mRecorder.quit();
        }
        mRecorder = null;
        Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
        try {
            unregisterReceiver(mStopActionReceiver);
        } catch (Exception e) {
            //ignored
        }
    }
    private void cancelRecorder() {
        if (mRecorder == null) return;
        Toast.makeText(this, "Permission denied! Screen recorder is cancel", Toast.LENGTH_SHORT).show();
        stopDec();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        mMediaProjectionManager = (MediaProjectionManager) getApplicationContext().getSystemService(MEDIA_PROJECTION_SERVICE);
        initFindEncodersByTypeAsync();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            int granted = PackageManager.PERMISSION_GRANTED;
            for (int r : grantResults) {
                granted |= r;
            }
            if (granted == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "get Permission!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No Permission!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermissions() {
        final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
        boolean showRationale = false;
        for (int i = 0; i < permissions.length; i++) {
            showRationale = shouldShowRequestPermissionRationale(permissions[i]);
            if (!showRationale) {
                break;
            }
        }
        if (!showRationale) {
            requestPermissions(permissions, REQUEST_PERMISSIONS);
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            MediaProjection mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
            if (mediaProjection == null) {
                Log.e("@@", "media projection is null");
                return;
            }
            VideoEncodeConfig videoEncodeConfig =createVideoConfig();
            AudioEncodeConfig audioEncodeConfig =createAudioConfig();
            if (videoEncodeConfig == null) {
                Toast.makeText(this, "Create ScreenRecorder failure", Toast.LENGTH_SHORT).show();
                mediaProjection.stop();
                return;
            }
            File dir = getSavingDir();
            if (!dir.exists() && !dir.mkdirs()) {
                cancelRecorder();
                return;
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US);
            final File file = new File(dir, "Screen-" + format.format(new Date())
                    + "-" + videoEncodeConfig.width + "x" + videoEncodeConfig.height + ".mp4");
            mRecorder =  new ScreenRecorder(videoEncodeConfig, audioEncodeConfig,
                    1, mediaProjection, file.getAbsolutePath());
            mRecorder.setCallback(new ScreenRecorder.Callback() {
                long startTime = 0;
                @Override
                public void onStop(Throwable error) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            stopDec();
                        }
                    });
                    if (error != null) {
                        Toast.makeText(DecCodeActivity.this, "Recorder error ! See logcat for more details", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        file.delete();
                    } else {
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                                .addCategory(Intent.CATEGORY_DEFAULT)
                                .setData(Uri.fromFile(file));
                        sendBroadcast(intent);
                    }
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onRecording(long presentationTimeUs) {

                }
            });
            startDec();
        }
    }

    private void initFindEncodersByTypeAsync() {
        Utils.findEncodersByTypeAsync(MainTestActivity.VIDEO_AVC, new Utils.Callback() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResult(MediaCodecInfo[] infos) {
//                MainTestActivity.logCodecInfos(infos, MainTestActivity.VIDEO_AVC);
                String[] names = new String[infos.length];
                for (int i = 0; i < infos.length; i++) {
                    names[i] = infos[i].getName();
                }
                mCodecName = names[0];
            }
        });

        Utils.findEncodersByTypeAsync(MainTestActivity.VIDEO_AVC, new Utils.Callback() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResult(MediaCodecInfo[] infos) {
//                MainTestActivity.logCodecInfos(infos, MainTestActivity.AUDIO_AAC);
                String[] names = new String[infos.length];
                for (int i = 0; i < infos.length; i++) {
                    names[i] = infos[i].getName();
                }
                mAudioCodec = names[0];
            }
        });
    }

    private String mCodecName = "",mAudioCodec="";

    private VideoEncodeConfig createVideoConfig() {
        final String codec = mCodecName;
        if (TextUtils.isEmpty(codec)) {
            Log.e("11", "mCodecName...is.." + mCodecName);
            return null;
        }
        int[] selectedWithHeight =getSelectedWithHeight();
        int framerate =15;
        int iframe =1;
        int bitrate =800000;
        MediaCodecInfo.CodecProfileLevel profileLevel= Utils.toProfileLevel("Default");
        return new VideoEncodeConfig(selectedWithHeight[0],selectedWithHeight[1], bitrate,
                framerate, iframe, codec, MainTestActivity.VIDEO_AVC, profileLevel);
    }
    private AudioEncodeConfig createAudioConfig() {
        String codec = mAudioCodec;
        int bitrate= 510000;
        int samplerate = 44100;
        int channelCount = 1;
        int profile =  MediaCodecInfo.CodecProfileLevel.AACObjectMain;
        return new AudioEncodeConfig(codec,MainTestActivity.AUDIO_AAC, bitrate, samplerate, channelCount, profile);
    }
    private int[] getSelectedWithHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        int[] xes = new int[2];
        xes[0] = 1920;
        xes[1] = 1080;
        return xes;
    }
    private  File getSavingDir() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "ScreenCaptures");
    }

    private BroadcastReceiver mStopActionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            File file = new File(mRecorder.getSavedPath());
            if (MainTestActivity.ACTION_STOP.equals(intent.getAction())) {
                stopDec();
            }
            Toast.makeText(context, "Recorder stopped!\n Saved file " + file, Toast.LENGTH_LONG).show();
            StrictMode.VmPolicy vmPolicy = StrictMode.getVmPolicy();
            try {
                // disable detecting FileUriExposure on public file
                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
                viewResult(file);
            } finally {
                StrictMode.setVmPolicy(vmPolicy);
            }
        }

        private void viewResult(File file) {
            Intent view = new Intent(Intent.ACTION_VIEW);
            view.addCategory(Intent.CATEGORY_DEFAULT);
            view.setDataAndType(Uri.fromFile(file), MainTestActivity.VIDEO_AVC);
            view.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(view);
            } catch (ActivityNotFoundException e) {
                // no activity can open this video
            }
        }
    };
}
