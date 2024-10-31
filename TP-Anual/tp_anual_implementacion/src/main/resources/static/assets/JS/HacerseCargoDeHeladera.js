
formularioDeHeladera = document.getElementById('form-hacerse-cargo-heladera');
formularioDeHeladera.addEventListener('submit', function (e) {
    e.preventDefault();

    //const colaboradorACargo = document.getElementById('colaboradorACargo');
    // Dato obtenido de la sesion del colaborador

    const capacidadViandas = document.getElementById('capacidadHeladera');
    const nombreModelo = document.getElementById('modeloHeladera');
    const calle = document.getElementById('direccionHeladera');
    const altura = document.getElementById('altura');
    const codPostal = document.getElementById('codPostal');
    const ciudad = document.getElementById('ciudadHeladera');
    const nombreDelPunto = document.getElementById('nombrePuntoHeladera');
    const puestaEnFuncionamiento = document.getElementById('fechaFuncionamientoHeladera');

    // ---------------- Validacion de campos obligatorios ---------------- //

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

    if (!codPostal.value) {
        hasError = true;
        document.getElementById('codPostalError').innerText = 'El código postal es requerido';
        codPostal.classList.add('error');
    } else {
        document.getElementById('codPostalError').innerText = '';
        codPostal.classList.remove('error');
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

    if (!puestaEnFuncionamiento.value) {
        hasError = true;
        document.getElementById('fechaFuncionamientoHeladeraError').innerText = 'La fecha de puesta en funcionamiento es requerida';
        puestaEnFuncionamiento.classList.add('error');
    } else {
        document.getElementById('fechaFuncionamientoHeladeraError').innerText = '';
        puestaEnFuncionamiento.classList.remove('error');
    }

    if (hasError) {
        return;
    }

    // ---------------- Generacion del objeto Json ---------------- //

    const heladeraData = {
        //colaboradorACargo: colaboradorACargo.value,
        capacidadViandas: capacidadViandas.value,
        nombreModelo: nombreModelo.value,
        calle: calle.value,
        altura: altura.value,
        codPostal: codPostal.value,
        ciudad: ciudad.value,
        nombreDelPunto: nombreDelPunto.value,
        puestaEnFuncionamiento: puestaEnFuncionamiento.value
    };

    // ---------------- Envio del Json al back ---------------- //

    fetch('/FormularioDeHeladera', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(heladeraData),
    })
        .then(response => {
            if (response.ok) {
                alert('Heladera creada exitosamente');
            } else {
                throw new Error(`Error al crear la heladera. Código de estado: ${response.status}`);
            }
        })
        .catch(error => {
            console.error('Hubo un problema con la solicitud:', error);
        });
});