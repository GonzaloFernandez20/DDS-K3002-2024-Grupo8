let map;
let marker;
let geocoder;
let responseDiv;
let response;

let inputRadio;
let inputText;
let autocomplete;

function initMap() {
  map = new google.maps.Map(document.getElementById("map"), {
    zoom: 14,
    center: { lat: -34.598481538332464, lng: -58.420170648034336 },
    mapTypeControl: false,
  });
  geocoder = new google.maps.Geocoder();

  // Crear input para ingresar la dirección
  inputText = document.createElement("input");
  inputText.type = "text";
  inputText.placeholder = "Ingresar Punto de Referencia";

  // Crear input para ingresar el radio
  inputRadio = document.createElement("input");
  inputRadio.type = "number";
  inputRadio.placeholder = "Ingresar Radio de Búsqueda (m)";

  const submitButton = document.createElement("input");
  submitButton.type = "button";
  submitButton.value = "Aceptar";
  submitButton.classList.add("button", "button-primary");

  const clearButton = document.createElement("input");
  clearButton.type = "button";
  clearButton.value = "Borrar";
  clearButton.classList.add("button", "button-secondary");

  response = document.createElement("pre");
  response.id = "response";
  response.innerText = "";

  responseDiv = document.createElement("div");
  responseDiv.id = "response-container";
  responseDiv.appendChild(response);

  const instructionsElement = document.createElement("p");
  instructionsElement.id = "instructions";
  instructionsElement.innerHTML =
    "<strong>Instrucciones</strong>: Ingresa un Punto de Referencia, un Radio, y te recomendamos posibles Puntos de Colocación de Heladeras";

  // Agregar controles al mapa
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(inputText);
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(inputRadio);
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(submitButton);
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(clearButton);
  map.controls[google.maps.ControlPosition.LEFT_TOP].push(instructionsElement);
  map.controls[google.maps.ControlPosition.LEFT_TOP].push(responseDiv);

  // Vincular el Autocomplete al inputText
  autocomplete = new google.maps.places.Autocomplete(inputText, {
    types: ["geocode"], // Opcional: filtra solo direcciones
    componentRestrictions: { country: "ar" }, // Opcional: restringe a un país específico (en este caso, Argentina)
  });

  // Evitar que el mapa se recentre cuando se selecciona un lugar
  autocomplete.bindTo("bounds", map);

  // Configurar el marcador inicial
  marker = new google.maps.Marker({
    map,
  });

  // Evento al seleccionar una sugerencia de Autocomplete
  autocomplete.addListener("place_changed", () => {
    const place = autocomplete.getPlace();
    if (!place.geometry || !place.geometry.location) {
      alert("No se pudo encontrar la ubicación ingresada. Intenta de nuevo.");
      return;
    }

    // Mover el mapa a la ubicación seleccionada
    map.setCenter(place.geometry.location);
    map.setZoom(14); // Ajustar el nivel de zoom
    marker.setPosition(place.geometry.location);
    marker.setMap(map);
  });

  // Configurar los eventos de los botones
  submitButton.addEventListener("click", () => {
    if (inputRadio.value > 0) {
      geocode({ address: inputText.value });
    } else {
      alert("Por favor, ingresa un radio válido (mayor a 0).");
    }
  });

  clearButton.addEventListener("click", () => {
    clear();
  });

  clear();
}

function clear() {
  marker.setMap(null);
}

function geocode(request) {
    clear(); // Limpia los marcadores previos
    geocoder
        .geocode(request)
        .then((result) => {
            const { results } = result;

            // Obtener latitud, longitud y radio ingresados por el usuario
            const latitud = results[0].geometry.location.lat();
            const longitud = results[0].geometry.location.lng();
            const radio = parseInt(inputRadio.value, 10);

            const data = {
                latitud: parseFloat(latitud),
                longitud: parseFloat(longitud),
                radio: radio,
            };

            // Realizar la solicitud GET al endpoint
            fetch(`/ObtenerPuntosRecomendados?latitud=${data.latitud}&longitud=${data.longitud}&radio=${data.radio}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error en la respuesta del servidor');
                    }
                    return response.json();
                })
                .then((puntos) => {
                    if (puntos.length > 0) {
                        // Agrega un marcador para cada punto recomendado con un cartel
                        puntos.forEach((punto) => {
                            if (punto.latitud != null && punto.longitud != null) {
                                const marcador = new google.maps.Marker({
                                    position: { lat: punto.latitud, lng: punto.longitud },
                                    map: map,
                                    title: "Posible Punto de Colocación",
                                    icon: {
                                        url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png",
                                    },
                                });

                                // Usar geocoder para obtener la dirección a partir de las coordenadas
                                geocoder.geocode(
                                    { location: { lat: punto.latitud, lng: punto.longitud } },
                                    (results, status) => {
                                        if (status === "OK" && results[0]) {
                                            const direccion = results[0].formatted_address; // Dirección completa
                                            const infoWindow = new google.maps.InfoWindow({
                                                content: `
                                                    <h4>Posible Punto de Colocación</h4>
                                                    <p>${direccion}</p>
                                                `,
                                            });

                                            // Abrir la ventana de información al hacer clic en el marcador
                                            marcador.addListener("click", () => {
                                                infoWindow.open({
                                                    anchor: marcador,
                                                    map,
                                                });
                                            });
                                        } else {
                                            console.error("No se pudo obtener la dirección:", status);
                                        }
                                    }
                                );
                            } else {
                                console.warn("Punto inválido recibido de la API:", punto);
                            }
                        });

                        // Ajustar los límites del mapa para que incluya todos los puntos recomendados
                        map.fitBounds(calculateBounds(puntos));
                    } else {
                        alert("No se encontraron puntos recomendados dentro del radio.");
                    }
                })
                .catch(error => {
                    console.error('Error al realizar la solicitud:', error);
                });

            return results;
        })
        .catch((e) => {
            alert("Ocurrió un error: " + e);
        });
}

function calculateBounds(points) {
    const bounds = new google.maps.LatLngBounds();
    points.forEach((point) => {
        if (point.latitud != null && point.longitud != null) {
            bounds.extend(new google.maps.LatLng(point.latitud, point.longitud));
        }
    });
    return bounds;
}

window.initMap = initMap;