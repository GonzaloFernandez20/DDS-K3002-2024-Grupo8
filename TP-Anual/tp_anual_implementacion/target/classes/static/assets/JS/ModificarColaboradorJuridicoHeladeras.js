function mostrarAlertasDeHeladera(idHeladera) {
    let alertas = document.getElementsByClassName('alerta');

    for(let i= 0; i<alertas.length; i++) {
        let alertaDeLaIteracion = alertas[i];
        if(alertaDeLaIteracion.classList.contains('heladera' + idHeladera)) {
            alertaDeLaIteracion.removeAttribute('hidden');
        } else {
            alertas[i].setAttribute('hidden', true);
        }
    }
}

// Función para modificar una heladera
function modificarHeladera(idHeladera) {
    document.getElementById('heladera-title').value = idHeladera;
    document.getElementById('heladera-title').innerText = document.getElementById('nombreDelPunto' + idHeladera).innerText;

    document.getElementById('nuevaCiudadHeladera').value = document.getElementById('ciudad' + idHeladera).innerText;
    document.getElementById('nuevaCapacidadHeladera').value = document.getElementById('capacidad' + idHeladera).innerText;

    let direccion = document.getElementById('direccion' + idHeladera);
    document.getElementById('nuevaCalleHeladera').value = direccion.getAttribute('data-calle');
    document.getElementById('nuevaAlturaHeladera').value = direccion.getAttribute('data-altura');

    let modelo = document.getElementById('modelo' + idHeladera);
    document.getElementById('nuevoModeloHeladera').value = modelo.innerText;
    document.getElementById('nuevaTempMax').value = modelo.getAttribute('data-temp-max');
    document.getElementById('nuevaTempMin').value = modelo.getAttribute('data-temp-min');

    document.getElementById('mod-heladera').style.display = "block";
}

document.addEventListener("DOMContentLoaded", () => {
    const nuevaCiudadHeladera = document.getElementById('nuevaCiudadHeladera');
    const nuevaCalleHeladera = document.getElementById('nuevaCalleHeladera');
    const nuevaAlturaHeladera = document.getElementById('nuevaAlturaHeladera');
    const nuevoModeloHeladera = document.getElementById('nuevoModeloHeladera');
    const nuevaTempMax = document.getElementById('nuevaTempMax');
    const nuevaTempMin = document.getElementById('nuevaTempMin');
    const nuevaCapacidadHeladera = document.getElementById('nuevaCapacidadHeladera');

    const formularioModificacion = document.getElementById('mod-heladera');
    formularioModificacion.addEventListener('submit', async function (e) {
        e.preventDefault();

        const idHeladera = document.getElementById('heladera-title').value;
        const nombreDelPunto = document.getElementById('heladera-title');

        const puestaEnFuncionamiento = document.getElementById('puestaEnFuncionamiento' + idHeladera);

        //Armado del JSON ----------------------------------------------------------------------------
        const datosDeModificacion = {
            idHeladera: idHeladera,
            ciudad: nuevaCiudadHeladera.value,
            calle: nuevaCalleHeladera.value,
            altura: nuevaAlturaHeladera.value,
            nombreModelo: nuevoModeloHeladera.value,
            tempMAXmodelo: nuevaTempMax.value,
            tempMINmodelo: nuevaTempMin.value,
            capacidadViandas: nuevaCapacidadHeladera.value,
            nombreDelPunto: nombreDelPunto.innerText,
            puestaEnFuncionamiento: puestaEnFuncionamiento.innerText
        };

        // Envio del JSON -----------------------------------------------------------------------------
        try {
            const response = await fetch('/ModificarHeladera', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(datosDeModificacion),
            });

            const msjDeRespuesta = await response.text();

            if (!response.ok) {
                throw new Error(msjDeRespuesta);
            }

            // Éxito
            Swal.fire({
                title: "Modificación de la heladera",
                text: msjDeRespuesta,
                icon: "success"
            });

        } catch (error) {
            // Error
            Swal.fire({
                title: "Modificación de la heladera",
                text: error.message,
                icon: "error"
            });
        }
    });
});

// Función para eliminar una heladera
function eliminarHeladera(numeroDeHeladera) {
    Swal.fire({
        title: "¿Está seguro que quiere dar de baja la heladera" + document.getElementById('nombreDelPunto' + numeroDeHeladera).innerText + " ?",
        text: "No podrá revertir este cambio.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Sí, quiero darla de baja"
    }).then((result) => {
        if (result.isConfirmed) {
            darDeBajaUnaHeladera(numeroDeHeladera).then(resultado => {
                if (resultado) {
                    Swal.fire("Confirmado!", "La heladera se dio de baja.", "success");
                } else {
                    Swal.fire("Lo sentimos!", "Ocurrió un error, intente de nuevo más tarde.", "error");
                }
            });
        }
    });
}

async function darDeBajaUnaHeladera(idHeladera) {
    try {
        const response = await fetch('/EliminarHeladera', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(idHeladera),
        });
        if (response.ok) {
            return true;
        } else {
            console.error('Error en la solicitud:', response.status);
            return false;
        }
    } catch (error) {
        console.error('Error de red o conexión:', error);
        return false;
    }
}

function ocultarReporteFalla() {
    document.getElementById('container-reporte-falla').style.display = 'none';
}