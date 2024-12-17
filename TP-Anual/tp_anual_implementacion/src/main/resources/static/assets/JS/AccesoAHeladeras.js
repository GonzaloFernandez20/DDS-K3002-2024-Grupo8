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
            body: codigo,
        })
        .then(response => {
                return response.text().then(msjDeRespuesta => {
                    if (!response.ok) {
                        showAlert(msjDeRespuesta, "error");
                    }else{
                        showAlert(msjDeRespuesta, "success");
                        //setTimeout(() => {
                          //  window.location.href = "/Home";
                        //}, 3000);
                    }
                });
            })
    } catch (error) {
        console.error('Error:', error);
        alertaSimple(error.message, "error");
    }
}