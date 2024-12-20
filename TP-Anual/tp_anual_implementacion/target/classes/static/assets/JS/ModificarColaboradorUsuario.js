document.addEventListener('DOMContentLoaded', function () {
    const btnBaja = document.getElementById('btn-dar-de-baja');
    if (btnBaja) { // Verificamos que el botón exista
        btnBaja.addEventListener('click', function () {
            Swal.fire({
                title: "¿Está seguro que quiere eliminar su cuenta?",
                text: "Una vez que acepte, no hay vuelta atrás.",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Sí, quiero eliminarla!"
            }).then((result) => {
                if (result.isConfirmed) {
                    solicitarBajaDeUsuario().then(resultado => {
                        if (resultado) {
                            Swal.fire({title: "Confirmado!", text: "Su cuenta ha sido eliminada!", icon: "success"});
                            window.location.href = "/Home";
                        } else {
                            Swal.fire("Lo sentimos!", "Ocurrió un error, intente de nuevo más tarde.", "error");
                        }
                    });
                }
            });
        });
    }
    async function solicitarBajaDeUsuario(){
        try {
            const response = await fetch('/ModificarColaborador/EliminarCuenta', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
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
});

document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.mod-cuenta').forEach(form => {
        form.addEventListener('submit', function (event) {
            event.preventDefault();

            Swal.fire({
                title: "¿Está seguro que desea confirmar los cambios?",
                showDenyButton: true,
                showCancelButton: false,
                confirmButtonText: "Sí, Confirmo",
                denyButtonText: "Mejor no"
            }).then((result) => {
                if (result.isConfirmed) {
                    guardarCambios().then(resultado => {
                        if (resultado) {
                            Swal.fire("Confirmado!", "Los cambios han sido guardados.", "success");
                        } else {
                            Swal.fire("Lo sentimos!", "Ocurrió un error, intente de nuevo más tarde.", "error");
                        }
                    });
                } else if (result.isDenied) {
                    Swal.fire("Los cambios no se guardaron", "", "info");
                }
            });
        });
    });
});


function ocultarReporteFalla() {
    document.getElementById('container-reporte-falla').style.display = 'none';
}

/*
NO EXISTE EN NUESTRO HTML. TODO LO QUE ES HUMANO VA EN MODIFICARCOLABORADORHUMANO. ACÁ VA SÓLO LO QUE SE COMPARTE ENTRE COLABORADORES.

document.getElementById('tipoNuevoMedioDeContactoHumano').addEventListener('change', function () {
    const inputContainer = document.getElementById('inputContainer');
    const input = document.getElementById('nuevoMedioDeContactoHumano');

    if (this.value) {
        inputContainer.style.display = 'block'; // Mostrar el input si hay selección
        input.placeholder = this.value === 'mail' ? 'Ingrese su correo electrónico' : 'Ingrese su número';
    } else {
        inputContainer.style.display = 'none'; // Ocultar el input si no hay selección
    }
});
*/