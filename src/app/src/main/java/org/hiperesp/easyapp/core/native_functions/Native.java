package org.hiperesp.easyapp.core.native_functions;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import org.hiperesp.easyapp.core.js_bridge.Bridge;
import org.hiperesp.easyapp.core.js_native_caller.ResponseProtocolConstants;

public abstract class Native implements ResponseProtocolConstants {

    Activity activity;
    Bridge bridge;

    public String callbackResolve;
    public String callbackReject;

    private int code;

    Native(Activity activity, Bridge bridge, String resolve, String reject, int code) {
        this.activity = activity;
        this.bridge = bridge;

        callbackResolve = resolve;
        callbackReject = reject;
        this.code = code;
    }

    abstract void requestPermission();
    abstract void startIntent();

    public abstract void onPermissionDenied();
    public abstract void onPermissionGranted();
    public abstract void onResultOk(Intent data);
    public abstract void onResultFailed();

    public int getCode(){ return code; }

    public void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults){
        boolean granted = false;
        for(int grantResult: grantResults) {
            if(grantResult == PackageManager.PERMISSION_GRANTED) {
                granted = true;
            }
        }
        if (granted) {
            onPermissionGranted();
        } else {
            onPermissionDenied();
        }
    }

    public void onActivityResult(int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            onResultOk(data);
        } else {
            onResultFailed();
        }
    }

    public void callback(){
        callback(callbackResolve+"()");
    }

    public void callback(String callbackCall){
        bridge.callback(callbackCall);
    }
}
