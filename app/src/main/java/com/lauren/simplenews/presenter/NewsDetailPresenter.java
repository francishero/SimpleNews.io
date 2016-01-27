package com.lauren.simplenews.presenter;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lauren.simplenews.model.INewsModel;
import com.lauren.simplenews.model.NewsModelImpl;
import com.lauren.simplenews.view.INewsDetailView;
import com.whiskeyfei.mvp.base.BasePresenter;

public class NewsDetailPresenter extends BasePresenter<INewsDetailView> implements INewsDetailPresenter {
    WebView mWebView;
    private INewsModel mNewsModel;

    public NewsDetailPresenter(INewsDetailView detailView) {
        attachView(detailView);
        mNewsModel = new NewsModelImpl();
    }

    @Override
    public void loadUrl(String newUrl) {
        checkViewAttached();
        if (mWebView != null) {
            mWebView.loadUrl(newUrl);
        }
    }

    @Override
    public void init(WebView webView) {
        mWebView = webView;
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        mWebView.setWebViewClient(new myWebViewClient());
    }

    private class myWebViewClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (TextUtils.isEmpty(url)){
                return true;
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            getMvpBaseView().showLoadErrorMessage(description);
        }
    }

}