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
function modificarHeladera(numeroDeHeladera) {
    document.getElementById('mod-heladera-punto-title').value = numeroDeHeladera;
    document.getElementById('mod-heladera-punto-title').innerText = numeroDeHeladera;

    document.getElementById('mod-heladera').style.display = "block";
}

// Función para eliminar una heladera
function eliminarHeladera(numeroDeHeladera) {
    if (confirm('¿Está seguro que quiere eliminar la heladera ' + heladerasDeUnUsuario[numeroDeHeladera].nombreDelPunto + '?')) {
        heladerasDeUnUsuario.splice(numeroDeHeladera, 1);
        console.log("Se eliminó la heladera...");
        iniciarPantalla();
    }
}

// Función auxiliar para encontrar una heladera por nombre
function heladeraQueTengaEseNombre(nombreDelPunto, index, heladeras) {
    return heladeras[index].nombreDelPunto == nombreDelPunto;
}