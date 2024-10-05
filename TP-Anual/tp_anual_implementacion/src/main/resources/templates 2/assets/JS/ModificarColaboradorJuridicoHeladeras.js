class warningHeladera {
    constructor(nombreDelPunto, ciudad, direccion, tipoDeAlerta, fechaDelIncidente, estado) {
        this.nombreDelPunto = nombreDelPunto;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.tipoDeAlerta = tipoDeAlerta;
        this.fechaDelIncidente = fechaDelIncidente;
        this.estado = estado;
    }
}

class heladera{
    constructor(nombreDelPunto, ciudad, direccion, modelo, capacidad, fechaDePuestaEnFuncionamiento) {
        this.nombreDelPunto = nombreDelPunto;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.modelo = modelo;
        this.capacidad = capacidad;
        this.fechaDePuestaEnFuncionamiento = fechaDePuestaEnFuncionamiento;
    }
}

let warningHeladeraDeUsuario = [new warningHeladera("Las Violetas", "CABA", "Medrano 5", "Temperatura baja", "23/04/2023", "Resuelto")];
let heladerasDeUnUsuario = [new heladera("Las Violetas", "CABA", "Medrano 5", "SuperFrio3000", "30", "01/01/2020"), new heladera("Campus", "CABA", "Mozart 2300", "ColdX", "15", "06/07/2008")];

function iniciarPantalla() {
    document.getElementById('mod-heladera').style.display = "none";
    agregarHeladerasConAlerta();
    agregarHeladerasAlListadoGeneral();
}

function agregarHeladerasConAlerta() {
    let agregarFilaWarning= document.getElementById('heladeras-warnings').innerHTML;

    agregarFilaWarning = '';

    for(let i=0; i < warningHeladeraDeUsuario.length; i++) {
        let nombreDelPunto = warningHeladeraDeUsuario[i].nombreDelPunto;
        let ciudad = warningHeladeraDeUsuario[i].ciudad;
        let direccion = warningHeladeraDeUsuario[i].direccion;
        let tipoDeAlerta = warningHeladeraDeUsuario[i].tipoDeAlerta;
        let fechaDelIncidente = warningHeladeraDeUsuario[i].fechaDelIncidente;
        let estado = warningHeladeraDeUsuario[i].estado;
        `${nombreDelPunto} ${ciudad} ${direccion} ${tipoDeAlerta} ${fechaDelIncidente} ${estado}`;
        
        agregarFilaWarning +=
        `<tr>
            <td>${nombreDelPunto}</td>
            <td>${ciudad}</td>
            <td>${direccion}</td>
            <td>${tipoDeAlerta}</td>
            <td>${fechaDelIncidente}</td>
            <td>${estado}</td>
        </tr>`
    }

    document.getElementById('heladeras-warnings').innerHTML = agregarFilaWarning;
    document.getElementById('non-warning-message').style.display = "none";
    document.getElementById('warnings').style.display = "block";
}

function agregarHeladerasAlListadoGeneral() {
    let agregarFilaListado = document.getElementById('user-heladeras').innerHTML;

    agregarFilaListado = '';

    for(let i=0; i < heladerasDeUnUsuario.length; i++) {
        let nombreDelPunto = heladerasDeUnUsuario[i].nombreDelPunto;
        let ciudad = heladerasDeUnUsuario[i].ciudad;
        let direccion = heladerasDeUnUsuario[i].direccion;
        let modelo = heladerasDeUnUsuario[i].modelo;
        let capacidad = heladerasDeUnUsuario[i].capacidad;
        let fechaDePuestaEnFuncionamiento = heladerasDeUnUsuario[i].fechaDePuestaEnFuncionamiento;
        `${nombreDelPunto} ${ciudad} ${direccion} ${modelo} ${capacidad} ${fechaDePuestaEnFuncionamiento}`;
        
        agregarFilaListado +=
        `<tr>
            <td>${nombreDelPunto}</td>
            <td>${ciudad}</td>
            <td>${direccion}</td>
            <td>${modelo}</td>
            <td>${capacidad}</td>
            <td>${fechaDePuestaEnFuncionamiento}</td>
            <td class="row-button"><button class="listado-button modificar" onclick="modificarHeladera(${i})">
                <svg height="20px" width="20px">
                    <image height="20px" width="20px" href="img/edit-icon.svg"/>
                </svg>
            </button></td>
            <td class="row-button"><button class="listado-button eliminar" onclick="eliminarHeladera(${i})">X</button></td>
        </tr>`
    }

    document.getElementById('user-heladeras').innerHTML = agregarFilaListado;
}

function modificarHeladera(numeroDeHeladera) {
    let heladeraAModificar = heladerasDeUnUsuario[numeroDeHeladera];

    document.getElementById('mod-heladera-title').innerHTML = heladeraAModificar.nombreDelPunto;
    document.getElementById('nuevaCiudadHeladera').placeholder = heladeraAModificar.ciudad;
    document.getElementById('nuevaDireccionHeladera').placeholder = heladeraAModificar.direccion;
    document.getElementById('nuevoModeloHeladera').placeholder = heladeraAModificar.modelo;
    document.getElementById('nuevaCapacidadHeladera').placeholder = heladeraAModificar.capacidad;

    document.getElementById('mod-heladera').style.display = "block";
}

function eliminarHeladera(numeroDeHeladera) {
    if(confirm('¿Está seguro que quiere eliminar la heladera ' + heladerasDeUnUsuario[numeroDeHeladera].nombreDelPunto + '?')){
        //ELIMINAR HELADERA
        let heladeraAEliminar = heladerasDeUnUsuario.splice(numeroDeHeladera, 1);
        console.log("Se elimino la heladera...");
    }
    iniciarPantalla();
}

document.getElementById('mod-heladera').addEventListener('submit', function(event) {
    event.preventDefault();    
    
    if(confirm('¿Está seguro que le quiere realizar estos cambios a la heladera ' + document.getElementById('mod-heladera-title').innerText + '?')) {
        let nombreDelPunto = document.getElementById('mod-heladera-title').innerText;
        let nuevaCiudad = document.getElementById('nuevaCiudadHeladera').value;
        let nuevaDireccion = document.getElementById('nuevaDireccionHeladera').value;
        let nuevoModelo = document.getElementById('nuevoModeloHeladera').value;
        let nuevaCapacidad = document.getElementById('nuevaCapacidadHeladera').value;

        let heladeraElegida = heladerasDeUnUsuario.find(heladera => heladera.nombreDelPunto == nombreDelPunto);
        heladeraElegida.ciudad = nuevaCiudad;
        heladeraElegida.direccion = nuevaDireccion;
        heladeraElegida.modelo = nuevoModelo;
        heladeraElegida.capacidad = nuevaCapacidad;
        
        // Si hubo una alerta en una heladera, se mantiene la información que estaba antes en esa heladera
        /*
        let heladerasElegidasWarning = warningHeladeraDeUsuario.filter(heladera => heladera.nombreDelPunto == nombreDelPunto);
        if(heladerasElegidasWarning.length != 0) {
            for(let i=0; i<heladerasElegidasWarning.length; i++) {
                heladerasElegidasWarning[i].ciudad = nuevaCiudad;
                heladerasElegidasWarning[i].direccion = nuevaDireccion;
            }
        }
        */

        iniciarPantalla();
    }
})

function heladeraQueTengaEseNombre(nombreDelPunto, index, heladeras) {
    return heladeras[index].nombreDelPunto == nombreDelPunto;
}