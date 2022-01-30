package com.teamnequit.Activities;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.teamnequit.databinding.ActivityKPIBinding;

public class KPIActivity extends AppCompatActivity {

    ActivityKPIBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKPIBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().setTitle("KPI ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setDisplayZoomControls(false);

        String url = "https://firebasestorage.googleapis.com/v0/b/team-nequit.appspot.com/o/kpi.pdf?alt=media&token=685bfe89-e513-4622-9f08-be7a4d21234a";

        binding.webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);

        binding.webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
//                progressDialog.dismiss();

            }
        });


    }
}