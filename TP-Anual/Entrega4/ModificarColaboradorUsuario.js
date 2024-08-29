document.querySelectorAll('.baja').forEach(button => {
    button.addEventListener('click', function() {
        if(confirm("¿Está seguro que quiere darse de baja?\n Una vez que haga click en Aceptar, no hay vuelta atrás.")) {
            // DAR DE BAJA EN SISTEMA
            console.log("Se dio de baja al usuario");
        }
    });
});