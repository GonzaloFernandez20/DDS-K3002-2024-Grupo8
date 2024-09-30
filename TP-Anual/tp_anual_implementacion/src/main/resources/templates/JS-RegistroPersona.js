document.getElementById('registroForm').addEventListener('submit', function(e) {
    e.preventDefault();

    let hasError = false;

    const usuario = document.getElementById('usuario');
    const contrasena = document.getElementById('contrasena');
    const tipoColaborador = document.getElementById('tipoColaborador');
    
    if (!usuario.value) {
        hasError = true;
        document.getElementById('usuarioError').innerText = 'El usuario es requerido';
        usuario.classList.add('error');
    } else {
        document.getElementById('usuarioError').innerText = '';
        usuario.classList.remove('error');
    }

    if (!contrasena.value) {
        hasError = true;
        document.getElementById('contrasenaError').innerText = 'La contraseña es requerida';
        contrasena.classList.add('error');
    } else {
        document.getElementById('contrasenaError').innerText = '';
        contrasena.classList.remove('error');
    }

    if (!tipoColaborador.value) {
        hasError = true;
        document.getElementById('tipoColaboradorError').innerText = 'Debe seleccionar un tipo de colaborador';
        tipoColaborador.classList.add('error');
    } else {
        document.getElementById('tipoColaboradorError').innerText = '';
        tipoColaborador.classList.remove('error');
    }

    if (!hasError) {// Mostrar la pestaña correspondiente al tipo de colaborador seleccionado
        const extraFormContainerJuridico = document.getElementById('extraFormContainerJuridico');
        const extraFormContainerHumano = document.getElementById('extraFormContainerHumano');
        
        if (tipoColaborador.value === 'juridico') {
            extraFormContainerJuridico.style.display = 'block';
            extraFormContainerHumano.style.display = 'none';
            
        } else if (tipoColaborador.value === 'humano') {
            extraFormContainerHumano.style.display = 'block';
            extraFormContainerJuridico.style.display = 'none';
        }
    }
});

// JURIDICO
document.getElementById('extraFormContainerJuridico').addEventListener('submit', function(e) {
    e.preventDefault();
    
    let hasError = false;

    const razonSocial = document.getElementById('razonSocial');
    const rubro = document.getElementById('rubro');
    const tipoOrganizacion = document.getElementById('tipoOrganizacion');
    const direccionJ = document.getElementById('direccionJ'); 

    const emailCaja = document.getElementById('emailCajaJ');
    const email = document.getElementById('emailIngresadoJ');

    const telefonoCaja = document.getElementById('telefonoCajaJ');
    const telefono = document.getElementById('telefonoIngresadoJ');

    const correoCaja = document.getElementById('correoCajaJ');
    const correo = document.getElementById('correoIngresadoJ');

    if (!razonSocial.value) {
        hasError = true;
        document.getElementById('razonSocialError').innerText = 'La razón social es requerida';
        razonSocial.classList.add('error');
    } else {
        document.getElementById('razonSocialError').innerText = '';
        razonSocial.classList.remove('error');
    }

    if (!rubro.value) {
        hasError = true;
        document.getElementById('rubroError').innerText = 'El rubro es requerido';
        rubro.classList.add('error');
    } else {
        document.getElementById('rubroError').innerText = '';
        rubro.classList.remove('error');
    }

    if (!tipoOrganizacion.value) {
        hasError = true;
        document.getElementById('tipoOrganizacionError').innerText = 'Debe seleccionar un tipo de organización';
        tipoOrganizacion.classList.add('error');
    } else {
        document.getElementById('tipoOrganizacionError').innerText = '';
        tipoOrganizacion.classList.remove('error');
    }

    if (checkboxEmailJ.checked){
        if (!email.value) {
            hasError = true;
            document.getElementById('emailErrorJ').innerText = 'Debe introducir un Email valido';
            emailCaja.classList.add('error');
        } else {
            document.getElementById('emailErrorJ').innerText = '';
            emailCaja.classList.remove('error');
        }
    }

    if (checkboxTelefonoJ.checked){
        if (!telefono.value) {
            hasError = true;
            document.getElementById('telefonoErrorJ').innerText = 'Debe introducir un Telefono valido';
            telefonoCaja.classList.add('error');
        } else {
            document.getElementById('telefonoErrorJ').innerText = '';
            telefonoCaja.classList.remove('error');
        }
    }

    if (checkboxCorreoJ.checked){
        if (!correo.value) {
            hasError = true;
            document.getElementById('correoErrorJ').innerText = 'Debe introducir una Direccion valida';
            correoCaja.classList.add('error');
        } else {
            document.getElementById('correoErrorJ').innerText = '';
            correoCaja.classList.remove('error');
        }
    }

    if(!(checkboxCorreoJ.checked || checkboxEmailJ.checked || checkboxTelefonoJ.checked)){
        hasError = true;
        document.getElementById('contactoErrorJ').innerText = 'Debe seleccionar al menos un Medio de Contacto';
    }
    else {
        document.getElementById('contactoErrorJ').innerText = '';
    }

    if (!hasError) {
        alert('Formulario de colaborador Juridico enviado correctamente');
        location.href="InicioDeSesion.html";
    }
});

