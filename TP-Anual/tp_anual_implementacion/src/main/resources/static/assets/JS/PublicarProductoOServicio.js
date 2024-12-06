/*form_productos = document.getElementById('publicarServicioForm');
function validarFormulario() {
    let hasError = false;

    const rubro = document.getElementById('rubro');
    const nombre_oferta = document.getElementById('nombreOferta');
    const nombre_producto = document.getElementById('nombreProducto');
    const cant_puntos = document.getElementById('puntosNecesarios');
    const stock = document.getElementById('stock');
    
    if (!rubro.value) {
        hasError = true;
        document.getElementById('error-rubro').innerText = 'Seleccionar Rubro';
        rubro.classList.add('error');
    } else {
        document.getElementById('error-rubro').innerText = '';
        rubro.classList.remove('error');
    }

    if (nombre_oferta.value === "") {
        hasError = true;
        document.getElementById('error-nombre').innerText = 'Ingresar nombre de la oferta';
        nombre_oferta.classList.add('error');
    } else {
        document.getElementById('error-nombre').innerText = '';
        nombre_oferta.classList.remove('error');
    }

    if (nombre_producto.value === "") {
        hasError = true;
        document.getElementById('error-producto').innerText = 'Ingresar nombre del producto';
        nombre_producto.classList.add('error');
    } else {
        document.getElementById('error-producto').innerText = '';
        nombre_producto.classList.remove('error');
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
}
*/

form_productos.addEventListener('submit', async function(event) {
    event.preventDefault();

    const formData = {
        rubro: document.getElementById('rubro').value,
        nombreOferta: document.getElementById('nombreOferta').value,
        nombreProducto: document.getElementById('nombreProducto').value,
        puntosNecesarios: document.getElementById('puntosNecesarios').value,
        stock: document.getElementById('stock').value,
    };

    try {
        const response = await fetch('/PublicarProductoOServicio', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData),
        });

        const result = await response.json();
        alert(result.mensaje);
    } catch (error) {
        console.error('Error al enviar los datos:', error);
    }
});
