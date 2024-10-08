class WarningHeladera {
    constructor(nombreDelPunto, ciudad, direccion, tipoDeAlerta, fechaDelIncidente, estado) {
        this.nombreDelPunto = nombreDelPunto;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.tipoDeAlerta = tipoDeAlerta;
        this.fechaDelIncidente = fechaDelIncidente;
        this.estado = estado;
    }
}

class Heladera {
    constructor(nombreDelPunto, ciudad, direccion, modelo, capacidad, fechaDePuestaEnFuncionamiento) {
        this.nombreDelPunto = nombreDelPunto;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.modelo = modelo;
        this.capacidad = capacidad;
        this.fechaDePuestaEnFuncionamiento = fechaDePuestaEnFuncionamiento;
    }
}

// Datos iniciales
let warningHeladeraDeUsuario = [
    new WarningHeladera("Las Violetas", "CABA", "Medrano 5", "Temperatura baja", "23/04/2023", "Resuelto"),
    new WarningHeladera("Las Violetas", "CABA", "Medrano 5", "Temperatura alta", "25/04/2023", "No resuelto"),
    new WarningHeladera("Campus", "CABA", "Mozart 2300", "Mucho movimiento", "25/08/2024", "No resuelto")
];

let heladerasDeUnUsuario = [
    new Heladera("Las Violetas", "CABA", "Medrano 5", "SuperFrio3000", "30", "01/01/2020"),
    new Heladera("Campus", "CABA", "Mozart 2300", "ColdX", "15", "06/07/2008")
];

// Función para iniciar la pantalla y cargar datos
function iniciarPantalla() {
    document.getElementById('mod-heladera').style.display = "none";
    agregarHeladerasConAlerta();
    agregarHeladerasAlListadoGeneral();
}

// Función para agregar las heladeras con alerta
function agregarHeladerasConAlerta() {
    let agregarFilaWarning = '';

    warningHeladeraDeUsuario.forEach(warning => {
        agregarFilaWarning +=
            `<tr>
                <td>${warning.nombreDelPunto}</td>
                <td>${warning.ciudad}</td>
                <td>${warning.direccion}</td>
                <td>${warning.tipoDeAlerta}</td>
                <td>${warning.fechaDelIncidente}</td>
                <td>${warning.estado}</td>
            </tr>`;
    });

    document.getElementById('heladeras-warnings').innerHTML = agregarFilaWarning;
    document.getElementById('non-warning-message').style.display = "none";
    document.getElementById('warnings').style.display = "block";
}

// Función para agregar heladeras al listado general
function agregarHeladerasAlListadoGeneral() {
    let agregarFilaListado = '';

    heladerasDeUnUsuario.forEach((heladera, i) => {
        agregarFilaListado +=
            `<tr>
                <td>${heladera.nombreDelPunto}</td>
                <td>${heladera.ciudad}</td>
                <td>${heladera.direccion}</td>
                <td>${heladera.modelo}</td>
                <td>${heladera.capacidad}</td>
                <td>${heladera.fechaDePuestaEnFuncionamiento}</td>
                <td class="row-button"><button class="listado-button modificar" onclick="modificarHeladera(${i})">
                    <svg height="20px" width="20px">
                        <image height="20px" width="20px" href="img/edit-icon.svg"/>
                    </svg>
                </button></td>
                <td class="row-button"><button class="listado-button eliminar" onclick="eliminarHeladera(${i})">X</button></td>
            </tr>`;
    });

    document.getElementById('user-heladeras').innerHTML = agregarFilaListado;
}

// Función para modificar una heladera
function modificarHeladera(numeroDeHeladera) {
    let heladeraAModificar = heladerasDeUnUsuario[numeroDeHeladera];

    document.getElementById('mod-heladera-title').innerText = heladeraAModificar.nombreDelPunto;
    document.getElementById('nuevaCiudadHeladera').placeholder = heladeraAModificar.ciudad;
    document.getElementById('nuevaDireccionHeladera').placeholder = heladeraAModificar.direccion;
    document.getElementById('nuevoModeloHeladera').placeholder = heladeraAModificar.modelo;
    document.getElementById('nuevaCapacidadHeladera').placeholder = heladeraAModificar.capacidad;

    document.getElementById('mod-heladera').style.display = "block";
}

// Función para eliminar una heladera
function eliminarHeladera(numeroDeHeladera) {
    if (confirm('¿Está seguro que quiere eliminar la heladera ' + heladerasDeUnUsuario[numeroDeHeladera].nombreDelPunto + '?')) {
        heladerasDeUnUsuario.splice(numeroDeHeladera, 1);
        console.log("Se eliminó la heladera...");
        iniciarPantalla();
    }
}

// Evento para modificar la heladera
document.getElementById('mod-heladera').addEventListener('submit', function(event) {
    event.preventDefault();

    if (confirm('¿Está seguro que desea realizar estos cambios?')) {
        let nombreDelPunto = document.getElementById('mod-heladera-title').innerText;
        let nuevaCiudad = document.getElementById('nuevaCiudadHeladera').value;
        let nuevaDireccion = document.getElementById('nuevaDireccionHeladera').value;
        let nuevoModelo = document.getElementById('nuevoModeloHeladera').value;
        let nuevaCapacidad = document.getElementById('nuevaCapacidadHeladera').value;

        let heladeraElegida = heladerasDeUnUsuario.find(heladera => heladera.nombreDelPunto == nombreDelPunto);
        if (nuevaCiudad) heladeraElegida.ciudad = nuevaCiudad;
        if (nuevaDireccion) heladeraElegida.direccion = nuevaDireccion;
        if (nuevoModelo) heladeraElegida.modelo = nuevoModelo;
        heladeraElegida.capacidad = nuevaCapacidad;

        iniciarPantalla();
    }
});

// Función auxiliar para encontrar una heladera por nombre
function heladeraQueTengaEseNombre(nombreDelPunto, index, heladeras) {
    return heladeras[index].nombreDelPunto == nombreDelPunto;
}

iniciarPantalla();
