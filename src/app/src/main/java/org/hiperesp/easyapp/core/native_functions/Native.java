package org.hiperesp.easyapp.core.native_functions;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import org.hiperesp.easyapp.core.js_bridge.Bridge;
import org.hiperesp.easyapp.core.js_bridge.Promise;
import org.hiperesp.easyapp.core.js_bridge.PromiseUtils;
import org.hiperesp.easyapp.core.js_native_caller.ResponseProtocolConstants;

public abstract class Native extends PromiseUtils implements ResponseProtocolConstants {

    Activity activity;
    Bridge bridge;

    public Promise promise;

    private int code;

    Native(Promise callback, Activity activity, Bridge bridge, int code) {
        this.activity = activity;
        this.code = code;
        this.bridge = bridge;
        this.promise = callback;
        this.promise.setBridge(this.bridge);
    }

    abstract void requestPermission();
    abstract void startIntent();

    public abstract void onResultOk(Intent data);
    public abstract void onResultFailed();
    public abstract void onPermissionResult(String[] permissions, boolean[] grantResults);

    public int getCode(){ return code; }

    public void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults){
        boolean grantResultsBoolean[] = new boolean[grantResults.length];
        for(int i=0; i<grantResults.length; i++) {
            grantResultsBoolean[i] = grantResults[i] == PackageManager.PERMISSION_GRANTED;
        }
        onPermissionResult(permissions, grantResultsBoolean);
    }

    public void onActivityResult(int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            onResultOk(data);
        } else {
            onResultFailed();
        }
    }
}
