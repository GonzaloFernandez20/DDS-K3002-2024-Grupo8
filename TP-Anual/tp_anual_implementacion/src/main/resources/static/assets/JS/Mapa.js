function agregarDescripcion(punto) {
    console.log("Va a agregar la descripción de: " + punto);
    const content = document.createElement("div");

    content.classList.add("punto-en-el-mapa");
    content.innerHTML = `
    <div class="descripcion">
        <h5>${punto.nombreDelPunto}</h5>
        <p>${punto.calle} ${punto.altura}, ${punto.ciudad}</p>
        <p>Ocupación: ${punto.stockActual}/${punto.capacidad} viandas</p>
        <p>${punto.estado}</p>
    </div>
    `;
    return content.outerHTML;
}

async function initMap() {
    const { Map, InfoWindow } = await google.maps.importLibrary("maps");
    const {AdvancedMarkerElement} = await google.maps.importLibrary("marker");

    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 12,
        center: { lat: -34.59863167295616, lng: -58.422691923482915 },
        mapId: "MAPA_HELADERAS",
    });

    const infoWindow = new google.maps.InfoWindow({
        minWidth: 200,
        maxWidth: 200
    });

    fetch("/heladerasEnElMapa")
        .then(response => {
            console.log(response);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(puntos => {
            console.log(puntos);
            puntos.forEach(punto => {
                const marker = new google.maps.marker.AdvancedMarkerElement({
                    position: { lat: punto.latitud, lng: punto.longitud },
                    map: map,
                    title: ` ${punto.nombreDelPunto}`,
                });
                google.maps.event.addListener(marker, 'click', function(){
                    infoWindow.setContent(agregarDescripcion(punto));
                    infoWindow.open(map, marker);
                });
            });
        })
        .catch(error => console.error("Error al cargar los puntos: ", error));
}