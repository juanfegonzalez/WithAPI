package com.juanfe.withapi.controladoras;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.juanfe.withapi.R;
import com.juanfe.withapi.utils.Constantes;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.DOWNLOAD_SERVICE;

public class ControladoraWeb extends Fragment {
    private static final int FILECHOOSER_RESULCODE =1;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessages;
    private Uri mCapturedImageUri = null;
    WebView v_web;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.webview_fragment,container,false);
        v_web = v.findViewById(R.id.web);
        WebSettings webSettings = v_web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        v_web.loadUrl(Constantes.OCR);
        v_web.getSettings().setDomStorageEnabled(true);
        v_web.getSettings().setAllowFileAccess(true);
        v_web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        v_web.clearCache(false);
        v_web.setWebViewClient(new MyClient());
        v_web.setWebChromeClient(new GoogleClient());

        v_web.setWebChromeClient(new WebChromeClient(){
            //Implementacion de Canvas de archivos
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String accepType){
                mUploadMessage = uploadMsg;
                openPDFChooser();
            }
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams filechooserParams){
                mUploadMessages = filePathCallback;
                openPDFChooser();
                return true;
            }
            public void openFileChooser(ValueCallback<Uri> uploadMsg){
                openFileChooser(uploadMsg, "");
            }
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
                openFileChooser(uploadMsg, acceptType);
            }
            //Fin
        });
        //Metodo para descargar pdf
        v_web.setDownloadListener(new DownloadListener()
        {
            @Override
            public void onDownloadStart(String url, String userAgent,String contentDisposition, String mimeType,long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setMimeType(mimeType);
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Descargando pdf..."); //request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition,mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS,".pdf");
                DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(context, "Descargando pdf...",Toast.LENGTH_LONG).show();
            }});
        v_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_web.loadUrl("https://Mobile.xhtml");
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    private void openPDFChooser(){
        try{
            File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "FolderName");
            if (!imageStorageDir.exists()){
                imageStorageDir.mkdirs();
            }
            File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
            mCapturedImageUri = Uri.fromFile(file);
            final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageUri);
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            Intent chooserIntent = Intent.createChooser(i, "*/*");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});
            startActivityForResult(chooserIntent, FILECHOOSER_RESULCODE);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode==FILECHOOSER_RESULCODE){
            if (null == mUploadMessage && null == mUploadMessages){
                return;
            }
            if (null != mUploadMessage){
                handleUploadMessage(requestCode, resultCode, intent);
            } else if (mUploadMessages != null){
                handleUploadMessages(requestCode, resultCode, intent);
            }
        }
    }

    private void handleUploadMessage(int requestCode, int resultCode, Intent intent) {
        Uri result = null;
        try {
            if (resultCode != RESULT_OK){
                result = null;
            } else {
                result = intent == null ? mCapturedImageUri : intent.getData();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        mUploadMessage.onReceiveValue(result);
        mUploadMessage = null;
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void handleUploadMessages(int requestCode, int resultCode, Intent intent) {
        Uri[] results = null;
        try {
            if (resultCode != RESULT_OK){
                results = null;
            } else {
                if (intent != null){
                    String dataString = intent.getDataString();
                    ClipData clipData = intent.getClipData();
                    if (clipData != null){
                        results = new Uri[clipData.getItemCount()];
                        for (int i = 0; i < clipData.getItemCount(); i++){
                            ClipData.Item item = clipData.getItemAt(i);
                            results[i] = item.getUri();
                        }
                    }
                    if (dataString != null){
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                } else {
                    results = new Uri[]{mCapturedImageUri};
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        mUploadMessages.onReceiveValue(results);
        mUploadMessages = null;
    }
    class MyClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            super.onPageStarted(view,url,favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String Url)
        {
            view.loadUrl(Url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view,String url)
        {
            super.onPageFinished(view,url);
        }
    }
    class GoogleClient extends WebChromeClient
    {
        @Override
        public void onProgressChanged(WebView view,int newProgress)
        {
            super.onProgressChanged(view,newProgress);
        }
    }
}
