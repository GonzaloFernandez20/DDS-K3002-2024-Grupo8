document.getElementById('heladeraDestino').addEventListener('change', function(e) {
    console.log("Toqué la heladeraDestino");
    if(document.getElementById('heladeraOrigen').value) {
        agregarMaximoDeViandasADistribuir();
    }
});

document.getElementById('heladeraOrigen').addEventListener('change', function(e) {
    console.log("Toqué la heladeraOrigen");
    if(document.getElementById('heladeraDestino').value) {
        agregarMaximoDeViandasADistribuir();
    }
});

function agregarMaximoDeViandasADistribuir() {
    const heladeraOrigen = document.getElementById('heladeraOrigen');
    const opcionOrigen = heladeraOrigen.options[heladeraOrigen.selectedIndex];
    const capacidadMaximaPorStockOrigen = opcionOrigen.getAttribute("data-stock");

    console.log(opcionOrigen);
    console.log(capacidadMaximaPorStockOrigen);

    const heladeraDestino = document.getElementById('heladeraDestino');
    const opcionDestino = heladeraDestino.options[heladeraDestino.selectedIndex];
    const capacidadMaximaPorCapacidadDestino = opcionDestino.getAttribute("data-capacidad");

    console.log(opcionDestino);
    console.log(capacidadMaximaPorCapacidadDestino);

    const cantidadViandas = document.getElementById('cantidadViandas');

    cantidadViandas.max = Math.min(capacidadMaximaPorStockOrigen, capacidadMaximaPorCapacidadDestino);
    cantidadViandas.disabled = false;
}

formularioDistribucion = document.getElementById('distribucionViandaForm');
formularioDistribucion.addEventListener('submit', function(e) {
    e.preventDefault();

    let hasError = false;

    const heladeraOrigen = document.getElementById('heladeraOrigen');
    const heladeraDestino = document.getElementById('heladeraDestino');
    const cantidadViandas = document.getElementById('cantidadViandas');
    const motivoDistribucion = document.getElementById('motivoDistribucion');

    
    if (!heladeraOrigen.value) {
        hasError = true;
        document.getElementById('heladeraOrigenError').innerText = 'Seleccionar Heladera de Origen';
        heladeraOrigen.classList.add('error');
    } else {
        document.getElementById('heladeraOrigenError').innerText = '';
        heladeraOrigen.classList.remove('error');
    }

    if (!heladeraDestino.value) {
        hasError = true;
        document.getElementById('heladeraDestinoError').innerText = 'Seleccionar Heladera de Destino';
        heladeraDestino.classList.add('error');
    } else {
        document.getElementById('heladeraDestinoError').innerText = '';
        heladeraDestino.classList.remove('error');
    }

    if (!cantidadViandas.value) {
        hasError = true;
        document.getElementById('cantidadViandasError').innerText = 'Ingresar cantidad de Viandas a distribuir';
        cantidadViandas.classList.add('error');
    } else {
        document.getElementById('cantidadViandasError').innerText = '';
        cantidadViandas.classList.remove('error');
    }

    if (!motivoDistribucion.value) {
        hasError = true;
        document.getElementById('motivoDistribucionError').innerText = 'Seleccionar Motivo de Distribucion';
        motivoDistribucion.classList.add('error');
    } else {
        document.getElementById('motivoDistribucionError').innerText = '';
        motivoDistribucion.classList.remove('error');
    }

    if(!hasError) {
        //Armado del JSON ----------------------------------------------------------------------------
        const datosDeDistribucion = {
            heladeraDeOrigenID: heladeraOrigen.value,
            heladeraDestinoID: heladeraDestino.value,
            cantidadDeViandas: cantidadViandas.value,
            motivoDeDistribucion: motivoDistribucion.value
        };

        // Envio del JSON -----------------------------------------------------------------------------
        fetch('/DarDeAltaPersonaEnSitVulnerable', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(datosDeDistribucion),
        })
            .then(response => {
                return response.text().then(msjDeRespuesta => {
                    if (!response.ok) {
                        throw new Error(msjDeRespuesta);
                    }
                    return msjDeRespuesta;
                });
            })
            .then(msjDeRespuesta => {
                showAlert(msjDeRespuesta, "success");
                setTimeout(function() {
                    window.location.href = "/Home";
                }, 4000);
            })
            .catch(error => {
                console.error('Error:', error);
                alert(error.message);
            });
    }
});