class Producto {
    constructor(productoId, nombreProducto, rubro, puntosNecesarios, imagen) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.rubro = rubro;
        this.puntosNecesarios = puntosNecesarios;
        this.imagen = imagen;
    }
}

class Imagen {
    constructor(nombre, epigrafe) {
        this.nombre = nombre;
        this.epigrafe = epigrafe;
    }
}

let productos = [new Producto(0, "Campana", "Sonido", 50, new Imagen("img/notifications-icon.svg", "Una campana que ya estaba por ahí.")),
    new Producto(1, "Lámpara blanca", "Iluminación", 2500, null)
];

var containerCanjeo = new Vue({
    el: '#containerCanjeo',
    data: {
        puntos: 2500, //HARDCODEADO, ESTO SE TRAE DEL BACK A PARTIR DE LA SESION
        productos: productos
    },
    methods: {
        conseguirProducto(productoId) {
            if(confirm("¿Está seguro que quiere cambiar los puntos por este producto?")) {
                //ESTO LO HACEMOS EN BACK
                this.puntos -= this.productos[productoId].puntosNecesarios;
                                //
                console.log("Cambió " + productos[productoId].puntosNecesarios + " por el producto " + productos[productoId].nombreProducto);
            };
        }
    }
})