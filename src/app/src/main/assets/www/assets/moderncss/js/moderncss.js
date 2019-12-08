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
function createCard(title, subtitle, background) {
    const card = document.createElement("div");
    card.classList.add("card");
    {
        const cardBackground = document.createElement("div");
        cardBackground.classList.add("card-background");
        cardBackground.appendChild(background);
        card.appendChild(cardBackground);

        const cardContent = document.createElement("div");
        cardContent.classList.add("card-content");
        {
            const cardSubtitle = document.createElement("h6");
            cardSubtitle.textContent = subtitle;
            cardContent.appendChild(cardSubtitle);

            const cardTitle = document.createElement("h1");
            cardTitle.textContent = title;
            cardContent.appendChild(cardTitle);
        }
        card.appendChild(cardContent);
    }
    return card;
}
function createPhoto(title, subtitle, imageAddress){
    const image = document.createElement("img");
    image.src = imageAddress;
    return createCard(title, subtitle, image);
}
function createVideo(title, subtitle, videoAddress, videoType){
    const video = document.createElement("video");
    video.controls = true;
    {
        const source = document.createElement("source");
        source.type = videoType;
        source.src = videoAddress;
        video.appendChild(source);
    }
    return createCard(title, subtitle, video);
}
function appendFirst(node, appendTo) {
    appendTo.appendChild(node);
    while(appendTo.firstChild!=node) {
        appendTo.appendChild(appendTo.firstChild);
    }
}

function takePhoto() {
    EasyAppNativeInterface.requestCameraPhoto()
    .then((data) => {
        const mediaList = document.querySelector("#mediaList");
        const photo = createPhoto("Foto", "by EasyApp", "data:image/png;base64,"+data);
        appendFirst(document.createTextNode(" "), mediaList);
        appendFirst(photo, mediaList);
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
        const mediaList = document.querySelector("#mediaList");
        const video = createVideo("Vídeo", "by EasyApp", data, "video/mp4");
        appendFirst(document.createTextNode(" "), mediaList);
        appendFirst(video, mediaList);
    })
    .catch((error) => {
        if(error==EasyAppNativeInterface.responseProtocolConstants.FAILED_USER_CANCELLED) {
            alert("Você cancelou o video :(");
        } else if(error==EasyAppNativeInterface.responseProtocolConstants.PERMISSION_DENIED) {
            alert("Permissão negada :(");
        }
    });
}
