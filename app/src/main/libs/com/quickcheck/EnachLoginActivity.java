package com.quickcheck;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class EnachLoginActivity extends AppCompatActivity {

    private WebView webView;
    private String mandateId;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_enach_login);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);
        mandateId = getIntent().getStringExtra("madateId");
        // https://app.zipsign.in/
        String url = "http://192.168.222.2:801/PostRequest.aspx?MandateId=";
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url+mandateId));
//        startActivity(browserIntent);
        webView.loadUrl(url + mandateId);
        initWebView();

    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }


    }

    private void initWebView() {
        webView.setWebChromeClient(new MyWebChromeClient(this));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                invalidateOptionsMenu();
                Log.d("page", "page started");

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                Log.d("page", "page Loading");

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("page", "page finished");
                Log.d("url", url);

                String charSequence = "Close.aspx";
                if (url.toString().contains(charSequence)) {
                    HomeActivity.callServiceFromEnach(mandateId);
                    finish();
                    overridePendingTransition(0, 0);
                }

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.d("page", "page errror");

            }
        });
        webView.clearCache(true);
        webView.clearHistory();
        // webView.getSettings().setAppCacheEnabled(false);
        //   webView.getSettings().setBlockNetworkLoads(true);
// webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setUserAgentString(null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

    }

    @Override
// Detect when the back button is pressed
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            finish();
            overridePendingTransition(0, 0);
        } else {
            // Let the system handle the back button
            super.onBackPressed();
            overridePendingTransition(0, 0);

        }
    }
}
