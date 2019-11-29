package org.hiperesp.easyapp.core.native_functions;

import android.app.Activity;
import android.content.Intent;
import org.hiperesp.easyapp.core.js_bridge.Bridge;

public class Toast extends Native {

    private String text;
    private boolean isShort;

    public Toast(String resolve, String reject, Activity activity, Bridge bridge, int code){
        super(activity, bridge, resolve, reject, code);
    }

    public void start(String text, boolean isShort) {
        this.text = text;
        this.isShort = isShort;
        requestPermission();
    }

    @Override
    void requestPermission(){
        startIntent();
    }

    @Override
    public void onPermissionGranted(){
        startIntent();
    }

    @Override
    public void onPermissionDenied(){
        callback(false, PERMISSION_DENIED);
    }

    @Override
    void startIntent(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int time;
                if (isShort) {
                    time = android.widget.Toast.LENGTH_SHORT;
                } else {
                    time = android.widget.Toast.LENGTH_LONG;
                }
                android.widget.Toast.makeText(activity, text, time).show();
                onResultOk(null);
            }
        });
    }

    @Override
    public void onResultOk(Intent data) {

        callback(true, SUCCESS);
    }

    @Override
    public void onResultFailed(){
        callback(false, FAILED_USER_CANCELLED);
    }

    private void callback(boolean success, int response){
        String callbackCall;
        if(success) {
            callbackCall = callbackResolve+"()";
        } else {
            callbackCall = callbackReject+"("+response+")";
        }
        callback(callbackCall);
    }
}
