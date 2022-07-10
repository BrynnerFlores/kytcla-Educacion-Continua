package com.brynnerflores.kytcla;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

public class ActivityWebView extends AppCompatActivity {

    // region Variables

    private MaterialToolbar materialToolbar;
    private WebView webView;
    private WebSettings webSettings;

    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        final String url = getIntent().getStringExtra("URL");

        if (url.isEmpty() || url == null) {
            Toast.makeText(this, "No se pudo obtener la url.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            materialToolbar = findViewById(R.id.material_toolbar_web_view);
            setSupportActionBar(materialToolbar);
            materialToolbar.setNavigationOnClickListener(view -> finish());

            webView = findViewById(R.id.web_view);
            webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new CallBack());
            webView.loadUrl(url);
        }
    }

    private class CallBack extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }
}