// HUMANO
document.getElementById('extraFormContainerHumano').addEventListener('submit', function(e) {
    e.preventDefault();
    
    let hasError = false;

    const nombre = document.getElementById('nombre');
    const apellido = document.getElementById('apellido');
    const fechaNacimiento = document.getElementById('fechaNacimiento');
    const direccionH = document.getElementById('direccionH');

    const emailCaja = document.getElementById('emailCajaH');
    const email = document.getElementById('emailIngresadoH');

    const telefonoCaja = document.getElementById('telefonoCajaH');
    const telefono = document.getElementById('telefonoIngresadoH');

    const correoCaja = document.getElementById('correoCajaH');
    const correo = document.getElementById('correoIngresadoH');

    if (!nombre.value) {
        hasError = true;
        document.getElementById('nombreError').innerText = 'El nombre es requerido';
        nombre.classList.add('error');
    } else {
        document.getElementById('nombreError').innerText = '';
        nombre.classList.remove('error');
    }

    if (!apellido.value) {
        hasError = true;
        document.getElementById('apellidoError').innerText = 'El apellido es requerido';
        apellido.classList.add('error');
    } else {
        document.getElementById('apellidoError').innerText = '';
        apellido.classList.remove('error');
    }  

    if (checkboxEmailH.checked){
        if (!email.value) {
            hasError = true;
            document.getElementById('emailErrorH').innerText = 'Debe introducir un Email valido';
            emailCaja.classList.add('error');
        } else {
            document.getElementById('emailErrorH').innerText = '';
            emailCaja.classList.remove('error');
        }
    }

    if (checkboxTelefonoH.checked){
        if (!telefono.value) {
            hasError = true;
            document.getElementById('telefonoErrorH').innerText = 'Debe introducir un Telefono valido';
            telefonoCaja.classList.add('error');
        } else {
            document.getElementById('telefonoErrorH').innerText = '';
            telefonoCaja.classList.remove('error');
        }
    }

    if (checkboxCorreoH.checked){
        if (!correo.value) {
            hasError = true;
            document.getElementById('correoErrorH').innerText = 'Debe introducir una Direccion valida';
            correoCaja.classList.add('error');
        } else {
            document.getElementById('correoErrorH').innerText = '';
            correoCaja.classList.remove('error');
        }
    }

    if(!(checkboxCorreoH.checked || checkboxEmailH.checked || checkboxTelefonoH.checked)){
        hasError = true;
        document.getElementById('contactoErrorH').innerText = 'Debe seleccionar al menos un Medio de Contacto';
    }
    else {
        document.getElementById('contactoErrorH').innerText = '';
    }

    if (!hasError) {
        alert('Formulario de colaborador Humano enviado correctamente');
        location.href="InicioDeSesion.html";
    }
});

var checkboxEmailJ = document.getElementById("medioContactoEmailJ");
checkboxEmailJ.addEventListener('change', function(e) {
    const emailCaja = document.getElementById('emailCajaJ');

    if (this.checked){
        emailCaja.style.display = 'inline';
    }
    else{
        emailCaja.style.display = 'none';
    }
});

var checkboxEmailH = document.getElementById("medioContactoEmailH");
checkboxEmailH.addEventListener('change', function(e) {
    const emailCaja = document.getElementById('emailCajaH');

    if (this.checked){
        emailCaja.style.display = 'inline';
    }
    else{
        emailCaja.style.display = 'none';
    }
});

var checkboxTelefonoJ = document.getElementById("medioContactoTelefonoJ");
checkboxTelefonoJ.addEventListener('change', function(e) {
    const telefonoCaja = document.getElementById('telefonoCajaJ');
    
    if (this.checked){
        telefonoCaja.style.display = 'inline';
    }
    else{
        telefonoCaja.style.display = 'none';
    }
});

var checkboxTelefonoH = document.getElementById("medioContactoTelefonoH");
checkboxTelefonoH.addEventListener('change', function(e) {
    const telefonoCaja = document.getElementById('telefonoCajaH');
    
    if (this.checked){
        telefonoCaja.style.display = 'inline';
    }
    else{
        telefonoCaja.style.display = 'none';
    }
});

var checkboxCorreoJ = document.getElementById("medioContactoCorreoJ");
checkboxCorreoJ.addEventListener('change', function(e) {
    const correoCaja = document.getElementById('correoCajaJ');
    
    if (this.checked){
        correoCaja.style.display = 'inline';
    }
    else{
        correoCaja.style.display = 'none';
    }
});

var checkboxCorreoH = document.getElementById("medioContactoCorreoH");
checkboxCorreoH.addEventListener('change', function(e) {
    const correoCaja = document.getElementById('correoCajaH');
    
    if (this.checked){
        correoCaja.style.display = 'inline';
    }
    else{
        correoCaja.style.display = 'none';
    }
});