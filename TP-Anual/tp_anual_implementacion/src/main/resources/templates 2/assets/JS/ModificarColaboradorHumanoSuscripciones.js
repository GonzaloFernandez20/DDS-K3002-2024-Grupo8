class Suscripcion {
    constructor(suscripcionId, nombrePunto, motivoDeAviso) {
        this.suscripcionId = suscripcionId;
        this.punto = nombrePunto;
        this.suceso = motivoDeAviso;
    }
}

let suscripcionesGenerales = [new Suscripcion(1, 'Heladera 1', 'Faltan 5 viandas'), new Suscripcion(2, 'Heladera 2', 'Quedan 2 viandas'), new Suscripcion(3, 'Heladera 3', 'Hay desperfectos')];

document.getElementById('btn-crear-suscripcion').addEventListener('click', function(event) {
    event.preventDefault();

    let heladeraElegida = document.getElementById('optionsHeladera').value;

    if(document.getElementById('check-suscripcion-disponen-viandas').checked) {
        let cantidadDeViandasDisponibles = document.getElementById('cantidadDeViandasDisponibles').value;

        // CREAR SUSCRIPCIÓN
        suscripcionesGenerales.push(new Suscripcion(4, heladeraElegida, 'Quedan ' + cantidadDeViandasDisponibles + ' viandas'));

        console.log("Quiere suscribirse a cuando dispongan de " + cantidadDeViandasDisponibles + " viandas en la heladera " + heladeraElegida);
    }

    if(document.getElementById('check-suscripcion-faltan-viandas').checked) {
        let cantidadDeViandasFaltantes = document.getElementById('cantidadDeViandasFaltantes').value;

        // CREAR SUSCRIPCIÓN
        suscripcionesGenerales.push(new Suscripcion(5, heladeraElegida, 'Faltan ' + cantidadDeViandasFaltantes + ' viandas'));

        console.log("Quiere suscribirse a cuando falten " + cantidadDeViandasFaltantes + " viandas en la heladera " + heladeraElegida);
    }

    if(document.getElementById('check-suscripcion-desperfecto').checked) {
        // CREAR SUSCRIPCIÓN
        suscripcionesGenerales.push(new Suscripcion(6, heladeraElegida, 'Hay desperfectos'));

        console.log("Quiere suscribirse a cuando hayan desperfectos en la heladera " + heladeraElegida);
    }

    let modal = document.getElementById('modalSuscripcion');
    let bootstrapModal = bootstrap.Modal.getInstance(modal);
    bootstrapModal.hide();

    document.getElementById('modal-form').reset();
});

var bodySubscriptions = new Vue({
    el: '#bodySubscriptions',
    data: {
        suscripciones: suscripcionesGenerales
    }
});

var optionsHeladera = new Vue({
    el: '#optionsHeladera',
    data: {
        heladeras: ['Heladera 1', 'Heladera 2', 'Heladera 3']
    }
});