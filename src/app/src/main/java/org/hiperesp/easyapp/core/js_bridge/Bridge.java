package org.hiperesp.easyapp.core.js_bridge;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.hiperesp.easyapp.core.js_native_caller.IntentRequestCodeConstants;
import org.hiperesp.easyapp.core.EasyApp;
import org.hiperesp.easyapp.core.settings.Settings;

class Bridge implements IntentRequestCodeConstants {

    EasyApp easyApp;
    private WebView webView;

    Bridge(EasyApp easyApp, WebView webView) {
        this.easyApp = easyApp;
        this.webView = webView;
    }

    private int uniqueCallbackId = 0;
    @JavascriptInterface
    public int __getCallbackId(){
        return uniqueCallbackId++;
    }

    private void sendScriptToWeb(String script) {
        webView.evaluateJavascript(script, null);
    }


    String callbackCameraResolve = "";
    String callbackCameraReject = "";
    public void callbackCameraFunction(boolean success, String data, int response){
        String callbackCall;
        if(success) {
            callbackCall = callbackCameraResolve+"(\""+data+"\");";
        } else {
            callbackCall = callbackCameraReject+"("+response+");";
        }
        sendScriptToWeb("window."+ Settings.EASYAPP_NATIVE_INTERFACE+".__private.callback."+callbackCall);
    }
}
