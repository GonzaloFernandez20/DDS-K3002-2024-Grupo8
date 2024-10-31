let usuario, contrasena;
// Capturar los valores de WhatsApp y Telegram

document.getElementById('registroForm').addEventListener('submit', function(e) {
    e.preventDefault();
    let hasError = false;

    usuario = document.getElementById('usuario');
    contrasena = document.getElementById('contrasena');
    const tipoColaborador = document.getElementById('tipoColaborador');

    // Validación del usuario
    if (!usuario.value) {
        hasError = true;
        document.getElementById('usuarioError').innerText = 'El usuario es requerido';
        usuario.classList.add('error');
    } else {
        document.getElementById('usuarioError').innerText = '';
        usuario.classList.remove('error');
    }

    // Validación de la contraseña
    if (!contrasena.value) {
        hasError = true;
        document.getElementById('contrasenaError').innerText = 'La contraseña es requerida';
        contrasena.classList.add('error');
    } else {
        document.getElementById('contrasenaError').innerText = '';
        contrasena.classList.remove('error');
    }

    // Validación del tipo de colaborador
    if (!tipoColaborador.value) {
        hasError = true;
        document.getElementById('tipoColaboradorError').innerText = 'Debe seleccionar un tipo de colaborador';
        tipoColaborador.classList.add('error');
    } else {
        document.getElementById('tipoColaboradorError').innerText = '';
        tipoColaborador.classList.remove('error');
    }

    // --------------- VERIFICACION CON EL BACK
    const datosDeUsuario = {
        nombreDeUsuario: usuario.value,
        contrasenia: contrasena.value
    };

    fetch('/ValidarUsuario', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(datosDeUsuario),
    })
    .then(response =>{
        if (!response.ok) {
            throw new Error('Error en la red');
        }
        return response.text();
    })
    .then(msjDeRespuesta => {
        alert(msjDeRespuesta);
        if (!hasError) { desplegarFormulario(tipoColaborador.value); }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Hubo un error al registrar el usuario');
    });

    // Función para desplegar el formulario correspondiente según el tipo de colaborador
    function desplegarFormulario(tipo) {
        const extraFormContainerJuridico = document.getElementById('extraFormContainerJuridico');
        const extraFormContainerHumano = document.getElementById('extraFormContainerHumano');

        if (tipo === 'juridico') {
            extraFormContainerJuridico.style.display = 'block';
            extraFormContainerHumano.style.display = 'none';
        } else if (tipo === 'humano') {
            extraFormContainerHumano.style.display = 'block';
            extraFormContainerJuridico.style.display = 'none';
        }
    }
});

document.getElementById('extraFormContainerJuridico').addEventListener('submit', function(e) {
    e.preventDefault();
    let hasError = false;

    const razonSocial = document.getElementById('razonSocial');
    const rubro = document.getElementById('rubro');
    const tipoOrganizacion = document.getElementById('tipoOrganizacion');
    const emailCaja = document.getElementById('emailCajaJ');
    const telefonoCaja = document.getElementById('telefonoCajaJ');
    const correoCaja = document.getElementById('correoCajaJ');
    const calle = document.getElementById('correoCajaJ'); // TODO: AGREGAR AL FORM ESTOS CAMPOS
    const altura = document.getElementById('correoCajaJ');
    const codigoPostal = document.getElementById('correoCajaJ');

    const whatsappChecked = document.getElementById('checkbox-wp-j').checked;
    const telegramChecked = document.getElementById('checkbox-tl-j').checked;

    // Validación de los campos obligatorios
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

    // Validación de medios de contacto (Email, Teléfono o Dirección)
    if (!(checkboxCorreoJ.checked || checkboxEmailJ.checked || checkboxTelefonoJ.checked)) {
        hasError = true;
        document.getElementById('contactoErrorJ').innerText = 'Debe seleccionar al menos un Medio de Contacto';
    } else {
        document.getElementById('contactoErrorJ').innerText = '';
    }

    // Generamos los datos a enviar
    const colaboradorJuridico = {
        nombreDeUsuario: usuario.value,
        contrasenia: contrasena.value,
        razonSocial: razonSocial.value,
        tipoOrganizacion: tipoOrganizacion.value,
        rubro: rubro.value,
        calle: calle.value,
        altura: altura.value,
        codigoPostal: codigoPostal.value,
        whatsapp: whatsappChecked,
        telegram: telegramChecked,
        mediosDeContacto: [
            { tipo: 'email', valor: emailCaja.value },
            { tipo: 'telefono', valor: telefonoCaja.value },
            { tipo: 'correo', valor: correoCaja.value }
        ]
    };
    // -> Enviamos los datos al back
    fetch('/RegistrarColaboradorJuridico', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(colaboradorJuridico),
    })
        .then(response =>{
            if (!response.ok) {
                throw new Error('Error en la red');
            }
            return response.text();
        })
        .then(msjDeRespuesta => {
            alert(msjDeRespuesta);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Hubo un error al registrar el usuario');
        });

    if (!hasError) {
        alert('Formulario de colaborador Jurídico enviado correctamente');
        location.href = "InicioDeSesion.html";
    }
});

