package com.grocery.fragment.fragmentMenu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.grocery.R;

/**
 * Created by ThanhBeo on 30/06/2017.
 */

public class FragmentAboutGrocery extends Fragment {

    private static final String URL_ABOUT_GROCERY = "https://www.google.com.vn/";
    WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_about_grocery, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        webView = view.findViewById(R.id.webview);
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);
        dialog.show();

        if (webView == null) return;
        webView.clearCache(true);
        webView.clearHistory();
        webView.setWebChromeClient(new WebChromeClient());
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                dialog.dismiss();
            }
        });
        webView.loadUrl(URL_ABOUT_GROCERY);
    }
}
