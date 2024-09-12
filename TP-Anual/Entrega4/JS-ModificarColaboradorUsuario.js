document.querySelectorAll('.baja').forEach(button => {
    button.addEventListener('click', function() {
        if(confirm("¿Está seguro que quiere darse de baja?\n Una vez que haga click en Aceptar, no hay vuelta atrás.")) {
            // DAR DE BAJA EN SISTEMA
            console.log("Se dio de baja al usuario");
        }
    });
});

function habilitarInputIdentificacion(tipo) {
    if(tipo=="humano") {
        document.getElementById('nuevoMedioDeContactoHumano').removeAttribute("disabled");
    } else {
        document.getElementById('nuevoMedioDeContacto').removeAttribute("disabled");
    }
}

document.querySelectorAll('.mod-cuenta').forEach(input => {
    input.addEventListener('submit', function() {
        if(!confirm("¿Está seguro que quiere realizar estas modificaciones?")) {
            return;
        }    
    
        let nombreNuevo = document.getElementsByClassName('mod-cuenta-nombre').value;
        let contraseniaNueva = document.getElementsByClassName('mod-cuenta-contrasenia').value;
        let direccionNueva = document.getElementsByClassName('mod-cuenta-direccion').value;
        let tipoMedioNuevo = document.getElementsByClassName('mod-cuenta-tipo-medio').value;
        let medioNuevo = document.getElementsByClassName('mod-cuenta-medio').value;
    
        // Modificar al usuario
    })
})

function ocultarReporteFalla() {
    document.getElementById('container-reporte-falla').style.display = 'none';
}