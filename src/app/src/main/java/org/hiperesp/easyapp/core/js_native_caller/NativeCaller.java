package org.hiperesp.easyapp.core.js_native_caller;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Base64;
import androidx.annotation.NonNull;
import org.hiperesp.easyapp.MainActivity;
import org.hiperesp.easyapp.core.js_bridge.BridgeWebInterface;
import java.io.ByteArrayOutputStream;

public class NativeCaller implements IntentRequestCodeConstants, ResponseProtocolConstants {

    private MainActivity activity;
    private BridgeWebInterface bridgeWebInterface;

    public NativeCaller(MainActivity activity, BridgeWebInterface bridgeWebInterface) {
        this.activity = activity;
        this.bridgeWebInterface = bridgeWebInterface;
    }

    public void requestCameraPhoto(){
        if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            startCameraIntent();
        }
    }

    @SuppressWarnings("unused")
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraIntent();
            } else {
                bridgeWebInterface.callbackCameraFunction(false, null, PERMISSION_DENIED);
            }
        }
    }

    private void startCameraIntent(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @SuppressWarnings({"unused", "ConstantConditions"})
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String imageBase64Data = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                bridgeWebInterface.callbackCameraFunction(true, imageBase64Data, SUCCESS);
            } else {
                bridgeWebInterface.callbackCameraFunction(false, null, FAILED_USER_CANCELLED);
            }
        }
    }
}
