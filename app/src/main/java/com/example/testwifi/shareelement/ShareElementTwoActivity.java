package com.example.testwifi.shareelement;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.widget.ImageView;

import com.example.testwifi.R;

public class ShareElementTwoActivity extends AppCompatActivity {
    private ImageView imageView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_element_two);
        imageView = findViewById(R.id.activity_share_two_img_1);

        //1、设置相同的TransitionName
        String name = getIntent().getStringExtra(ShareElementActivity.ACTION_KEY);
        ViewCompat.setTransitionName(imageView, name);
        //2、设置WindowTransition,除指定的ShareElement外，其它所有View都会执行这个Transition动画
        getWindow().setExitTransition(new Fade());
        getWindow().setEnterTransition(new Fade());
        // 3、设置ShareElementTransition,指定的ShareElement会执行这个Transiton动画
        TransitionSet transitionSet= new TransitionSet();
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeTransform());
        transitionSet.addTarget(imageView);
        getWindow().setSharedElementEnterTransition(transitionSet);
    }
}
