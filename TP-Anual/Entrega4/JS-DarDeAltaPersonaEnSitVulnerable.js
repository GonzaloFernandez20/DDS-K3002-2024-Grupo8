document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("form-dar-de-alta-persona-en-sit-vulnerable");
    
    const nombrePersonaVul = document.getElementById("nombrePersonaVul");
    const fechaNacimientoPersonaVul = document.getElementById("fechaNacimientoPersonaVul");
    const sitViviendaPersonaVul = document.getElementById("sitViviendaPersonaVul");
    const domicilioPersonaVul = document.getElementById("domicilioPersonaVul");
    const tipoDocumentoPersonaVul = document.getElementById("tipoDocPersonaVul");
    const numeroDocumentoPersonaVul = document.getElementById("numeroDocPersonaVul");
    const tieneMenoresPersonaVul = document.getElementById("tieneMenoresPersonaVul");
    const cantidadMenoresPersonaVul = document.getElementById("cantidadMenoresPersonaVul");
    const tarjetaPersonaVul = document.getElementById("tarjetaPersonaVul");

    domicilioPersonaVul.parentElement.style.display = "none";
    numeroDocumentoPersonaVul.parentElement.style.display = "none";
    cantidadMenoresPersonaVul.parentElement.style.display = "none";

    sitViviendaPersonaVul.addEventListener("change", function () {
        if (sitViviendaPersonaVul.value === "Posee domicilio") {
            domicilioPersonaVul.parentElement.style.display = "block";
            domicilioPersonaVul.setAttribute("required", "required");
        } else {
            domicilioPersonaVul.parentElement.style.display = "none";
            domicilioPersonaVul.removeAttribute("required");
        }
    });

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
