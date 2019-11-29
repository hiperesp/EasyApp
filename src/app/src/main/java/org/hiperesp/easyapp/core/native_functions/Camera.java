package org.hiperesp.easyapp.core.native_functions;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Base64;
import org.hiperesp.easyapp.core.js_bridge.Bridge;
import org.hiperesp.easyapp.core.js_native_caller.ResponseProtocolConstants;
import java.io.ByteArrayOutputStream;

public class Camera extends Native implements ResponseProtocolConstants {

    private String[] requestPermissions = new String[]{
            Manifest.permission.CAMERA
    };

    public Camera(String callbackResolve, String callbackReject, Activity activity, Bridge bridge, int code){
        super(activity, bridge, callbackResolve, callbackReject, code);
    }

    public void start(){
        requestPermission();
    }

    @Override
    void requestPermission(){
        if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(requestPermissions, getCode());
        } else {
            startIntent();
        }
    }

    @Override
    public void onPermissionGranted(){
        startIntent();
    }

    @Override
    public void onPermissionDenied(){
        callback(false, null, PERMISSION_DENIED);
    }

    @Override
    void startIntent(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, getCode());
    }

    @Override
    public void onResultOk(Intent data) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageBase64Data = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        callback(true, imageBase64Data, SUCCESS);
    }

    @Override
    public void onResultFailed(){
        callback(false, null, FAILED_USER_CANCELLED);
    }

    private void callback(boolean success, String data, int response){
        String callbackCall;
        if(success) {
            callbackCall = callbackResolve+"(\""+data+"\")";
        } else {
            callbackCall = callbackReject+"("+response+")";
        }
        callback(callbackCall);
    }
}
