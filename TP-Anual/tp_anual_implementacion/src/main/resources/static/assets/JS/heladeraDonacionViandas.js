function agregarMaximoDeViandasADonar() {
    const heladera = document.getElementById('heladera');
    const opcion = heladera.options[heladera.selectedIndex];
    const capacidadMaximaPorStock = opcion.getAttribute("data-stock");

    console.log(opcion);
    console.log(capacidadMaximaPorStock);
}

document.addEventListener("DOMContentLoaded", () => {
    const IdHeladera = document.getElementById('heladera');

    IdHeladera.addEventListener('change', function(e) {
        console.log("Toqué la heladera");
        if(IdHeladera.value) {
            agregarMaximoDeViandasADonar();
        }
    });

    const formularioDonacion = document.getElementById('form-donar-heladera');
    formularioDonacion.addEventListener('submit', async function(e) {
        e.preventDefault();
        console.log("Un submit");

        // Marcar errores --------------------------------------------------------------
        let hasError = false;

        if (!IdHeladera.value) {
            hasError = true;
            document.getElementById('heladeraError').innerText = 'Seleccionar Heladera';
            IdHeladera.classList.add('error');
        } else {
            document.getElementById('heladeraError').innerText = '';
            IdHeladera.classList.remove('error');
        }

        if(!hasError) {
            //Armado del JSON ----------------------------------------------------------------------------
            const datosDeDonacion = {
                IdHeladeraID: IdHeladera.value
            };

            // Envio del JSON -----------------------------------------------------------------------------
            fetch('/PrevioDonarViandas', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(datosDeDonacion),
            })
                .then(response => {
                    return response.text().then(msjDeRespuesta => {
                        if (!response.ok) {
                            throw new Error(msjDeRespuesta);
                        }
                        return msjDeRespuesta;
                    });
                })
                .then(msjDeRespuesta => {
                    alert(msjDeRespuesta);
                    setTimeout(function() {
                        window.location.href = "/DonarViandas";
                    }, 4000);
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert(error.message);
                });
        }
    });
});