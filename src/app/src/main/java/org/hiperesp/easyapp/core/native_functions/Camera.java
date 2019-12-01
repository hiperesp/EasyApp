package org.hiperesp.easyapp.core.native_functions;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Base64;
import org.hiperesp.easyapp.core.js_bridge.Bridge;
import org.hiperesp.easyapp.core.js_bridge.Promise;
import java.io.ByteArrayOutputStream;

public class Camera extends Native {

    private String[] requestPermissions = new String[]{
            Manifest.permission.CAMERA
    };

    public Camera(Promise callback, Activity activity, Bridge bridge, int code){
        super(callback, activity, bridge, code);
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
    public void onPermissionResult(String[] permissions, boolean[] results){
        boolean granted = true;
        for (boolean result:results) {
            if(result) {
                continue;
            }
            granted = false;
            break;
        }
        if(granted) {
            onPermissionGranted();
        } else {
            onPermissionDenied();
        }
    }
    private void onPermissionGranted(){
        startIntent();
    }
    private void onPermissionDenied(){
        reject(PERMISSION_DENIED);
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
        resolve(imageBase64Data);
    }

    @Override
    public void onResultFailed(){
        reject(FAILED_USER_CANCELLED);
    }

    private void resolve(String data){
        promise.resolve(string(data));
    }
    private void reject(int errorCode){
        promise.reject(number(errorCode));
    }
}
