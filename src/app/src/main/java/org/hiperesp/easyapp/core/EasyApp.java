package org.hiperesp.easyapp.core;

import android.annotation.SuppressLint;
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
        setChromeClient();
        setWebViewClient();
        enableDebugging();
        enableJavascript();
        addJavascriptInterface();
    }
    private void setChromeClient(){
        webView.setWebChromeClient(webViewChromeClient);
    }
    private void setWebViewClient(){
        webView.setWebViewClient(webViewNativeClient);
    }
    private void addJavascriptInterface(){
        bridgeWebInterface = new BridgeWebInterface(this, webView);
        webView.addJavascriptInterface(bridgeWebInterface, Settings.easyAppNativeInterface);
        setPermissionManager();
    }
    private void setPermissionManager(){
        nativeCaller = new NativeCaller(activity, bridgeWebInterface);
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void enableJavascript(){
        webSettings.setJavaScriptEnabled(true);
    }
    private void enableDebugging() {
        WebView.setWebContentsDebuggingEnabled(true);
    }
    private void start(){
        webView.loadUrl(Settings.indexPath);
    }

}
