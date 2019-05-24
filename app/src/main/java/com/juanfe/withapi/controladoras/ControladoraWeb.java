package com.juanfe.withapi.controladoras;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.juanfe.withapi.R;
import com.juanfe.withapi.utils.Constantes;

public class ControladoraWeb extends Fragment {
    WebView v_web;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.webview_fragment,container,false);
        v_web = v.findViewById(R.id.web);
        WebSettings webSettings = v_web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        v_web.setWebViewClient(new WebViewClient());
        v_web.loadUrl(Constantes.OCR);

        return v;
    }

}
