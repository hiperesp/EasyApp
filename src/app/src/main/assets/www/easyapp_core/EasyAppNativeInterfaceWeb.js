if(typeof EasyAppNativeInterface=="undefined") {
    //to do all
    var EasyAppNativeInterface;
    (()=>{
        class GraphicalInterface {
            static init() {
                let style = document.createElement("link");
                style.rel = "stylesheet";
                style.type = "text/css";
                style.href = "easyapp_core/web_assets/style.css";
                document.querySelector("head").appendChild(style);
            }
            static alert(content, title = "") {

            }
            static confirm(content, title = "") {

            }
            static prompt(content, defaultValue, title = "") {

            }
            static Util = class {
                static drawCanvas(canvas, img){
                    canvas.width = getComputedStyle(canvas).width.split('px')[0];
                    canvas.height = getComputedStyle(canvas).height.split('px')[0];
                    let ratio  = Math.min(canvas.width / img.width, canvas.height / img.height);
                    let x = (canvas.width - img.width * ratio) / 2;
                    let y = (canvas.height - img.height * ratio) / 2;
                    canvas.getContext('2d').clearRect(0, 0, canvas.width, canvas.height);
                    canvas.getContext('2d').drawImage(img, 0, 0, img.width, img.height,
                        x, y, img.width * ratio, img.height * ratio);
                }
            }
            static Modal = class {
                modal;
                header;
                content;
                constructor(title){
                    this.modal = document.createElement("div");
                    this.modal.classList.add("easyapp-native-interface-web-modal");
                    this.header = document.createElement("div");
                    this.header.classList.add("easyapp-native-interface-web-modal-header");
                    this.content = document.createElement("div");
                    this.content.classList.add("easyapp-native-interface-web-modal-content");
                    this.modal.appendChild(this.header);
                    this.modal.appendChild(this.content);
                    this.setTitle(title);
                }
                setTitle(title = "") {
                    this.header.textContent = title;
                }
                show(){
                    document.querySelector("body").appendChild(this.modal);
                }
            }
            static Intent = class {
                static async startCameraIntent() {
                }
            }
        }
        GraphicalInterface.init();
        EasyAppNativeInterface = class {
            static __frameworkName() {
                return "EasyApp Web";
            }
            static __frameworkVersion() {
                return "0.0.20alpha";
            }
            static __requestCameraPhoto(callbackResolve, callbackReject) {
                Bridge.callbackCameraResolve = callbackResolve;
                Bridge.callbackCameraReject = callbackReject;
                NativeCaller.requestCameraPhoto();
            }
            static __getCallbackId() {
                return Bridge.getCallbackId();
            }
        };
        class Bridge {
            static callbackCameraResolve = "";
            static callbackCameraReject = "";
            static sendScriptToWeb(script) {
                eval(script, null);
            };
            static __uniqueCallbackId = 0;
            static getCallbackId(){
                return this.__uniqueCallbackId++;
            }
            static callbackCameraFunction(success, data, response){
                let callbackCall;
                if(success) {
                    callbackCall = this.callbackCameraResolve+"(\""+data+"\");";
                } else {
                    callbackCall = this.callbackCameraReject+"("+response+");";
                }
                this.sendScriptToWeb("window.EasyAppNativeInterface.__private.callback."+callbackCall);
            }
        };
        class ResponseProtocolConstants {
            static FAILED_USER_CANCELLED = -2;
            static PERMISSION_DENIED = -1;
            static SUCCESS = 0;
        };
        class NativeCaller extends ResponseProtocolConstants {
            static requestCameraPhoto(){
                this.startCameraIntent();
            }

            static startCameraIntent(){
                GraphicalInterface.Intent.startCameraIntent()
                .then((data) => {
                    console.log("foi");
                    Bridge.callbackCameraFunction(true, "", this.SUCCESS);
                })
                .catch((error) => {
                    console.log("falhou");
                    Bridge.callbackCameraFunction(false, "", this.PERMISSION_DENIED);
                });
            }
        };

        class Native {
            
        };
    });
}