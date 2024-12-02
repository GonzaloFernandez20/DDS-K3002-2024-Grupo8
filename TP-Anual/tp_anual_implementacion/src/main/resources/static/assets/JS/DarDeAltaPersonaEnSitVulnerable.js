document.addEventListener("DOMContentLoaded", () => {
    const nombrePersonaVul = document.getElementById("nombrePersonaVul");
    const apellidoPersonaVul = document.getElementById("apellidoPersonaVul");
    const fechaNacimientoPersonaVul = document.getElementById("fechaNacimientoPersonaVul");
    const sitViviendaPersonaVul = document.getElementById("sitViviendaPersonaVul");
    const calleDomicilioPersonaVul = document.getElementById("calleDomicilioPersonaVul");
    const alturaDomicilioPersonaVul = document.getElementById("alturaDomicilioPersonaVul");
    const tipoDocumentoPersonaVul = document.getElementById("tipoDocPersonaVul");
    const numeroDocumentoPersonaVul = document.getElementById("numeroDocPersonaVul");
    const sexoDoc = document.getElementById("sexoDoc");
    const tieneMenoresPersonaVul = document.getElementById("tieneMenoresPersonaVul");
    const cantidadMenoresPersonaVul = document.getElementById("cantidadMenoresPersonaVul");
    const tarjetaPersonaVul = document.getElementById("tarjetaPersonaVul");

    //Campos del formulario que aparecen segun otras opciones seleccionadas -----------------------------------
    calleDomicilioPersonaVul.parentElement.style.display = "none";
    alturaDomicilioPersonaVul.parentElement.style.display = "none";
    numeroDocumentoPersonaVul.parentElement.style.display = "none";
    sexoDoc.parentElement.style.display = "none";
    cantidadMenoresPersonaVul.parentElement.style.display = "none";

    sitViviendaPersonaVul.addEventListener("change", function () {
        if (sitViviendaPersonaVul.value === "POSEE_DOMICILIO") {
            calleDomicilioPersonaVul.parentElement.style.display = "block";
            calleDomicilioPersonaVul.setAttribute("required", "required");
            alturaDomicilioPersonaVul.parentElement.style.display = "block";
            alturaDomicilioPersonaVul.setAttribute("required", "required");
        } else {
            calleDomicilioPersonaVul.parentElement.style.display = "none";
            calleDomicilioPersonaVul.removeAttribute("required");
            alturaDomicilioPersonaVul.parentElement.style.display = "none";
            alturaDomicilioPersonaVul.removeAttribute("required");
        }
    });

    tipoDocumentoPersonaVul.addEventListener("change", function () {
        numeroDocumentoPersonaVul.parentElement.style.display = "block";
        numeroDocumentoPersonaVul.setAttribute("required", "required");
        sexoDoc.parentElement.style.display = "block";
        sexoDoc.setAttribute("required", "required");
    })

    tieneMenoresPersonaVul.addEventListener("change", function () {
        if (tieneMenoresPersonaVul.value === "Sí") {
            cantidadMenoresPersonaVul.parentElement.style.display = "block";
            cantidadMenoresPersonaVul.setAttribute("required", "required");
        } else {
            cantidadMenoresPersonaVul.parentElement.style.display = "none";
            cantidadMenoresPersonaVul.removeAttribute("required");
        }
    });
    document.getElementById("form-dar-de-alta-persona-en-sit-vulnerable").addEventListener("submit", async function (event) {
        // Previene la recarga de la página
        event.preventDefault();
        //Armado del JSON ----------------------------------------------------------------------------
        const datosDeVulnerable = {
            nombre: nombrePersonaVul.value,
            apellido: apellidoPersonaVul.value,
            fechaNacimiento: fechaNacimientoPersonaVul.value,
            tipoDeDocumento: tipoDocumentoPersonaVul.value,
            numeroDocumento: numeroDocumentoPersonaVul.value,
            sexo: sexoDoc.value,
            direccionCalle: calleDomicilioPersonaVul.value,
            direccionAltura: alturaDomicilioPersonaVul.value,
            estadoDeVivienda: sitViviendaPersonaVul.value,
            cantidadMenores: cantidadMenoresPersonaVul.value,
            codigoTarjeta: tarjetaPersonaVul.value
        };

        // Envio del JSON -----------------------------------------------------------------------------
        fetch('/DarDeAltaPersonaEnSitVulnerable', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(datosDeVulnerable),
        })
            .then(response => {
                return response.text().then(msjDeRespuesta => {
                    if (!response.ok) {
                        showAlert(msjDeRespuesta, "error");
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

    });
});