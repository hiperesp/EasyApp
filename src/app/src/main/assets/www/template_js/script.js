window.addEventListener("load", loadHandler);
let cardContainer;
function loadHandler(e){
    cardContainer = document.querySelector("#card-list");
}
function createCard(cardContentDataVector, cardBackgroundData){
    let card = document.createElement("div");
    card.classList.add("card");
    let cardBackground = document.createElement("div");
    cardBackground.classList.add("card-background");
    cardBackground.appendChild(cardBackgroundData);
    card.appendChild(cardBackground);
    let cardContent = document.createElement("div");
    cardContent.classList.add("card-content");
    for(let i=0; i<cardContentDataVector.length; i++) cardContent.appendChild(cardContentDataVector[i]);
    card.appendChild(cardContent);
    return card;
}
function createCardTitleSubtitle(title, subtitle) {
    let cardTitle = document.createElement("h1");
    cardTitle.textContent = title;
    let cardSubtitle = document.createElement("h6");
    cardSubtitle.textContent = subtitle;
    return [cardSubtitle, cardTitle];
}
function createVideoCard(title, subtitle, videoSource){
    let video = document.createElement("video");
    video.controls = false;
    video.autoplay = true;
    video.muted = true;
    video.onplay = function(e) {
        e.target.playing = true;
    };
    video.onpause = function(e){
        e.target.playing = false;
    };
    video.addEventListener("click", function(e){
        if(e.target.playing) {
            if(e.target.muted) {
                e.target.muted = false;
            } else {
                e.target.pause();
            }
        } else {
            e.target.muted = true;
            e.target.play();
        }
    });
    let source = document.createElement("source");
    source.type = "video/mp4";
    source.src = videoSource;
    video.appendChild(source);
    let cardContent = createCardTitleSubtitle(title, subtitle);
    let cardBackground = video;
    return createCard(cardContent, cardBackground);
}
function createImageCard(title, subtitle, imageSource){
    let image = document.createElement("img");
    image.src = imageSource;
    let cardContent = createCardTitleSubtitle(title, subtitle);
    let cardBackground = image;
    return createCard(cardContent, cardBackground);
}
function appendFirst(container, content){
    container.appendChild(content);
    while(container.firstChild!=content) {
        container.appendChild(container.firstChild)
    }
}
function appendCard(card){
    let cardSeparator = document.createTextNode(" ");
    appendFirst(cardContainer, cardSeparator);
    appendFirst(cardContainer, card);
}
function takePhoto(){
    EasyAppNativeInterface.requestCameraPhoto()
    .then((data) => {
        appendCard(createImageCard("Foto", "tirada com EasyApp", "data:image/png;base64,"+data));
    })
    .catch((error) => {
        let errorText;
        if(error==EasyAppNativeInterface.responseProtocolConstants.FAILED_USER_CANCELLED) {
            errorText = "Você cancelou a foto :(";
        } else if(error==EasyAppNativeInterface.responseProtocolConstants.PERMISSION_DENIED) {
            errorText = "Você não deixou a gente tirar fotos.";
        }
        EasyAppNativeInterface.makeToast(errorText, true);
    });
}
function recordVideo(){
    EasyAppNativeInterface.requestCameraVideo()
    .then((data) => {
        appendCard(createVideoCard("Vídeo", "gravado com EasyApp", data));
    })
    .catch((error) => {
        let errorText;
        if(error==EasyAppNativeInterface.responseProtocolConstants.FAILED_USER_CANCELLED) {
            errorText = "Você cancelou o vídeo :(";
        } else if(error==EasyAppNativeInterface.responseProtocolConstants.PERMISSION_DENIED) {
            errorText = "Você não deixou a gente gravar vídeos.";
        }
        EasyAppNativeInterface.makeToast(errorText, true);
    });
}