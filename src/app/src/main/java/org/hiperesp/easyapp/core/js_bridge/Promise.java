package org.hiperesp.easyapp.core.js_bridge;

public class Promise {
    String resolve;
    String reject;
    Bridge bridge;
    public Promise(String resolve, String reject) {
        this.resolve = resolve;
        this.reject = reject;
    }
    public void setBridge(Bridge bridge) {
        this.bridge = bridge;
    }
    public void resolve(String...parameters) {
        bridge.callback(resolve+parseParameters(parameters));
    }
    public void reject(String...parameters) {
        bridge.callback(reject+parseParameters(parameters));
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
