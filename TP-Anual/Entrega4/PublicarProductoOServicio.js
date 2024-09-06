form_productos = document.getElementById('publicarServicioForm');
form_productos.addEventListener('submit', function(e) {
    e.preventDefault();

    let hasError = false;

    const rubro = document.getElementById('rubro');
    const nombre_oferta = document.getElementById('nombre-oferta');
    const cant_puntos = document.getElementById('cant-puntos');

    
    if (!rubro.value) {
        hasError = true;
        document.getElementById('error-rubro').innerText = 'Seleccionar Rubro';
        rubro.classList.add('error');
    } else {
        document.getElementById('error-rubro').innerText = '';
        rubro.classList.remove('error');
    }

    if (nombre_oferta.value == null) {
        hasError = true;
        document.getElementById('error-nombre').innerText = 'Ingresar nombre de la oferta';
        nombre_oferta.classList.add('error');
    } else {
        document.getElementById('error-nombre').innerText = '';
        nombre_oferta.classList.remove('error');
    }

    if (!cant_puntos.value) {
        hasError = true;
        document.getElementById('error-puntos').innerText = 'Ingresar cantidad de puntos';
        cant_puntos.classList.add('error');
    } else {
        document.getElementById('error-puntos').innerText = '';
        cant_puntos.classList.remove('error');
    }

    if (!stock.value) {
        hasError = true;
        document.getElementById('error-stock').innerText = 'Ingrese stock del producto';
        stock.classList.add('error');
    } else {
        document.getElementById('error-stock').innerText = '';
        stock.classList.remove('error');
    }

    if (!hasError) {

        alert('Solicitud de Publicación realizada');
        form_productos.reset();
    }
});