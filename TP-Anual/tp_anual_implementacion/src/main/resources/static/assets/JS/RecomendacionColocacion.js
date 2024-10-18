/**
 * @license
 * Copyright 2019 Google LLC. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */
let map;
let marker;
let geocoder;
let responseDiv;
let response;

let inputRadio
let inputText

function initMap() {
  map = new google.maps.Map(document.getElementById("map"), {
    zoom: 14,
    center: { lat: -34.598481538332464, lng: -58.420170648034336 },
    mapTypeControl: false,
  });
  geocoder = new google.maps.Geocoder();

  inputText = document.createElement("input");


  inputText.type = "text";
  inputText.placeholder = "Ingresar Punto de Referencia";

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
    "<strong>Instrucciones</strong>: Ingresa un Punto de Referencia, un Radio, y te recomendamos un posible Punto de Colocación de Heladeras";
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(inputText);
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(inputRadio);
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(submitButton);
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(clearButton);
  map.controls[google.maps.ControlPosition.LEFT_TOP].push(
    instructionsElement
  );
  map.controls[google.maps.ControlPosition.LEFT_TOP].push(responseDiv);
  marker = new google.maps.Marker({
    map,
  });
  map.addListener("click", (e) => {
    geocode({ location: e.latLng });
  });
  submitButton.addEventListener("click", () =>
    geocode({ address: inputText.value })
  );
  clearButton.addEventListener("click", () => {
    clear();
  });
  clear();
}

function clear() {
  marker.setMap(null);
}

function geocode(request) {
    const infoWindow = new google.maps.InfoWindow();
    clear();
    geocoder
        .geocode(request)
        .then((result) => {
        const { results } = result;

        map.setCenter(results[0].geometry.location);
        marker.setPosition(results[0].geometry.location);
        marker.setMap(map);
        
        var titulo = "<h4>Posible Punto de Colocación</h4>";
        infoWindow.setContent(titulo + results[0].formatted_address);

        infoWindow.open({anchor: marker});

        // Obtener latitud y longitud
        const latitud = results[0].geometry.location.lat();
        const longitud = results[0].geometry.location.lng();

        // Obtener el radio ingresado
        const radio = inputRadio.value;

        const data = {
            latitud: parseFloat(latitud),
            longitud: parseFloat(longitud),
            radio: parseInt(radio),
        };

        // Realizar la solicitud POST
        fetch('/ObtenerPuntosRecomendados', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error en la respuesta del servidor');
                }
                return response.json();
            })
            .then(data => {
                //console.log('Puntos recomendados:', data);
                // Logica para mostrar los puntos en el mapa
            })
            .catch(error => {
                console.error('Error al realizar la solicitud:', error);
            });


        return results;
    })
    .catch((e) => {
        alert("Ocurrio un error: " + e);
    });
}

window.initMap = initMap;