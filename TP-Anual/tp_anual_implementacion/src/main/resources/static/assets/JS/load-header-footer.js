function loadHTML(containerId, url) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", url, true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            document.getElementById(containerId).innerHTML = xhr.responseText;
        } else {
            console.error(`Error al cargar ${url}: ${xhr.status}`);
        }
    };
    xhr.onerror = function () {
        console.error(`Error de conexión al intentar cargar ${url}`);
    };
    xhr.send();
}

document.addEventListener("DOMContentLoaded", () => {
    loadHTML("header", "/Header");
    loadHTML("footer", "/Footer");
});