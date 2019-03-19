package com.example.testwifi.paint;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.testwifi.R;

public class PaintActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        webView = findViewById(R.id.test_web_view);
        //不缩放
        webView.setInitialScale(100);

        WebSettings webSettings = webView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        webSettings.setLoadWithOverviewMode(false);
        //保存表单数据
        webSettings.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        //启动应用缓存
        webSettings.setAppCacheEnabled(true);
        //设置缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //设置此属性，可任意比例缩放
        webSettings.setUseWideViewPort(false);
        //告诉WebView启用JavaScript执行。默认的是false。
        webSettings.setJavaScriptEnabled(true);
        //页面加载好以后，再放开图片
        webSettings.setBlockNetworkImage(false);
        //使用localStorage则必须打开
        webSettings.setDomStorageEnabled(true);
        //排版适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否新窗口打开(加了后可能打不开网页)
        webSettings.setSupportMultipleWindows(true);
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //设置字体默认缩放大小
        webSettings.setTextZoom(100);

        webView.setWebViewClient(new TWebClient());
        webView.setWebChromeClient(new TClient());
        webView.addJavascriptInterface(new JsObject(), "onClick");
        String url = "https://v.youku.com/v_show/id_XMzgzOTgyMzc4MA==.html?spm=a2hmv.20009921.posterMovieGrid86981.5~5~5~1~3!2~A&s=c6c62a475a5d4a14ab48";
//        url = "https://www.huya.com";
        url = "https://www.baidu.com";
        webView.loadUrl(url);
    }

    class TWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            view.loadUrl(url);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try{
                if(!url.startsWith("http://") || !url.startsWith("https://")){
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(intent);
                    return true;
                }
            }catch (Exception e){//防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
            }
            view.loadUrl(url);
            return true;
        }
    }

    class TClient extends WebChromeClient {
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
        // Android 使WebView支持HTML5 Video（全屏）播放的方法
        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            Log.e("11", "onHideCustomView...");
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            Log.e("11", "onShowCustomView...");
            callback.onCustomViewHidden();
        }

    }

    private class JsObject {
        @JavascriptInterface
        public void fullscreen() {
            Log.e("11", "fullscreen...");
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
