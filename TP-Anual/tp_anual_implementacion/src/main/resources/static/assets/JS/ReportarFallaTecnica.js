function showAlert(message, type) {
    // Verifica si alertElement está definido
    const alertElement = document.getElementById('alert-box');
    alertElement.innerText = message;
    alertElement.className = type;  // 'success' o 'error'
    alertElement.style.display = 'block';
}

/*
document.addEventListener('DOMContentLoaded', () => {
    event.preventDefault();

    // Manejar el envío del formulario
    document.getElementById("form-reportar-falla").addEventListener("submit", async function (event) {
        event.preventDefault(); // Previene la recarga de la página

        const heladera = document.getElementById("heladera").value;
        const descripcion = document.getElementById("descripcionFalla").value;

        if (!heladera) {
            alert("Por favor, complete los datos del formulario.");
            return;
        }

        const datosDonacion = {
            descripcion,
            heladera
        };
        console.log(JSON.stringify(datosDonacion, null, 2));

        // Enviar datos al servidor
        try {
            const response = await fetch('/ReportarFallaTecnica', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
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
            showAlert("Error en el envío del reporte de la falla: " + error.message, "error");
        }
    });
});
*/
document.getElementById("form-reportar-falla").addEventListener("submit", async function (event) {
    event.preventDefault(); // Previene la recarga de la página
    /*const formData = {
        heladera : document.getElementById("heladera").value,
        descripcion : document.getElementById("descripcionFalla").value,
        foto : document.getElementById("fotoFalla").files[0] // Obtén el archivo seleccionado
    }*/
    const heladera = document.getElementById('heladera').value;
    const descripcionFalla = document.getElementById('descripcionFalla').value;
    const formData = new FormData();
    formData.append('heladera', heladera); // O el valor dinámico
    formData.append('descripcionFalla', descripcionFalla);
    formData.append('fotoFalla', document.getElementById('fotoFalla').files[0]);

    try {
        const response = await fetch('/ReportarFallaTecnica', {
            method: 'POST',
            body: formData,
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
        showAlert("Error en el envío del reporte de la falla: " + error.message, "error");
    }
});
