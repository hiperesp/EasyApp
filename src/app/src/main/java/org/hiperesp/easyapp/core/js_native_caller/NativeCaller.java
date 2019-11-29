package org.hiperesp.easyapp.core.js_native_caller;

import androidx.annotation.NonNull;
import org.hiperesp.easyapp.MainActivity;
import org.hiperesp.easyapp.core.js_bridge.BridgeWebInterface;
import org.hiperesp.easyapp.core.native_functions.Camera;
import org.hiperesp.easyapp.core.native_functions.Native;
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

    private ArrayList<Native> aNatives = new ArrayList<Native>();

    public void requestCameraPhoto(String resolve, String reject){
        Camera camera = new Camera(resolve, reject, activity, bridgeWebInterface, getUniqueInt());
        aNatives.add(camera);
        camera.start();
    }
    public void makeToast(String resolve, String reject, String text, boolean isShort){
        Toast toast = new Toast(resolve, reject, activity, bridgeWebInterface, getUniqueInt());
        toast.start(text, isShort);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for(Native aNative : aNatives) {
            if(aNative.getCode()==requestCode) {
                aNative.onRequestPermissionsResult(permissions, grantResults);
            }
        }
    }
    public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        for(Native aNative : aNatives) {
            if(aNative.getCode()==requestCode) {
                aNative.onActivityResult(resultCode, data);
            }
        }
    }

    private int getUniqueInt() {
        return uniqueInt++;
    }
}
