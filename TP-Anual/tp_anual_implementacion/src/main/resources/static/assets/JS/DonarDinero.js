formularioDonacion = document.getElementById('donacionDineroForm');
formularioDonacion.addEventListener('submit', function(e) {
    e.preventDefault();

    let hasError = false;

    const monto = document.getElementById('montoADonar');
    const fechaDonacion = document.getElementById('fechaDonacion');
    const frecuenciaDonacion = document.getElementById('frecuenciaDonacion');
    const metodoPago = document.getElementById('metodoDePago');


    if (!monto.value) {
        hasError = true;
        document.getElementById('montoError').innerText = 'El monto es requerido';
        monto.classList.add('error');
    } else {
        document.getElementById('montoError').innerText = '';
        monto.classList.remove('error');
    }

    if (!fechaDonacion.value) {
        hasError = true;
        document.getElementById('fechaDonacionError').innerText = 'La fecha es requerida';
        fechaDonacion.classList.add('error');
    } else {
        document.getElementById('fechaDonacionError').innerText = '';
        fechaDonacion.classList.remove('error');
    }

    if (!frecuenciaDonacion.value) {
        hasError = true;
        document.getElementById('frecuenciaDonacionError').innerText = 'Debe seleccionar una frecuencia de colaboracion';
        frecuenciaDonacion.classList.add('error');
    } else {
        document.getElementById('frecuenciaDonacionError').innerText = '';
        frecuenciaDonacion.classList.remove('error');
    }

    if (!metodoPago.value) {
        hasError = true;
        document.getElementById('metodoDePagoError').innerText = 'Debe seleccionar una frecuencia de colaboracion';
        metodoPago.classList.add('error');
    } else {
        document.getElementById('metodoDePagoError').innerText = '';
        metodoPago.classList.remove('error');
    }

    if (!hasError) {

        // Le enviamos los datos al Back

        const donacionData = {
            monto: monto.value,
            fecha: fechaDonacion.value,
            frecuencia: frecuenciaDonacion.value,
            metodoPago: metodoPago.value
        };

        fetch('/ProcesarDonacion', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(donacionData),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error en la red');
                }
                return response.json();
            })
            .then(data => {
                if(data.ok){
                    alert('Donación enviada exitosamente');
                    formularioDonacion.reset();
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Hubo un error al enviar la donación');
            });
    }
});