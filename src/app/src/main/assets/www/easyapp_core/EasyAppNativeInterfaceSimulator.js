if(typeof EasyAppNativeInterface=="undefined") {
    var EasyAppNativeInterface;
    (()=>{
        EasyAppNativeInterface = class {
            static __frameworkName() {
                return "EasyApp Simulator";
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
        class Manifest {
            static permission = {
                CAMERA: "android.permission.CAMERA",
            }
            static permissionTextAction = {
                "android.permission.CAMERA": "tire fotos e grave vídeos"
            }
        };
        class ResponseProtocolConstants {
            static FAILED_USER_CANCELLED = -2;
            static PERMISSION_DENIED = -1;
            static SUCCESS = 0;
        };
        class IntentRequestCodeConstants extends ResponseProtocolConstants {
            static CAMERA_REQUEST = 1888;
            static CAMERA_PERMISSION_CODE = 100;
        };
        class NativeCaller extends IntentRequestCodeConstants {
            static requestCameraPhoto(){
                if (!Native.checkPermission(Manifest.permission.CAMERA)) {
                    Native.requestPermissions([Manifest.permission.CAMERA], this.CAMERA_PERMISSION_CODE);
                } else {
                    this.startCameraIntent();
                }
            }

            static onRequestPermissionsResult(requestCode, permissions, grantResults) {
                if (requestCode == this.CAMERA_PERMISSION_CODE) {
                    if (grantResults[0]) {
                        this.startCameraIntent();
                    } else {
                        Bridge.callbackCameraFunction(false, "", this.PERMISSION_DENIED);
                    }
                }
            }

            static startCameraIntent(){
                if(window.confirm("Tirar foto?")) {
                    let photoList = [
                        "iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsIAAA7CARUoSoAAAACjSURBVFhH7ZdRCoAwCECt07RT1M3Gblan6DiF4E+bbgrBFvkg8kPYm5rQBLBc0JGZ3t1wARdwAXEP7HBSVGeDAGts5x4pUPSErYD2cERzOCLlFQKWw2OkQAknMd4M5BXAHkvkN+L63MoZvwIS3PRLk16jewVcoCmAveaet/juV4BYNyEy3B4oBCz9TYkCJdyeYCtgkdAuHynP/wtcwAX+LgBwA1jMLpvMc7kYAAAAAElFTkSuQmCC",
                        "iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAC6SURBVFhH7ZcBCsIwDACjr9FXzJ8Vf6av8Dkb0QZKmqTVrBY0B6EdlOWWhMIOAKcVJnLM6zRCIARCQL0HbvDIO5sLnJ/rkvTz9+vrjIQo0JucSClvDDSJqgXvJvfingFqwac0BTCBFRJWzznuCvDho+RcQhvSpgDOhBR74a6Al98TwF5T9FBdRLy/2qQjvUlK+HBOb0GzAhbSFVx+oVShoRXgL+fPEpWA1fMRiBX4pkT8F4RACPy7AMAGvRw6yRA2JRIAAAAASUVORK5CYII=",
                    ];
                    let data = photoList[Math.floor(Math.random()*photoList.length)];
                    this.onActivityResult(this.CAMERA_REQUEST, true, data)
                } else {
                    this.onActivityResult(this.CAMERA_REQUEST, false, "")
                }
            }

            static onActivityResult(requestCode, resultCode, data) {
                if (requestCode == this.CAMERA_REQUEST) {
                    if(resultCode) {
                        Bridge.callbackCameraFunction(true, data, this.SUCCESS);
                    } else {
                        Bridge.callbackCameraFunction(false, "", this.FAILED_USER_CANCELLED);
                    }
                }
            }
        };

        class Native {
            static userPermissions = [];
            static addUserPermission(permission) {
                this.userPermissions.push(permission);
            }
            static checkPermission(permission){
                return this.userPermissions.indexOf(permission)>-1;
            }
            static requestPermissions(permissions, permission_code) {
                let grantResults = [];
                for(let permission of permissions) {
                    if(window.confirm("Permitir que EasyApp "+Manifest.permissionTextAction[permission]+"?")) {
                        this.addUserPermission(permission);
                        grantResults.push(true);
                    } else {
                        grantResults.push(false);
                    }
                }
                NativeCaller.onRequestPermissionsResult(permission_code, permissions, grantResults);
            }
        };
    })();
}