document.addEventListener("DOMContentLoaded", () => {
    formTarjetas = document.getElementById('solicitarTarjetas');
    formTarjetas.addEventListener('submit', function(e) {
        e.preventDefault();

        let hasError = false;

        const cantTarjetas = document.getElementById('cant-tarjetas');

        if (!cantTarjetas.value) {
            hasError = true;
            document.getElementById('tarjetas-error').innerText = 'Debe seleccionar una cantidad de tarjetas para entregar';
            cantTarjetas.classList.add('error');
        } else {
            document.getElementById('tarjetas-error').innerText = '';
            cantTarjetas.classList.remove('error');
        }

        if (!hasError) {
            fetch("/SolicitarTarjetas", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(cantTarjetas.value),
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
                    alertaSimple(msjDeRespuesta);
                    setTimeout(function() {
                        window.location.href = "/Home";
                    }, 4000);
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert(error.message);
                });
        }
    });
});