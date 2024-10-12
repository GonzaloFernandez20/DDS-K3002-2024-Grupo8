/*formularioDistribucion = document.getElementById('distribucionViandaForm');
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

    if (!hasError) {

        alert('Solicitud de Distribucion Realizada');
        formularioDistribucion.reset();
    }
});*/