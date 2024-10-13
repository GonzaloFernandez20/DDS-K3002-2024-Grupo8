document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("form-dar-de-alta-persona-en-sit-vulnerable");
    
    const nombrePersonaVul = document.getElementById("nombrePersonaVul");
    const fechaNacimientoPersonaVul = document.getElementById("fechaNacimientoPersonaVul");
    const sitViviendaPersonaVul = document.getElementById("sitViviendaPersonaVul");
    const calleDomicilioPersonaVul = document.getElementById("calleDomicilioPersonaVul");
    const alturaDomicilioPersonaVul = document.getElementById("alturaDomicilioPersonaVul");
    const tipoDocumentoPersonaVul = document.getElementById("tipoDocPersonaVul");
    const numeroDocumentoPersonaVul = document.getElementById("numeroDocPersonaVul");
    const tieneMenoresPersonaVul = document.getElementById("tieneMenoresPersonaVul");
    const cantidadMenoresPersonaVul = document.getElementById("cantidadMenoresPersonaVul");
    const tarjetaPersonaVul = document.getElementById("tarjetaPersonaVul");

    calleDomicilioPersonaVul.parentElement.style.display = "none";
    alturaDomicilioPersonaVul.parentElement.style.display = "none";
    numeroDocumentoPersonaVul.parentElement.style.display = "none";
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

    form.addEventListener("submit", function (event) {
        let isValid = true;
        
        if (nombrePersonaVul.value.trim() === "") {
            alert("El nombre es obligatorio.");
            isValid = false;
        }

        if (fechaNacimientoPersonaVul.value === "") {
            alert("La fecha de nacimiento es obligatoria.");
            isValid = false;
        }

        if (sitViviendaPersonaVul.value === "--Seleccione--") {
            alert("Debes seleccionar una situación de vivienda.");
            isValid = false;
        }

        if (tarjetaPersonaVul.value.trim() === "") {
            alert("El código de tarjeta es obligatorio.");
            isValid = false;
        }

        if (tieneMenoresPersonaVul.value === "Sí" && cantidadMenoresPersonaVul.value === "") {
            alert("Debes ingresar la cantidad de menores a cargo.");
            isValid = false;
        }

        if (!isValid) {
            event.preventDefault();
        }
    });
});
