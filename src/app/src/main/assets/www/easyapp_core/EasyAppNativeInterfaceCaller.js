EasyAppNativeInterface.__private = {
    callback: {},
    registerCallback: function(callback){
        let callbackIdString = "callback_"+EasyAppNativeInterface.__getCallbackId();
        EasyAppNativeInterface.__private.callback[callbackIdString] = callback;
        return callbackIdString;
    },
    nativePromiseCall: function(action) {
        return new Promise(function(resolve, reject) {
            let callbackResolveName = EasyAppNativeInterface.__private.registerCallback(resolve);
            let callbackRejectName = EasyAppNativeInterface.__private.registerCallback(reject);
            action.bind(EasyAppNativeInterface)(callbackResolveName, callbackRejectName);
        });
    }
};
EasyAppNativeInterface.requestCameraPhoto = async () => {
    return EasyAppNativeInterface.__private.nativePromiseCall(EasyAppNativeInterface.__requestCameraPhoto);
}