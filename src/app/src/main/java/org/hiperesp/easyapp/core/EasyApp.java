package org.hiperesp.easyapp.core;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.hiperesp.easyapp.MainActivity;
import org.hiperesp.easyapp.core.settings.Settings;
import org.hiperesp.easyapp.core.webview_client.WebViewChromeClient;
import org.hiperesp.easyapp.core.js_bridge.BridgeWebInterface;
import org.hiperesp.easyapp.core.js_native_caller.NativeCaller;
import org.hiperesp.easyapp.core.webview_client.WebViewNativeClient;

public class EasyApp {

    public NativeCaller nativeCaller;
    private MainActivity activity;
    private WebView webView;
    private WebViewNativeClient webViewNativeClient = new WebViewNativeClient();
    private WebViewChromeClient webViewChromeClient = new WebViewChromeClient();
    private WebSettings webSettings;
    private BridgeWebInterface bridgeWebInterface;

    public EasyApp(MainActivity activity, WebView webView) {
        this.activity = activity;
        this.webView = webView;
        configure();
        start();
    }
    private void configure(){
        webSettings = webView.getSettings();
        enableHardwareAcceleration();
        setChromeClient();
        setWebViewClient();
        enableDebugging();
        enableFeatures();
        addJavascriptInterface();
    }
    private void enableHardwareAcceleration(){
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }
    private void setChromeClient(){
        webView.setWebChromeClient(webViewChromeClient);
    }
    private void setWebViewClient(){
        webView.setWebViewClient(webViewNativeClient);
    }
    private void addJavascriptInterface(){
        bridgeWebInterface = new BridgeWebInterface(this, webView);
        webView.addJavascriptInterface(bridgeWebInterface, Settings.EASYAPP_NATIVE_INTERFACE);
        nativeCaller = new NativeCaller(activity, bridgeWebInterface);
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void enableFeatures(){
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
    }
    private void enableDebugging() {
        WebView.setWebContentsDebuggingEnabled(true);
    }
    private void start(){
        webView.loadUrl(Settings.indexPath);
    }

    public boolean onBackPressed(){
        if(Settings.backButtonActionToWebView) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return false;
    }
}
