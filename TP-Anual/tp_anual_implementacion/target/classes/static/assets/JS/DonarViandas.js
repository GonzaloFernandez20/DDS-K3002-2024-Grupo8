formularioDonacion = document.getElementById('form-donar-viandas');

// Función para establecer la fecha mínima al día de hoy
function establecerFechaHoy() {
    var hoy = new Date();
    var dd = String(hoy.getDate()).padStart(2, '0'); // Día
    var mm = String(hoy.getMonth() + 1).padStart(2, '0'); // Mes (los meses comienzan en 0)
    var yyyy = hoy.getFullYear(); // Año

    // Formato a YYYY-MM-DD
    hoy = yyyy + '-' + mm + '-' + dd;

    // Establecer el valor mínimo en el input de fecha
    document.getElementById("fechaDeCaducidad").setAttribute("min", hoy);
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
    document.getElementById('fechaDeCaducidad').value = '';
    document.getElementById('peso').value = '';
    document.getElementById('calorias').value = '';
};

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
        modal.classList.add('visible');
    };

    // Función para cerrar el modal
    const closeModal = () => {
        modal.classList.remove('visible');
    };

    // Manejar clic en "Agregar vianda"
    btnAgregarVianda.addEventListener('click', (event) => {
        event.preventDefault();
        maxViandas = parseInt(document.getElementById("cantViandas").value); // Captura la cantidad deseada
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
        const fechaCaducidad = document.getElementById('fechaDeCaducidad').value;
        const peso = document.getElementById('peso').value;
        const calorias = document.getElementById('calorias').value;

        if (tipoDeComida && fechaDeCaducidad) {
            const fechaFormateada = new Date(fechaCaducidad).toISOString().split('T')[0];
            const vianda = {  tipoDeComida, fechaDeCaducidad: fechaFormateada, peso, calorias };
            viandasDTO.push(vianda);

            // Mostrar vianda en la lista HTML
            const li = document.createElement('li');
            li.textContent = `Vianda ${viandasAgregadas + 1}: ${tipoDeComida}, ${peso}, ${calorias}`;
            listaViandasElement.appendChild(li);

            viandasAgregadas++;

            if (viandasAgregadas < maxViandas) {
                limpiarCamposModal();
                closeModal();
                setTimeout(openModal, 200); // Abre el modal después de un breve retraso
            } else {
                ('Has agregado todas las viandas necesarias.');
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

        if (viandasDTO.length !== maxViandas  ) {
            showAlert("Debe agregar información de cada una de las viandas a donar","error" );
            return;
        }

        const heladeraID = document.getElementById("heladera").value;

        const datosDonacion = {
            heladeraID,
            viandasDTO: viandasDTO // Enviar la lista completa de viandas
        };
        fetch('/DonarViandas', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(datosDonacion),
        })
            .then(response => {
                return response.text().then(msjDeRespuesta => {
                    if (!response.ok) {
                        showAlert(msjDeRespuesta, "error");
                    }else{
                        showAlert(msjDeRespuesta, "success");
                        formularioDonacion.reset();
                    }
                });
            })
            .catch(error => {
                console.error('Error:', error);
                alert(error.message);
            });
    });
});
