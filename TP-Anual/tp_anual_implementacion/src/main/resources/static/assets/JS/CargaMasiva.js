document.addEventListener("DOMContentLoaded", () => {
    const archivoCSVCarga = document.getElementById('archivoCSVCarga');

    const formularioCargaCSV = document.getElementById('form-carga-masiva');
    formularioCargaCSV.addEventListener('submit', async function (e) {
        e.preventDefault();

        try {
            console.log("Intento enviar el archivo");

            const formData = new FormData();
            formData.append("archivoCSVCarga", archivoCSVCarga.files[0]);

            console.log("FormData armado");

            const response = await fetch('/CargaMasiva', {
                method: 'POST',
                body: formData,
            });

            console.log("Hice el fetch");

            const msjDeRespuesta = await response.text();

            if (!response.ok) {
                throw new Error(msjDeRespuesta);
            }

            console.log("Sobreviví la respuesta");

            // Éxito
            Swal.fire({
                title: "Carga Masiva de Colaboradores",
                text: msjDeRespuesta,
                icon: "success"
            });

            console.log("Tuve éxito");

        } catch (error) {
            // Error
            console.log("Hubo un error");
            Swal.fire({
                title: "Carga Masiva de Colaboradores",
                text: error.message,
                icon: "error"
            });
        }
    });
});