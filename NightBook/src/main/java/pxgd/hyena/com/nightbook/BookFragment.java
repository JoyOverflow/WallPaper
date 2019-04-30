package pxgd.hyena.com.nightbook;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 *
 */
public class BookFragment extends Fragment {
    public static final String BOOK_URL_KEY = "BOOK_URL_KEY";

    private WebView webView = null;
    public BookFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initData();
    }
    private void initUI(View rootview) {
        webView = rootview.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        String appCachePath = getActivity().getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAppCacheEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
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
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        getView().setFocusableInTouchMode(true);
        getView().setOnKeyListener( new View.OnKeyListener(){
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event ){
                if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
                    webView.goBack();

                    return true;
                }
                return false;
            }
        } );

    }
    private void initData() {
        Bundle bundle = getArguments();
        String book_url = bundle.getString(BOOK_URL_KEY);
        if (TextUtils.isEmpty(book_url)){
            return;
        }
        webView.loadUrl(book_url);
    }

    /**
     * 释放资源
     */
    @Override
    public void onDestroy() {
        webView.stopLoading();
        webView.destroy();
        super.onDestroy();
    }
}
