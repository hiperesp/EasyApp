package org.hiperesp.easyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import org.hiperesp.easyapp.core.EasyApp;
import org.hiperesp.easyapp.core.settings.Settings;

public class MainActivity extends AppCompatActivity {

    EasyApp easyApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        Settings.init();
        setDefaultFlags();
        easyApp = new EasyApp(this, (WebView) findViewById(R.id.mainWebView));
    }

    private void setDefaultFlags(){
        //Window window = getWindow();
        //window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        easyApp.nativeCaller.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyApp.nativeCaller.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if(!easyApp.onBackPressed()){
            super.onBackPressed();
        }
    }
}
