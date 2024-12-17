function encontrarUbicacion(calle, altura, ciudad) {
    return new Promise((resolve, reject) => {
        var geocoder = new google.maps.Geocoder();
        geocoder.geocode({ address: `${calle} ${altura}, ${ciudad}` })
            .then(result => {
                const { results } = result;
                const latitud = parseFloat(results[0].geometry.location.lat());
                const longitud = parseFloat(results[0].geometry.location.lng());

                resolve({ latitud, longitud });
            })
            .catch(err => {
                reject(err);
            });
    });
}

formularioDeHeladera = document.getElementById('form-hacerse-cargo-heladera');
formularioDeHeladera.addEventListener('submit', function (e) {
    e.preventDefault();

    //const colaboradorACargo = document.getElementById('colaboradorACargo');
    // Dato obtenido de la sesion del colaborador

    const capacidadViandas = document.getElementById('capacidadHeladera');
    const nombreModelo = document.getElementById('modeloHeladera');
    const calle = document.getElementById('direccionHeladera');
    const altura = document.getElementById('altura');
    const ciudad = document.getElementById('ciudadHeladera');
    const nombreDelPunto = document.getElementById('nombrePuntoHeladera');

    // ---------------- Validación de campos obligatorios ---------------- //

    let hasError = false;

    if (!capacidadViandas.value) {
        hasError = true;
        document.getElementById('capacidadHeladeraError').innerText = 'La capacidad de viandas es requerida';
        capacidadViandas.classList.add('error');
    } else {
        document.getElementById('capacidadHeladeraError').innerText = '';
        capacidadViandas.classList.remove('error');
    }

    if (!nombreModelo.value) {
        hasError = true;
        document.getElementById('modeloHeladeraError').innerText = 'El nombre del modelo es requerido';
        nombreModelo.classList.add('error');
    } else {
        document.getElementById('modeloHeladeraError').innerText = '';
        nombreModelo.classList.remove('error');
    }

    if (!calle.value) {
        hasError = true;
        document.getElementById('direccionHeladeraError').innerText = 'La calle es requerida';
        calle.classList.add('error');
    } else {
        document.getElementById('direccionHeladeraError').innerText = '';
        calle.classList.remove('error');
    }

    if (!altura.value) {
        hasError = true;
        document.getElementById('alturaError').innerText = 'La altura es requerida';
        altura.classList.add('error');
    } else {
        document.getElementById('alturaError').innerText = '';
        altura.classList.remove('error');
    }

    if (!ciudad.value) {
        hasError = true;
        document.getElementById('ciudadHeladeraError').innerText = 'La ciudad es requerida';
        ciudad.classList.add('error');
    } else {
        document.getElementById('ciudadHeladeraError').innerText = '';
        ciudad.classList.remove('error');
    }

    if (!nombreDelPunto.value) {
        hasError = true;
        document.getElementById('nombrePuntoHeladeraError').innerText = 'El nombre del punto es requerido';
        nombreDelPunto.classList.add('error');
    } else {
        document.getElementById('nombrePuntoHeladeraError').innerText = '';
        nombreDelPunto.classList.remove('error');
    }

    if (hasError) {
        return;
    }

    // ---------------- Generación del objeto Json ---------------- //

    encontrarUbicacion(calle.value, altura.value, ciudad.value)
        .then(data => {
            const latitud = data.latitud;
            const longitud = data.longitud;

            // Generación del objeto JSON
            const heladeraData = {
                capacidadViandas: capacidadViandas.value,
                nombreModelo: nombreModelo.value,
                calle: calle.value,
                altura: altura.value,
                ciudad: ciudad.value,
                nombreDelPunto: nombreDelPunto.value,
                latitud: latitud,
                longitud: longitud
            };

            // Envío del JSON al back
            return fetch('/FormularioDeHeladera', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(heladeraData),
            });
        })
        .then(response => {
            if (response.ok) {
                showAlert('Heladera dada alta exitosamente', 'success')
            } else {
                throw new Error(`Error al dar de alta la heladera. Código de estado: ${response.status}`);
            }
        })
        .catch(error => {
            console.error('Hubo un problema con la solicitud:', error);
        });
});