package org.hiperesp.easyapp.core.native_functions;

import android.app.Activity;
import android.os.Handler;
import org.hiperesp.easyapp.core.js_bridge.BridgeInternalInterface;
import org.hiperesp.easyapp.core.js_bridge.Promise;

public class Toast extends Native {

    private String text;
    private boolean isShort;

    public Toast(Promise callback, Activity activity, BridgeInternalInterface bridgeInternalInterface, int code){
        super(callback, activity, bridgeInternalInterface, code);
    }

    public void start(String text, boolean isShort) {
        this.text = text;
        this.isShort = isShort;
        showToast();
    }

    public void showToast(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final int time;
                if (isShort) {
                    time = android.widget.Toast.LENGTH_SHORT;
                } else {
                    time = android.widget.Toast.LENGTH_LONG;
                }
                android.widget.Toast.makeText(activity, text, time).show();
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        resolve();
                    }
                }, time==android.widget.Toast.LENGTH_SHORT?2500:4000);
            }
        });
    }

    private void resolve(){
        promise.resolve();
    }

}
