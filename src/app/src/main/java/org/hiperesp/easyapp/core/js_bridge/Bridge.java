package org.hiperesp.easyapp.core.js_bridge;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.hiperesp.easyapp.core.js_native_caller.IntentRequestCodeConstants;
import org.hiperesp.easyapp.core.EasyApp;
import org.hiperesp.easyapp.core.settings.Settings;

class Bridge implements IntentRequestCodeConstants {

    EasyApp easyApp;
    private WebView webView;

    String callbackCameraResolve = "";
    String callbackCameraReject = "";

    Bridge(EasyApp easyApp, WebView webView) {
        this.easyApp = easyApp;
        this.webView = webView;
    }

    private void sendScriptToWeb(String script) {
        webView.evaluateJavascript(script, null);
    }

    private int uniqueCallbackId = 0;
    @JavascriptInterface
    public int __getCallbackId(){
        return uniqueCallbackId++;
    }


    public void callbackCameraFunction(boolean success, String data){
        String callbackFunction;
        if(success) {
            callbackFunction = callbackCameraResolve;
        } else {
            callbackFunction = callbackCameraReject;
        }
        sendScriptToWeb("window."+ Settings.EASYAPP_NATIVE_INTERFACE+".__private.callback."+callbackFunction+"(\""+data+"\")");
    }
}
