document.addEventListener("DOMContentLoaded", () => {
    const archivoCSVCarga = document.getElementById('archivoCSVCarga');

    const formularioCargaCSV = document.getElementById('form-carga-masiva');
    formularioCargaCSV.addEventListener('submit', async function (e) {
        e.preventDefault();

        // Envio del JSON -----------------------------------------------------------------------------
        try {
            const response = await fetch('/ModificarHeladera', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(archivoCSVCarga),
            });

            const msjDeRespuesta = await response.text();

            if (!response.ok) {
                throw new Error(msjDeRespuesta);
            }

            // Éxito
            Swal.fire({
                title: "Carga Masiva de Colaboradores",
                text: msjDeRespuesta,
                icon: "success"
            });

        } catch (error) {
            // Error
            Swal.fire({
                title: "Carga Masiva de Colaboradores",
                text: error.message,
                icon: "error"
            });
        }
    });
});