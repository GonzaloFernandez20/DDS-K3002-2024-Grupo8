document.addEventListener("DOMContentLoaded", () => {
    const optionsHeladera = document.getElementById("optionsHeladera");

    const switchDesperfecto = document.getElementById("check-suscripcion-desperfecto");
    const switchFaltanViandas = document.getElementById("check-suscripcion-faltan-viandas");
    const switchQuedanViandas = document.getElementById("check-suscripcion-disponen-viandas");

    const inputCantidadFaltan = document.getElementById("cantidadDeViandasFaltantes");
    const inputCantidadQuedan = document.getElementById("cantidadDeViandasDisponibles");

    // Escucha cambios en el select para actualizar el estado del switch
    optionsHeladera.addEventListener("change", () => {
        const selectedOption = optionsHeladera.options[optionsHeladera.selectedIndex];
        const suscritoFallas = selectedOption.getAttribute("data-suscrito-fallas");
        const faltanViandas = selectedOption.getAttribute("data-cantidad-faltan");
        const quedanViandas = selectedOption.getAttribute("data-cantidad-quedan");

        // Convertir a booleano
        const isSuscritoFallas = suscritoFallas === "true";
        const isFaltanViandas = faltanViandas > 0;
        const isQuedanViandas = quedanViandas > 0;

        // Activar o desactivar el switch basado en el valor
        switchDesperfecto.checked = isSuscritoFallas;
        switchFaltanViandas.checked = isFaltanViandas;
        switchQuedanViandas.checked = isQuedanViandas;

        // Establecer valores iniciales de los inputs
        inputCantidadFaltan.value = isFaltanViandas ? faltanViandas : "Cantidad N con las que quiere ser avisado";
        inputCantidadQuedan.value = isQuedanViandas ? quedanViandas : "Cantidad N con las que quiere ser avisado";
    });

    // Inicializar el estado del switch al cargar el modal (primera opción seleccionada)
    const initialOption = optionsHeladera.options[optionsHeladera.selectedIndex];
    const initialSuscritoFallas = initialOption.getAttribute("data-suscrito-fallas") === "true";
    const initialFaltanViandas = initialOption.getAttribute("data-cantidad-faltan") > 0;
    const initialQuedanViandas = initialOption.getAttribute("data-cantidad-quedan") > 0;

    switchDesperfecto.checked = initialSuscritoFallas;
    switchFaltanViandas.checked = initialFaltanViandas;
    switchQuedanViandas.checked = initialQuedanViandas;

// Establecer valores iniciales de los inputs
    inputCantidadFaltan.value = initialFaltanViandas && (initialFaltanViandas) > 0 ? (initialFaltanViandas) : "Cantidad N con las que quiere ser avisado";
    inputCantidadQuedan.value = initialQuedanViandas && (initialQuedanViandas) > 0 ? (initialQuedanViandas) : "Cantidad N con las que quiere ser avisado";
});

document.addEventListener("DOMContentLoaded", () => {
    const deleteButtons = document.querySelectorAll('.delete-btn');

    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            const id_heladera = this.getAttribute('data-id');
            const evento = this.getAttribute('data-evento');

            // Construir la URL con los parámetros de consulta
            const url = `/EliminarSuscripcion?id_heladera=${encodeURIComponent(id_heladera)}&evento=${encodeURIComponent(evento)}`;

            fetch(url, {
                method: 'POST',  // Mantener el método 'POST'
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.ok) {
                        // Eliminar la fila de la tabla
                        // this.closest('tr').remove();
                    } else {
                        alert('Error al eliminar la suscripción');
                    }
                })
                .catch(error => {
                    alert('Error de conexión');
                });
        });
    });

});


// Validar el formulario antes de enviarlo
document.getElementById("modal-form").addEventListener("submit", function (event) {
    const checkDisponenViandas = document.getElementById("check-suscripcion-disponen-viandas");
    const cantidadDisponibles = document.getElementById("cantidadDeViandasDisponibles");
    const checkFaltanViandas = document.getElementById("check-suscripcion-faltan-viandas");
    const cantidadFaltantes = document.getElementById("cantidadDeViandasFaltantes");
    const checkDesperfecto = document.getElementById("check-suscripcion-desperfecto");

    // Deshabilitar inputs de cantidades si los switches no están activados
    if (!checkDisponenViandas.checked) {
        cantidadDisponibles.disabled = true;
        cantidadDisponibles.value = ""; // Limpiar valor para evitar envíos no deseados
    }

    if (!checkFaltanViandas.checked) {
        cantidadFaltantes.disabled = true;
        cantidadFaltantes.value = ""; // Limpiar valor para evitar envíos no deseados
    }

    // Verificar que al menos un tipo de suscripción esté activo
    if (
        !checkDisponenViandas.checked &&
        !checkFaltanViandas.checked &&
        !checkDesperfecto.checked
    ) {
        event.preventDefault(); // Cancelar el envío del formulario
        alert("Debe seleccionar al menos un tipo de suscripción antes de confirmar.");
    }
});
