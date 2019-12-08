package org.hiperesp.easyapp.core.native_functions;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import org.hiperesp.easyapp.core.js_bridge.BridgeInternalInterface;
import org.hiperesp.easyapp.core.js_bridge.Promise;
import java.io.ByteArrayOutputStream;

public class Camera extends Native implements NativePermission, NativeIntent {

    public Camera(Promise callback, Activity activity, BridgeInternalInterface bridgeInternalInterface, int code){
        super(callback, activity, bridgeInternalInterface, code);
    }

    public void start(){
        requestPermission();
    }

    @Override
    public void requestPermission(){
        if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{ Manifest.permission.CAMERA}, getCode());
        } else {
            startIntent();
        }
    }

    @Override
    public void onPermissionResult(String[] permissions, int[] granted){
        if(granted[0]==PackageManager.PERMISSION_GRANTED) {
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
    public void startIntent(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, getCode());
    }

    @Override
    public void onActivityResult(int resultCode, Intent data) {
        if(resultCode==Activity.RESULT_OK) {
            onResultOk(data);
        } else {
            onResultFailed();
        }
    }

    private void onResultOk(Intent data) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageBase64Data = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        resolve(imageBase64Data);
    }

    private void onResultFailed(){
        reject(FAILED_USER_CANCELLED);
    }

    private void resolve(String data){
        promise.resolve(string(data));
    }
    private void reject(int errorCode){
        promise.reject(number(errorCode));
    }
}
