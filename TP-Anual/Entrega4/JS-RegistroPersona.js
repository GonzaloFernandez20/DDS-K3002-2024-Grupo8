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
    const direccion = document.getElementById('direccion'); 
    const emailCaja = document.getElementById('emailCaja');
    const telefonoCaja = document.getElementById('telefonoCaja');
    const correoCaja = document.getElementById('correoCaja');
    const email = document.getElementById('emailIngresado');
    const telefono = document.getElementById('telefonoIngresado');
    const correo = document.getElementById('correoIngresado');

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

    if (medioContacto.value == "email"){
        if (!email.value) {
            hasError = true;
            document.getElementById('emailError').innerText = 'Debe introducir un Email valido';
            emailCaja.classList.add('error');
        } else {
            document.getElementById('emailError').innerText = '';
            emailCaja.classList.remove('error');
        }
    }

    if (medioContacto.value == "telefono"){
        if (!telefono.value) {
            hasError = true;
            document.getElementById('telefonoError').innerText = 'Debe introducir un Telefono valido';
            telefonoCaja.classList.add('error');
        } else {
            document.getElementById('telefonoError').innerText = '';
            telefonoCaja.classList.remove('error');
        }
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
    const direccion = document.getElementById('direccion');
    const medioContacto = document.getElementById('medioContacto');
    const emailCaja = document.getElementById('emailCaja');
    const telefonoCaja = document.getElementById('telefonoCaja');
    const email = document.getElementById('emailIngresado');
    const telefono = document.getElementById('telefonoIngresado');

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

    if (!medioContacto.value) {
        hasError = true;
        document.getElementById('medioContactoError').innerText = 'Debe seleccionar un medio de contacto';
        medioContacto.classList.add('error');
    } else {
        document.getElementById('medioContactoError').innerText = '';
        medioContacto.classList.remove('error');
    }    

    if (medioContacto.value == "email"){
        if (!email.value) {
            hasError = true;
            document.getElementById('emailError').innerText = 'Debe introducir un Email valido';
            emailCaja.classList.add('error');
        } else {
            document.getElementById('emailError').innerText = '';
            emailCaja.classList.remove('error');
        }
    }

    if (medioContacto.value == "telefono"){
        if (!telefono.value) {
            hasError = true;
            document.getElementById('telefonoError').innerText = 'Debe introducir un Telefono valido';
            telefonoCaja.classList.add('error');
        } else {
            document.getElementById('telefonoError').innerText = '';
            telefonoCaja.classList.remove('error');
        }
    }

    if (!hasError) {
        alert('Formulario de colaborador Humano enviado correctamente');
        location.href="InicioDeSesion.html";
    }
});

var checkboxEmail = document.getElementById("medioContactoEmail");
checkboxEmail.addEventListener('change', function(e) {
    const emailCaja = document.getElementById('emailCaja');

    if (this.checked){
        emailCaja.style.display = 'inline';
    }
    else{
        emailCaja.style.display = 'none';
    }
});

var checkboxTelefono = document.getElementById("medioContactoTelefono");
checkboxTelefono.addEventListener('change', function(e) {
    const telefonoCaja = document.getElementById('telefonoCaja');
    
    if (this.checked){
        telefonoCaja.style.display = 'inline';
    }
    else{
        telefonoCaja.style.display = 'none';
    }
});

var checkboxCorreo = document.getElementById("medioContactoCorreo");
checkboxCorreo.addEventListener('change', function(e) {
    const correoCaja = document.getElementById('correoCaja');
    
    if (this.checked){
        correoCaja.style.display = 'inline';
    }
    else{
        correoCaja.style.display = 'none';
    }
});