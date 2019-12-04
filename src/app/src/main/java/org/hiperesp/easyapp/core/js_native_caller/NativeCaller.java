package org.hiperesp.easyapp.core.js_native_caller;

import androidx.annotation.NonNull;
import org.hiperesp.easyapp.MainActivity;
import org.hiperesp.easyapp.core.js_bridge.BridgeWebInterface;
import org.hiperesp.easyapp.core.js_bridge.Promise;
import org.hiperesp.easyapp.core.native_functions.Camera;
import org.hiperesp.easyapp.core.native_functions.Native;
import org.hiperesp.easyapp.core.native_functions.NativeIntent;
import org.hiperesp.easyapp.core.native_functions.NativePermission;
import org.hiperesp.easyapp.core.native_functions.Toast;

import java.util.ArrayList;

public class NativeCaller {

    private MainActivity activity;
    private BridgeWebInterface bridgeWebInterface;

    private int uniqueInt = 0;

    public NativeCaller(MainActivity activity, BridgeWebInterface bridgeWebInterface) {
        this.activity = activity;
        this.bridgeWebInterface = bridgeWebInterface;
    }

    private ArrayList<NativePermission> nativesPermission = new ArrayList<NativePermission>();
    private ArrayList<NativeIntent> nativesIntent = new ArrayList<NativeIntent>();

    public void requestCameraPhoto(Promise callback){
        Camera camera = new Camera(callback, activity, bridgeWebInterface, getUniqueInt());
        nativesPermission.add(camera);
        nativesIntent.add(camera);
        camera.start();
    }

    public void makeToast(Promise callback, String text, boolean isShort){
        Toast toast = new Toast(callback, activity, bridgeWebInterface, getUniqueInt());
        toast.start(text, isShort);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for(NativePermission nativePermission: nativesPermission) {
            if(((Native)nativePermission).getCode()==requestCode) {
                nativePermission.onPermissionResult(permissions, grantResults);
                break;
            }
        }
    }
    public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        for(NativeIntent nativeIntent: nativesIntent) {
            if(((Native)nativeIntent).getCode()==requestCode) {
                nativeIntent.onActivityResult(resultCode, data);
                break;
            }
        }
    }

    private int getUniqueInt() {
        return uniqueInt++;
    }
}
