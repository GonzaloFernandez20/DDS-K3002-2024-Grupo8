// Función para establecer la fecha mínima al día de hoy
function establecerFechaHoy() {
    var hoy = new Date();
    var dd = String(hoy.getDate()).padStart(2, '0'); // Día
    var mm = String(hoy.getMonth() + 1).padStart(2, '0'); // Mes (los meses comienzan en 0)
    var yyyy = hoy.getFullYear(); // Año

    // Formato a YYYY-MM-DD
    hoy = yyyy + '-' + mm + '-' + dd;

    // Establecer el valor mínimo en el input de fecha
    document.getElementById("fechaCaducidad").setAttribute("min", hoy);
    document.getElementById("fechaDonacion").setAttribute("min", hoy);
}

// Mostrar viandas en la interfaz
function actualizarListaDeViandas() {
    const listaContainer = document.getElementById("lista-viandas");
    listaContainer.innerHTML = ""; // Limpiar la lista

    viandas.forEach((vianda, index) => {
        listaContainer.innerHTML += `<li>Vianda ${index + 1}: ${vianda.tipoDeVianda}, ${vianda.peso} kg, ${vianda.calorias} cal.</li>`;
    });
}

// Función para limpiar los campos del modal
const limpiarCamposModal = () => {
    document.getElementById('tipoDeVianda').value = '';
    document.getElementById('fechaCaducidad').value = '';
    document.getElementById('peso').value = '';
    document.getElementById('calorias').value = '';
    document.getElementById('estado').selectedIndex = 0; // Reinicia el select
};

function showAlert(message, type) {
    // Verifica si alertElement está definido
    const alertElement = document.getElementById('alert-box');
    alertElement.innerText = message;
    alertElement.className = type;  // 'success' o 'error'
    alertElement.style.display = 'block';
}


document.addEventListener('DOMContentLoaded', () => {
    event.preventDefault();

    let maxViandas = 0;
    let viandasAgregadas = 0;
    const viandasDTO = [];  // Array para almacenar las viandas

    const btnAgregarVianda = document.getElementById('btn-agregar-vianda');
    const modal = document.getElementById('modal-vianda');
    const btnGuardarVianda = document.getElementById('guardar-vianda');
    const listaViandasElement = document.getElementById('lista-viandas');
    const closeBtn = document.querySelector('.close');
    establecerFechaHoy();

    // Función para abrir el modal
    const openModal = () => {
        limpiarCamposModal();  // Limpia los campos
        modal.style.display = 'block';
    };

    // Función para cerrar el modal
    const closeModal = () => {
        modal.style.display = 'none';
    };

    // Manejar clic en "Agregar vianda"
    btnAgregarVianda.addEventListener('click', () => {
        event.preventDefault();
        maxViandas = parseInt(document.getElementById("cantViandas").value); // Captura la cantidad deseada
        console.log("Cantidad máxima de viandas:", maxViandas); // Verificar el valor
        if (isNaN(maxViandas) || maxViandas <= 0) {
            alert("Por favor, ingrese una cantidad válida de viandas.");
            return;
        }
        viandasAgregadas = 0;  // Reinicia contador si se inicia una nueva donación
        viandasDTO.length = 0; // Vacía la lista anterior
        listaViandasElement.innerHTML = ''; // Limpia la lista visible
        openModal();
    });

    // Guardar vianda
    btnGuardarVianda.addEventListener('click', () => {
        event.preventDefault();
        const tipoDeComida = document.getElementById('tipoDeVianda').value;
        const fechaCaducidad = document.getElementById('fechaCaducidad').value;
        const peso = document.getElementById('peso').value;
        const calorias = document.getElementById('calorias').value;
        const estado = document.getElementById('estado').value;

        if (tipoDeComida && fechaCaducidad && peso && calorias && estado) {
            const vianda = { tipoDeComida, fechaCaducidad, peso, calorias, estado };
            viandasDTO.push(vianda);

            // Mostrar vianda en la lista HTML
            const li = document.createElement('li');
            li.textContent = `Vianda ${viandasAgregadas + 1}: ${tipoDeComida}, ${peso}, ${calorias}`;
            listaViandasElement.appendChild(li);

            viandasAgregadas++;

            if (viandasAgregadas < maxViandas) {
                printf("quedan por agregar " + viandasAgregadas + " viandas");
                limpiarCamposModal();
                closeModal();
                setTimeout(openModal, 200); // Abre el modal después de un breve retraso
            } else {
                alert('Has agregado todas las viandas necesarias.');
                closeModal();
            }
        } else {
            alert('Por favor completa todos los campos.');
        }
    });

    // Cerrar modal manualmente
    closeBtn.addEventListener('click', closeModal);

    // Manejar el envío del formulario
    document.getElementById("form-donar-viandas").addEventListener("submit", async function (event) {
        event.preventDefault(); // Previene la recarga de la página
        establecerFechaHoy();

        if (viandasDTO.length !== maxViandas) {
            alert(`Debe agregar exactamente ${maxViandas} viandas antes de enviar.`);
            return;
        }

        /*if (!heladeraID || !fechaDonacion) {
            alert("Por favor, complete los datos del formulario.");
            return;
        }*/
        const heladeraID = document.getElementById("heladera").value;
        const fechaDonacion = document.getElementById("fechaDonacion").value;

        const datosDonacion = {
            heladeraID,
            fechaDonacion,
            viandasDTO: viandasDTO // Enviar la lista completa de viandas
        };
        console.log(JSON.stringify(datosDonacion, null, 2));

        // Enviar datos al servidor
        try {
            const response = await fetch('/DonarViandas', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(datosDonacion),
            });

            const msjDeRespuesta = await response.text();
            console.log('Estado de la respuesta:', response.status);
            console.log('Texto de la respuesta:', msjDeRespuesta);

            if (!response.ok) {
                throw new Error(msjDeRespuesta);
            }

            showAlert(msjDeRespuesta, "success");
            setTimeout(function() {
                window.location.href = "/Home";
            }, 4000);
        } catch (error) {
            console.error('Error:', error);
            showAlert("Error en el envío de la donación: " + error.message, "error");
        }
    });
});
