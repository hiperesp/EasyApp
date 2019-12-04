package org.hiperesp.easyapp.core.native_functions;

import android.app.Activity;
import org.hiperesp.easyapp.core.js_bridge.BridgeInternalInterface;
import org.hiperesp.easyapp.core.js_bridge.Promise;
import org.hiperesp.easyapp.core.js_bridge.PromiseUtils;
import org.hiperesp.easyapp.core.native_functions.util.ResponseProtocolConstants;

public abstract class Native extends PromiseUtils implements ResponseProtocolConstants {

    Activity activity;
    BridgeInternalInterface bridgeInternalInterface;

    public Promise promise;

    private int code;

    Native(Promise callback, Activity activity, BridgeInternalInterface bridgeInternalInterface, int code) {
        this.activity = activity;
        this.code = code;
        this.bridgeInternalInterface = bridgeInternalInterface;
        this.promise = callback;
        this.promise.setBridgeInternalInterface(this.bridgeInternalInterface);
    }


    public int getCode(){ return code; }
}
