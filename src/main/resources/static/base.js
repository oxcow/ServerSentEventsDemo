function getById(id) {
    return document.getElementById(id);
}

function outPrint(id, message, color) {

    var now = new Date();
    var out = getById(id);

    if (message) {
        var node = document.createElement("div");
        var textNode = document.createTextNode(now + ": " + message);
        if (color) {
            node.setAttribute("style", "color:" + color);
        }
        node.appendChild(textNode);
        out.appendChild(node);
    }
}

function cleanPrint(id) {
    getById(id).innerHTML = '';
}

function closeEvent(evtSource) {
    evtSource.close();
}