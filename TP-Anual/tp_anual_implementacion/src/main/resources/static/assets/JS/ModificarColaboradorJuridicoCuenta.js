async function guardarCambios() {
    const nuevaRazonSocial = document.getElementById("nuevaRazonSocial");
    const nuevoTipoOrg = document.getElementById("nuevoTipoOrg");
    const nuevoRubro = document.getElementById("nuevoRubro");
    const nuevaCalleJuridico = document.getElementById("nuevaCalleJuridico");
    const nuevaAlturaJuridico = document.getElementById("nuevaAlturaJuridico");
    const nuevoNombreJuridico = document.getElementById("nuevoNombreJuridico");
    const nuevaContraseniaJuridico = document.getElementById("nuevaContraseniaJuridico");
    /*
    const tipoMedioNuevo = document.getElementById("tipoNuevoMedioDeContactoHumano");
    const medioNuevo = document.getElementById("nuevoMedioDeContactoHumano");
    */

    const datosDeUsuario = {
        usuario: nuevoNombreJuridico.value,
        contrasenia: nuevaContraseniaJuridico.value,
        razonSocial: nuevaRazonSocial.value,
        tipoDeOrganizacion: nuevoTipoOrg.value,
        rubro: nuevoRubro.value,
        calle: nuevaCalleJuridico.value,
        altura: nuevaAlturaJuridico.value

        // email: emailCaja.value, TODO: AGREGAR CAMPOS
        // telefono: telefonoCaja.value,
        // whatsapp: whatsappChecked,
    };

    try {
        const response = await fetch('/ModificarColaborador/Juridico', {
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