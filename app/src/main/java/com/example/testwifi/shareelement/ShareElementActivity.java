package com.example.testwifi.shareelement;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.testwifi.R;

public class ShareElementActivity extends AppCompatActivity {
    static final String ACTION_KEY="SHARE_NAME";
    private ImageView imageView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //1、打开FEATURE_CONTENT_TRANSITIONS开关(可选)，这个开关默认是打开的
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //2、设置除ShareElement外其它View的退出方式(左边滑出)
        getWindow().setExitTransition(new Slide(Gravity.LEFT));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_element);
        imageView = findViewById(R.id.activity_share_img_1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDetailsActivity();
            }
        });
//        setRe();
    }

    private void setRe() {
        ViewCompat.setTransitionName(imageView, "imgagview_1");
    }

    private void startDetailsActivity() {
        Intent intent = new Intent(this, ShareElementTwoActivity.class);
        intent.putExtra(ACTION_KEY,"imgagview_1");
        Pair<View, String> l = new Pair<>((View) imageView, "imgagview_1");
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, l);
        ActivityCompat.startActivityForResult(this,intent,10,optionsCompat.toBundle());
    }
}
