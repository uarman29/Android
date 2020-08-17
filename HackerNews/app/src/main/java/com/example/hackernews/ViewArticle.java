package com.example.hackernews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ViewArticle extends AppCompatActivity
{
    WebView viewArticleWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article);

        viewArticleWebView = (WebView) findViewById(R.id.webView);
        viewArticleWebView.getSettings().setJavaScriptEnabled(true);
        viewArticleWebView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        viewArticleWebView.loadUrl(intent.getStringExtra("articleID"));
    }
}