package org.hiperesp.easyapp.core.native_functions;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;

import androidx.core.content.MimeTypeFilter;

import org.hiperesp.easyapp.core.js_bridge.BridgeInternalInterface;
import org.hiperesp.easyapp.core.js_bridge.Promise;

public class Video extends Native implements NativePermission, NativeIntent {

    public Video(Promise callback, Activity activity, BridgeInternalInterface bridgeInternalInterface, int code){
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
        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
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
        resolve(data.getDataString());
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
