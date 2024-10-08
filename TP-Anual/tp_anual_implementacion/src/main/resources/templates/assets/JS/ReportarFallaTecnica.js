document.getElementById('form-reportar-falla').addEventListener('submit', function(event) {
    event.preventDefault();

    let fechaFalla = document.getElementById('fechaFalla').value;
    let nombrePuntoFalla = document.getElementById('nombrePuntoFalla').value;
    let descripcionFalla = document.getElementById('descripcionFalla').value;
    let fotoFalla = document.getElementById('fotoFalla').value;

    //REPORTAR FALLA EN BACK

    alert("Se reportó exitosamente la falla. ¡Muchas gracias!");
});