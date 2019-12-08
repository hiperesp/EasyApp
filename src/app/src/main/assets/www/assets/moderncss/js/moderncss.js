function toggleMenuView(toggle = true, enable = false){
    var menuClassName = "menu-open";
    var menuContainer = document.querySelector("body");
    var globalContainer = menuContainer.querySelector(".page");
    var transitionDurationMs = 1000;
    if(toggle) {
        enable = !menuContainer.classList.contains(menuClassName);
    }
    if(enable) {
        menuContainer.classList.add(menuClassName);
        menuContainer.style.overflowX = "hidden";
        globalContainer.style.height = "100vh";
    } else {
        menuContainer.classList.remove(menuClassName);
    }
    setTimeout(function(globalContainer, menuContainer, menuClassName) {
        if(menuContainer.classList.contains(menuClassName)) {
            globalContainer.style.height = "100vh";
        } else {
            globalContainer.style.height = "";
            menuContainer.style.overflowX = "";
        }
    }, transitionDurationMs, globalContainer, menuContainer, menuClassName);
}
function toggleLights(toggle = true, setTo = 0){
    var lightClassName = ["", "dark", "black" ];
    var body = document.querySelector("body");
    var actual = 0;
    for(var i=1; i<lightClassName.length; i++) {
        if(body.classList.contains(lightClassName[i])) {
            body.classList.remove(lightClassName[i]);
            actual = i;
            break;
        }
    }
    if(toggle) {
        setTo = actual+1;
    }
    if(setTo>=lightClassName.length) {
        setTo = 0;
    }
    if(setTo>0) {
        body.classList.add(lightClassName[setTo]);
    }
}
/*
window.addEventListener("load", function(e){
    toggleMenuView();
});
*/
function createPhoto(title, subtitle, imageAddress){
    const card = document.createElement("div");
    card.classList.add("card");
    card.innerHTML = '<div class="card-background"><img src="'+imageAddress+'"></div><div class="card-content"><h6>'+subtitle+'</h6><h1>'+title+'</h1></div>';
    return card;
}
function createVideo(title, subtitle, videoAddress){
    const card = document.createElement("div");
    card.classList.add("card");
    card.innerHTML = '<div class="card-background"><video controls=""><source type="video/mp4" src="'+videoAddress+'"></video></div><div class="card-content"><h6>'+subtitle+'</h6><h1>'+title+'</h1></div>';
    return card;
}
function takePhoto() {
    EasyAppNativeInterface.requestCameraPhoto()
    .then((data) => {
        const card = createPhoto("Foto", "by EasyApp", "data:image/png;base64,"+data);
        const photoList = document.querySelector("#photoList");
        //photoList.appendChild(card);
        photoList.innerHTML = card.outerHTML + " " + photoList.innerHTML;
    })
    .catch((error) => {
        if(error==EasyAppNativeInterface.responseProtocolConstants.FAILED_USER_CANCELLED) {
            alert("Você cancelou a foto :(");
        } else if(error==EasyAppNativeInterface.responseProtocolConstants.PERMISSION_DENIED) {
            alert("Permissão negada :(");
        }
    });
}
function captureVideo() {
    EasyAppNativeInterface.requestCameraVideo()
    .then((data) => {
        const card = createVideo("Video", "by EasyApp", data);
        const photoList = document.querySelector("#photoList");
        //photoList.appendChild(card);
        photoList.innerHTML = card.outerHTML + " " + photoList.innerHTML;
    })
    .catch((error) => {
        if(error==EasyAppNativeInterface.responseProtocolConstants.FAILED_USER_CANCELLED) {
            alert("Você cancelou a foto :(");
        } else if(error==EasyAppNativeInterface.responseProtocolConstants.PERMISSION_DENIED) {
            alert("Permissão negada :(");
        }
    });
}
