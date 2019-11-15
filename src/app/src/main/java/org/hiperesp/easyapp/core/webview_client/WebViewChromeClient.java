package org.hiperesp.easyapp.core.webview_client;

import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
/*
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.JsResult;
import android.webkit.WebView;
*/
public class WebViewChromeClient extends WebChromeClient {
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        android.util.Log.d("WebView", consoleMessage.message());
        return true;
    }
    /*


        TO DO:


        Essa função sobrescreve o Alert padrão da webview, pois o alert padrão aparece com um título
        similar a "The page at file:/// says:", portanto fica inviável utilizar esse Alert padrão.

        A função abaixo utiliza o AlertDialog do sistema, sobrescrevendo o Alert padrão da webview,
        mas ela não pausa a webview enquanto o alert está sendo mostrado, o código continua em
        execução mesmo se o usuário não apertar o botão de confirmação, e caso haja alguma página
        que mostre 2 alerts consecutivos, um irá aparecer na frente do outro, diferente do esperado,
        que é mostrar um alert, e somente quando o usuário apertar o botão de confirmação, aparecer
        o outro alert.

        Função comentada por este motivo. Arrumar um jeito de pausar o script para esperar o usuário
        realizar uma ação.


    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        AlertDialog dialog = new AlertDialog.Builder(view.getContext()).
                setTitle(Settings.projectTitle).
                setMessage(message).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                }).create();
        dialog.show();
        result.confirm();
        return true;
    }*/
}
