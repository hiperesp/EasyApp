if(typeof EasyAppNativeInterface=="undefined") {
    var EasyAppNativeInterface = {
        ____uniqueCallbackId: 0,
        __getCallbackId: () => EasyAppNativeInterface.____uniqueCallbackId++,
        __frameworkName: () => "EasyApp Simulator",
        __frameworkVersion: () => "0.0.11alpha",
        __requestCameraPhoto: (callbackResolve, callbackReject) => {
            let photoList = [
                "iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsIAAA7CARUoSoAAAACjSURBVFhH7ZdRCoAwCECt07RT1M3Gblan6DiF4E+bbgrBFvkg8kPYm5rQBLBc0JGZ3t1wARdwAXEP7HBSVGeDAGts5x4pUPSErYD2cERzOCLlFQKWw2OkQAknMd4M5BXAHkvkN+L63MoZvwIS3PRLk16jewVcoCmAveaet/juV4BYNyEy3B4oBCz9TYkCJdyeYCtgkdAuHynP/wtcwAX+LgBwA1jMLpvMc7kYAAAAAElFTkSuQmCC",
                "iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAC6SURBVFhH7ZcBCsIwDACjr9FXzJ8Vf6av8Dkb0QZKmqTVrBY0B6EdlOWWhMIOAKcVJnLM6zRCIARCQL0HbvDIO5sLnJ/rkvTz9+vrjIQo0JucSClvDDSJqgXvJvfingFqwac0BTCBFRJWzznuCvDho+RcQhvSpgDOhBR74a6Al98TwF5T9FBdRLy/2qQjvUlK+HBOb0GzAhbSFVx+oVShoRXgL+fPEpWA1fMRiBX4pkT8F4RACPy7AMAGvRw6yRA2JRIAAAAASUVORK5CYII=",
            ];
            let data = photoList[Math.floor(Math.random()*photoList.length)];
            window.EasyAppNativeInterface.__private.callback[callbackResolve](data);
        },
        __setStatusBarColor: ()=>{},
        __setNavigationBarColor: ()=>{},


    }
}