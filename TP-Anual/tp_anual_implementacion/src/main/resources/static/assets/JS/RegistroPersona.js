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

    // --------------- CHEQUEO DE ERRORES

    if (hasError) { return; }

    // --------------- VERIFICACION CON EL BACK
    const datosDeUsuario = new URLSearchParams({
        contrasenia: contrasena.value
    });

    fetch('/ValidarUsuario', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: datosDeUsuario.toString(),
    })
    .then(response => {
        return response.text().then(msjDeRespuesta => {
            if (!response.ok) {
                throw new Error(msjDeRespuesta);
            }
            return msjDeRespuesta;
        });
    })
    .then(msjDeRespuesta => {
        //alert(msjDeRespuesta);
        if (msjDeRespuesta.includes("Usuario y contraseña validados exitosamente.")) {

            const mainContainer = document.querySelector('.main-container');
            mainContainer.style.display = 'none';

            desplegarFormulario(tipoColaborador.value);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alertaSimple(error.message, "error")
        //alert(error.message);
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
    let huboError = false;

    const razonSocial = document.getElementById('razonSocial');
    const rubro = document.getElementById('rubro');
    const tipoOrganizacion = document.getElementById('tipoOrganizacion');
    const calle = document.getElementById('direccionE');
    const altura = document.getElementById('altura');

    const emailCaja = document.getElementById('emailIngresadoJ');
    const telefonoCaja = document.getElementById('telefonoIngresadoJ');

    const whatsappChecked = document.getElementById('checkbox-wp-j').checked;
    const telegramChecked = document.getElementById('checkbox-tl-j').checked;

    const datosDeUsuario = {
        usuario: usuario.value,
        contrasenia: contrasena.value,
        razonSocial: razonSocial.value,
        tipoDeOrganizacion: tipoOrganizacion.value,
        rubro: rubro.value,
        calle: calle.value,
        altura: altura.value,
        email: emailCaja.value,
        telefono: telefonoCaja.value,
        tieneWp: whatsappChecked || false,
        tieneTg: telegramChecked || false
    };


    // -> Enviamos los datos al back
    fetch('/RegistrarColaboradorJuridico', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(datosDeUsuario),
    })
        .then(response =>{
            if (!response.ok) {
                throw new Error("No se pudo registrar el usuario.");
            }
            alertaSimple("Usuario registrado con exito!", "success");
            setTimeout(function() {
                window.location.href = "/Home";
            }, 1300);
        })
        .catch(error => {
            console.error('Error:', error);
            alertaSimple('Hubo un error al registrar el usuario', "error");
        });
});

document.getElementById('extraFormContainerHumano').addEventListener('submit', function(e) {
    e.preventDefault();
    let hasError = false;

    const nombre = document.getElementById('nombre');
    const apellido = document.getElementById('apellido');
    const fechaNacimiento = document.getElementById('fechaNacimiento');
    const tipoDocumento = document.getElementById('tipoDocumento');
    const numeroDocPersona = document.getElementById('numeroDocPersona');
    const sexo = document.getElementById('sexo');
    const calle = document.getElementById('calle');
    const altura = document.getElementById('alturaH');
    const emailCaja = document.getElementById('emailIngresadoH');
    const telefonoCaja = document.getElementById('telefonoIngresadoH');
    const whatsappChecked = document.getElementById('checkbox-wp-h').checked;
    const telegramChecked = document.getElementById('checkbox-tl-h').checked;

    // Generamos los datos a enviar
    const colaboradorHumano = {

        usuario: usuario.value,
        contrasenia: contrasena.value,

        nombre: nombre.value,
        apellido: apellido.value,
        fechaDeNacimiento: fechaNacimiento.value,
        tipo: tipoDocumento.value,
        numero: numeroDocPersona.value,
        sexo: sexo.value,
        calle: calle.value,
        altura: altura.value,

        email: emailCaja.value,
        telefono: telefonoCaja.value,
        whatsapp: whatsappChecked,
        telegram: telegramChecked,
    };

    fetch('/RegistrarColaboradorHumano', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(colaboradorHumano),
    })
    .then(response =>{
        if (!response.ok) {
            throw new Error("No se pudo registrar el usuario.");
        }
        alertaSimple("Usuario registrado con exito!", "success");
        setTimeout(function() {
            window.location.href = "/Home";
        }, 1300);
    })
    .catch(error => {
        console.error('Error:', error);
        alertaSimple('Hubo un error al registrar el usuario', "error");
    });
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

//Funcionalidad de checkbox de Email
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