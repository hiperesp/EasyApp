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
