package org.hiperesp.easyapp.core.native_functions;

public interface NativePermission {
    void requestPermission();
    void onPermissionResult(String[] permissions, int[] grantResults);
}
