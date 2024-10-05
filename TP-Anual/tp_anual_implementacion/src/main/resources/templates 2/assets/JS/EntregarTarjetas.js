formTarjetas = document.getElementById('entregarTarjetasForm');
formTarjetas.addEventListener('submit', function(e) {
    e.preventDefault();

    let hasError = false;

    const cantTarjetas = document.getElementById('cant-tarjetas');

    
    if (!cantTarjetas.value) {
        hasError = true;
        document.getElementById('tarjetas-error').innerText = 'Seleccionar Heladera de Origen';
        cantTarjetas.classList.add('error');
    } else {
        document.getElementById('tarjetas-error').innerText = '';
        cantTarjetas.classList.remove('error');
    }

    if (!hasError) {

        alert('Solicitud de Tarjetas Realizada');
        formTarjetas.reset();
    }
});