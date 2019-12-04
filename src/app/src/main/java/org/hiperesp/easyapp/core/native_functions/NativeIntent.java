package org.hiperesp.easyapp.core.native_functions;

public interface NativeIntent {
    void startIntent();
    void onActivityResult(int resultCode, android.content.Intent data);
}
