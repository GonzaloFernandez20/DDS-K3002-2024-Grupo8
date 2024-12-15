async function guardarCambios() {
    const nombre = document.getElementById("nuevoNombre");
    const apellido = document.getElementById("nuevoApellido");
    const usuario = document.getElementById("nuevoNombreHumano");
    const fechaNacimiento = document.getElementById("fechaNacimiento");
    const tipoDocumento = document.getElementById("tipoDocumento");
    const numeroDocPersona = document.getElementById("nroDocumento");
    const contrasenia = document.getElementById("nuevaContraseniaHumana");
    const calle = document.getElementById("nuevaCalleHumana");
    const altura = document.getElementById("nuevaAlturaHumana");
    const sexo = document.getElementById("sexo");
    /*
    const tipoMedioNuevo = document.getElementById("tipoNuevoMedioDeContactoHumano");
    const medioNuevo = document.getElementById("nuevoMedioDeContactoHumano");
    */

    const datosDeUsuario = {
        usuario: usuario.value,
        contrasenia: contrasenia.value,

        nombre: nombre.value,
        apellido: apellido.value,
        fechaDeNacimiento: fechaNacimiento.value,
        tipo: tipoDocumento.value,
        numero: numeroDocPersona.value,
        sexo: sexo.value,
        calle: calle.value,
        altura: altura.value,

        // email: emailCaja.value, TODO: AGREGAR CAMPOS
        // telefono: telefonoCaja.value,
        // whatsapp: whatsappChecked,
    };

    try {
        const response = await fetch('/ModificarColaborador/Humano', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(datosDeUsuario),
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

/*
NO EXISTEN EN NUESTRO HTML

document.getElementById("tipoNuevoMedioDeContactoHumano").addEventListener("change", function () {
    const medioNuevo = document.getElementById("nuevoMedioDeContactoHumano");
    medioNuevo.parentElement.style.display = "block"; // Mostrar el contenedor del input
    medioNuevo.setAttribute("required", "required"); // Hacer el input requerido
});

document.getElementById("mod-cuenta-humano").addEventListener("submit", function (event) {
    event.preventDefault();

    const medioNuevo = document.getElementById("nuevoMedioDeContactoHumano");

    medioNuevo.parentElement.style.display = "none"; // Ocultar el input tras el envío (opcional)
});
*/





