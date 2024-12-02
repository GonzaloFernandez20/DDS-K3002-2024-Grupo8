document.getElementById("form-donar-heladera").addEventListener("submit", function(event) {
    event.preventDefault(); // Evita el comportamiento por defecto del formulario

    // Obtener los valores del formulario
    const campo1 = document.getElementById("heladera").value;

    // Crear el objeto que se va a enviar
    const datos = {
        campo1: campo1
    };

    // Realizar la solicitud fetch
    fetch("/PrevioDonarViandas", {
        method: "POST", // Método de la solicitud
        headers: {
            "Content-Type": "application/json" // Indicamos que el cuerpo de la solicitud es JSON
        },
        body: JSON.stringify(datos) // Convertimos el objeto a una cadena JSON
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error en la solicitud");
            }
            // Enviar un redirect manual después de que la solicitud sea exitosa
            window.location.href = "/DonarViandas"; // Esto redirige a la página de DonarViandas
        })
        .catch(error => {
            console.error("Hubo un error en la solicitud:", error);
        });
});
