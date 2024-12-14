document.addEventListener("DOMContentLoaded", () => {
    const botonCodigoTarjeta = document.getElementById("nav-codigo-tarjeta");
    if (botonCodigoTarjeta) {
        botonCodigoTarjeta.addEventListener("click", () => {
            mostrarFormularioDeTarjeta(botonCodigoTarjeta.getAttribute("data-tarjeta"));
        });
    } else {
        console.error("El elemento con id 'nav-codigo-tarjeta' no fue encontrado.");
    }
});

async function mostrarFormularioDeTarjeta(tieneTarjeta) {
    if(tieneTarjeta === "true") {
        alertaSimple("Ya posee una tarjeta", "warning");
        return;
    }

    const {value : codigo} = await Swal.fire({
        title: "Acceso a Heladeras como Colaborador",
        input: "text",
        inputLabel: "Ingrese el código de tarjeta para que pueda abrir las heladeras",
        showCancelButton: true,
        customClass: {
            popup: 'custom-swal-popup',
        },
        inputValidator: (value) => {
            if (!value) {
                return "El código no puede estar vacío";
            }
        },
    });

    if (codigo) {
        console.log("Código ingresado:", codigo);

        aceptarCodigoDeTarjeta(codigo);
    } else {
        console.log("El usuario canceló la operación o no ingresó código.");
    }
}

async function aceptarCodigoDeTarjeta(codigo) {
    try {
        const respuesta = await fetch(`/IngresarTarjetaColaborador`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(codigo),
        });

        if (!respuesta.ok) {
            throw new Error("El código es incorrecto.");
        }

        const mensaje = await respuesta.text();

        alertaSimple(mensaje, "success");
        setTimeout(() => {
            window.location.href = "/Home";
        }, 3000);
    } catch (error) {
        console.error('Error:', error);
        alertaSimple(error.message, "error");
    }
}