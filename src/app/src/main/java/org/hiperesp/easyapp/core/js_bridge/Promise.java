package org.hiperesp.easyapp.core.js_bridge;

public class Promise {
    String resolve;
    String reject;
    BridgeInternalInterface bridgeInternalInterface;
    public Promise(String resolve, String reject) {
        this.resolve = resolve;
        this.reject = reject;
    }
    public void setBridgeInternalInterface(BridgeInternalInterface bridgeInternalInterface) {
        this.bridgeInternalInterface = bridgeInternalInterface;
    }
    public void resolve(String...parameters) {
        bridgeInternalInterface.callback(resolve+parseParameters(parameters));
    }
    public void reject(String...parameters) {
        bridgeInternalInterface.callback(reject+parseParameters(parameters));
    }
    private String parseParameters(String...parameters) {
        String callback = "(";
        for (int i=0; i<parameters.length; i++) {
            callback+= parameters[i];
            if(i!=parameters.length-1) callback+= ",";
        }
        callback+=")";
        return callback;
    }
}