document.getElementById('extraFormContainerHumano').addEventListener('submit', function(e) {
    e.preventDefault();
    let hasError = false;

    const nombre = document.getElementById('nombre');
    const apellido = document.getElementById('apellido');
    const fechaNacimiento = document.getElementById('fechaNacimiento');
    const emailCaja = document.getElementById('emailCajaJ');
    const telefonoCaja = document.getElementById('telefonoCajaJ');
    const correoCaja = document.getElementById('correoCajaJ');
    const tipoDeDocumento = document.getElementById('correoCajaJ');
    const numero = document.getElementById('correoCajaJ');
    const sexo = document.getElementById('correoCajaJ');
    const calle = document.getElementById('correoCajaJ');
    const altura = document.getElementById('correoCajaJ');
    const codigoPostal = document.getElementById('correoCajaJ');
    const mediosDeContacto = document.getElementById('correoCajaJ');
    const whatsappChecked = document.getElementById('checkbox-wp-h').checked;
    const telegramChecked = document.getElementById('checkbox-tl-h').checked;

    // Validación de los campos obligatorios
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

    if (!fechaNacimiento.value) {
        hasError = true;
        document.getElementById('fechaNacimientoError').innerText = 'La fecha de nacimiento es requerida';
        fechaNacimiento.classList.add('error');
    } else {
        document.getElementById('fechaNacimientoError').innerText = '';
        fechaNacimiento.classList.remove('error');
    }

    if (!(checkboxEmailH.checked || checkboxTelefonoH.checked)) {
        hasError = true;
        document.getElementById('contactoErrorH').innerText = 'Debe seleccionar al menos un Medio de Contacto';
    } else {
        document.getElementById('contactoErrorH').innerText = '';
    }

    // Generamos los datos a enviar
    const colaboradorHumano = {
        nombreDeUsuario: usuario.value,
        contrasenia: contrasena.value,
        nombre: nombre.value,
        apellido: apellido.value,
        fechaNacimiento: fechaNacimiento.value,
        tipoDeDocumento: tipoDeDocumento.value,
        numero: numero.value,
        sexo: sexo.value,
         //TODO: Agregar al form estos campos necesarios
        calle: calle.value,
        altura: altura.value,
        codigoPostal: codigoPostal.value,
        whatsapp: whatsappChecked,
        telegram: telegramChecked,
        mediosDeContacto: [
            { tipo: 'email', valor: emailCaja.value },
            { tipo: 'telefono', valor: telefonoCaja.value },
            { tipo: 'correo', valor: correoCaja.value }
        ]
    };
// -> Enviamos los datos al back
    fetch('/RegistrarColaboradorHumano', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(colaboradorHumano),
    })
        .then(response =>{
            if (!response.ok) {
                throw new Error('Error en la red');
            }
            return response.text();
        })
        .then(msjDeRespuesta => {
            alert(msjDeRespuesta);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Hubo un error al registrar el usuario');
        });

    if (!hasError) {
        alert('Formulario de colaborador Humano enviado correctamente');
        location.href = "InicioDeSesion.html";
    }
});

// ---------- PARA COLABORADORES JURIDICOS

// Funcionalidad de checkbox de Email
const checkboxEmailJ = document.getElementById("medioContactoEmailJ");
checkboxEmailJ.addEventListener('change', function() {
    const emailCaja = document.getElementById('emailCajaJ');
    emailCaja.style.display = this.checked ? 'inline' : 'none';
});

// Funcionalidad de checkbox de Telefono
const checkboxTelefonoJ = document.getElementById("medioContactoTelefonoJ");
checkboxTelefonoJ.addEventListener('change', function() {
    const telefonoCaja = document.getElementById('telefonoCajaJ');
    telefonoCaja.style.display = this.checked ? 'inline' : 'none';
});

// ---------- PARA COLABORADORES HUMANOS

// Funcionalidad de checkbox de Email
const checkboxEmailH = document.getElementById("medioContactoEmailH");
checkboxEmailH.addEventListener('change', function() {
    const emailCaja = document.getElementById('emailCajaH');
    emailCaja.style.display = this.checked ? 'inline' : 'none';
});

// Funcionalidad de checkbox de Telefono
const checkboxTelefonoH = document.getElementById("medioContactoTelefonoH");
checkboxTelefonoH.addEventListener('change', function() {
    const telefonoCaja = document.getElementById('telefonoCajaH');
    telefonoCaja.style.display = this.checked ? 'inline' : 'none';
});